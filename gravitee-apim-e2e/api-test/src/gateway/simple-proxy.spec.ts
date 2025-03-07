/*
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { APIsApi } from '@management-apis/APIsApi';
import { forManagementAsApiUser } from '@client-conf/*';
import { afterAll, beforeAll, describe, expect } from '@jest/globals';
import { succeed } from '@lib/jest-utils';
import { ApisFaker } from '@management-fakers/ApisFaker';
import { ApiEntity } from '@management-models/ApiEntity';
import { PlansFaker } from '@management-fakers/PlansFaker';
import { LifecycleAction } from '@management-models/LifecycleAction';
import { PlanStatus } from '@management-models/PlanStatus';
import { fetchGatewaySuccess } from '@lib/gateway';
import { APIPlansApi } from '@management-apis/APIPlansApi';
import { LoadBalancerTypeEnum } from '@management-models/LoadBalancer';

const orgId = 'DEFAULT';
const envId = 'DEFAULT';

const apisResourceAsPublisher = new APIsApi(forManagementAsApiUser());
const apiPlansResourceAsPublisher = new APIPlansApi(forManagementAsApiUser());

describe('Gateway - Simple proxy', () => {
  let createdApi: ApiEntity;

  beforeAll(async () => {
    // create an API with a published free plan
    createdApi = await succeed(
      apisResourceAsPublisher.importApiDefinitionRaw({
        envId,
        orgId,
        body: ApisFaker.apiImport({
          plans: [PlansFaker.plan({ status: PlanStatus.PUBLISHED })],
          proxy: ApisFaker.proxy({
            groups: [
              {
                name: 'default-group',
                endpoints: [
                  {
                    inherit: true,
                    name: 'default',
                    target: `${process.env.WIREMOCK_BASE_URL}/hello`,
                    weight: 1,
                    backup: false,
                    type: 'http',
                  },
                ],
                load_balancing: {
                  type: LoadBalancerTypeEnum.ROUNDROBIN,
                },
                http: {
                  connectTimeout: 5000,
                  idleTimeout: 60000,
                  keepAlive: true,
                  readTimeout: 10000,
                  pipelining: false,
                  maxConcurrentConnections: 100,
                  useCompression: true,
                  followRedirects: false,
                },
              },
            ],
          }),
        }),
      }),
    );

    // start it
    await apisResourceAsPublisher.doApiLifecycleAction({
      envId,
      orgId,
      api: createdApi.id,
      action: LifecycleAction.START,
    });
  });

  describe('Gateway call', () => {
    test('Gateway call should return backend response', async () => {
      await fetchGatewaySuccess({ contextPath: createdApi.context_path })
        .then((res) => res.json())
        .then((json) => {
          expect(json.message).toBe('Hello, World!');
        });
    });
  });

  afterAll(async () => {
    if (createdApi) {
      // stop API
      await apisResourceAsPublisher.doApiLifecycleAction({
        envId,
        orgId,
        api: createdApi.id,
        action: LifecycleAction.STOP,
      });

      // close plan
      await apiPlansResourceAsPublisher.closeApiPlan({
        envId,
        orgId,
        plan: createdApi.plans[0].id,
        api: createdApi.id,
      });

      // delete API
      await apisResourceAsPublisher.deleteApi({
        envId,
        orgId,
        api: createdApi.id,
      });
    }
  });
});
