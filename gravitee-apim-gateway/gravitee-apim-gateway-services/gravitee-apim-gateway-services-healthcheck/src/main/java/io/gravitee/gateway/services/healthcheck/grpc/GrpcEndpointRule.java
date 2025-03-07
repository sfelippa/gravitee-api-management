/**
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
package io.gravitee.gateway.services.healthcheck.grpc;

import io.gravitee.definition.model.endpoint.HttpEndpoint;
import io.gravitee.definition.model.services.healthcheck.HealthCheckService;
import io.gravitee.el.TemplateEngine;
import io.gravitee.gateway.services.healthcheck.EndpointRule;
import io.gravitee.gateway.services.healthcheck.rule.AbstractEndpointRule;
import io.gravitee.gateway.services.healthcheck.rule.EndpointRuleHandler;
import io.vertx.core.Vertx;
import io.vertx.core.net.ProxyOptions;
import org.springframework.core.env.Environment;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class GrpcEndpointRule extends AbstractEndpointRule<HttpEndpoint> {

    public GrpcEndpointRule(String api, HttpEndpoint endpoint, HealthCheckService service, ProxyOptions systemProxyOptions) {
        super(api, endpoint, service, systemProxyOptions);
    }

    @Override
    public EndpointRuleHandler<HttpEndpoint> createRunner(
        Vertx vertx,
        EndpointRule<HttpEndpoint> rule,
        TemplateEngine templateEngine,
        Environment environment
    ) throws Exception {
        return new GrpcEndpointRuleHandler(vertx, rule, templateEngine, environment);
    }
}
