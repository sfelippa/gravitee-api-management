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
package io.gravitee.rest.api.service;

import io.gravitee.rest.api.model.PageEntity;
import io.gravitee.rest.api.model.api.ApiEntity;
import io.gravitee.rest.api.service.common.ExecutionContext;

/**
 * @author Guillaume Cusnieux (guillaume.cusnieux at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface AccessControlService {
    boolean canAccessApiFromPortal(ExecutionContext executionContext, String apiId);

    boolean canAccessApiFromPortal(ExecutionContext executionContext, ApiEntity apiEntity);

    boolean canAccessPageFromPortal(final ExecutionContext executionContext, PageEntity pageEntity);

    boolean canAccessPageFromPortal(final ExecutionContext executionContext, String apiId, PageEntity pageEntity);

    boolean canAccessPageFromConsole(final ExecutionContext executionContext, ApiEntity apiEntity, PageEntity pageEntity);
}
