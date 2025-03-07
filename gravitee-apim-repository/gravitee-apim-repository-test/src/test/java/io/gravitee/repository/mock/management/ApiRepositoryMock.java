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
package io.gravitee.repository.mock.management;

import static io.gravitee.repository.management.model.ApiLifecycleState.PUBLISHED;
import static io.gravitee.repository.management.model.LifecycleState.STARTED;
import static io.gravitee.repository.management.model.LifecycleState.STOPPED;
import static io.gravitee.repository.management.model.Visibility.PUBLIC;
import static io.gravitee.repository.utils.DateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.ApiFieldInclusionFilter;
import io.gravitee.repository.management.api.ApiRepository;
import io.gravitee.repository.management.api.search.ApiCriteria;
import io.gravitee.repository.management.api.search.ApiFieldExclusionFilter;
import io.gravitee.repository.management.api.search.builder.PageableBuilder;
import io.gravitee.repository.management.model.Api;
import io.gravitee.repository.management.model.ApiLifecycleState;
import io.gravitee.repository.management.model.LifecycleState;
import io.gravitee.repository.management.model.Visibility;
import io.gravitee.repository.mock.AbstractRepositoryMock;
import java.util.*;
import java.util.Set;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ApiRepositoryMock extends AbstractRepositoryMock<ApiRepository> {

    public ApiRepositoryMock() {
        super(ApiRepository.class);
    }

    @Override
    protected void prepare(ApiRepository apiRepository) throws Exception {
        final Api apiToDelete = mock(Api.class);
        when(apiToDelete.getId()).thenReturn("api-to-delete");
        when(apiToDelete.getDefinition()).thenReturn("\"proxy\" : {  \"context_path\" : \"/product\" }");

        final Api apiToUpdate = mock(Api.class);
        when(apiToUpdate.getId()).thenReturn("api-to-update");
        when(apiToUpdate.getName()).thenReturn("api-to-update name");
        when(apiToUpdate.getApiLifecycleState()).thenReturn(PUBLISHED);
        when(apiToUpdate.getDefinition()).thenReturn("\"proxy\" : {  \"context_path\" : \"/product\" }");
        final Api apiUpdated = mock(Api.class);
        when(apiUpdated.getName()).thenReturn("New API name");
        when(apiUpdated.getEnvironmentId()).thenReturn("new_DEFAULT");
        when(apiUpdated.getDescription()).thenReturn("New description");
        when(apiUpdated.getCategories()).thenReturn(Sets.newSet("category1", "category2"));
        when(apiUpdated.getDefinition()).thenReturn("New definition");
        when(apiUpdated.getDeployedAt()).thenReturn(parse("11/02/2016"));
        when(apiUpdated.getGroups()).thenReturn(singleton("New group"));
        when(apiUpdated.getLifecycleState()).thenReturn(STARTED);
        when(apiUpdated.getPicture()).thenReturn("New picture");
        when(apiUpdated.getBackground()).thenReturn("New background");
        when(apiUpdated.getCreatedAt()).thenReturn(parse("11/02/2016"));
        when(apiUpdated.getUpdatedAt()).thenReturn(parse("13/11/2016"));
        when(apiUpdated.getVersion()).thenReturn("New version");
        when(apiUpdated.getVisibility()).thenReturn(Visibility.PRIVATE);
        when(apiUpdated.getApiLifecycleState()).thenReturn(ApiLifecycleState.UNPUBLISHED);
        when(apiUpdated.isDisableMembershipNotifications()).thenReturn(false);

        when(apiRepository.findById("api-to-update")).thenReturn(of(apiToUpdate), of(apiUpdated));

        when(apiRepository.findById("api-to-delete")).thenReturn(of(apiToDelete), empty());

        when(apiRepository.findById("findByNameMissing")).thenReturn(empty());

        final Api newApi = mock(Api.class);
        when(newApi.getId()).thenReturn("newApi-Id");
        when(newApi.getVersion()).thenReturn("1");
        when(newApi.getLifecycleState()).thenReturn(LifecycleState.STOPPED);
        when(newApi.getVisibility()).thenReturn(Visibility.PRIVATE);
        when(newApi.getDefinition()).thenReturn("{}");
        when(newApi.getEnvironmentId()).thenReturn("DEFAULT");
        when(newApi.getCreatedAt()).thenReturn(parse("11/02/2016"));
        when(newApi.getUpdatedAt()).thenReturn(parse("12/02/2016"));
        when(newApi.getApiLifecycleState()).thenReturn(ApiLifecycleState.CREATED);
        when(newApi.isDisableMembershipNotifications()).thenReturn(true);
        when(apiRepository.findById("newApi-Id")).thenReturn(of(newApi), empty());

        final Api groupedApi = mock(Api.class);
        when(groupedApi.getGroups()).thenReturn(singleton("api-group"));
        when(groupedApi.getId()).thenReturn("grouped-api");
        when(groupedApi.getApiLifecycleState()).thenReturn(PUBLISHED);
        when(groupedApi.getCategories()).thenReturn(Set.of("category-1"));
        when(apiRepository.findById("grouped-api")).thenReturn(of(groupedApi));
        when(groupedApi.getDefinition()).thenReturn("\"proxy\" : {  \"context_path\" : \"/product\" }");

        final Api apiToFindById = mock(Api.class);
        when(apiToFindById.getId()).thenReturn("api-to-findById");
        when(apiToFindById.getEnvironmentId()).thenReturn("DEFAULT");
        when(apiToFindById.getVersion()).thenReturn("1");
        when(apiToFindById.getName()).thenReturn("api-to-findById name");
        when(apiToFindById.getLifecycleState()).thenReturn(LifecycleState.STOPPED);
        when(apiToFindById.getVisibility()).thenReturn(PUBLIC);
        when(apiToFindById.getDefinition()).thenReturn(null);
        when(apiToFindById.getCreatedAt()).thenReturn(parse("11/02/2016"));
        when(apiToFindById.getUpdatedAt()).thenReturn(parse("12/02/2016"));
        when(apiToFindById.getLabels()).thenReturn(asList("label 1", "label 2"));
        when(apiToFindById.getApiLifecycleState()).thenReturn(ApiLifecycleState.DEPRECATED);
        when(apiToFindById.isDisableMembershipNotifications()).thenReturn(true);
        when(apiRepository.findById("api-to-findById")).thenReturn(of(apiToFindById));

        final Api apiToFindByCrossId = mock(Api.class);
        when(apiToFindByCrossId.getId()).thenReturn("crossId-api");

        final Api apiBigName = mock(Api.class);
        when(apiBigName.getId()).thenReturn("big-name");
        when(apiBigName.getEnvironmentId()).thenReturn("DEV");

        final List<Api> searchedApis = asList(mock(Api.class), mock(Api.class), mock(Api.class), mock(Api.class));
        final List<Api> searchedApisAfterDeletion = asList(mock(Api.class), mock(Api.class), mock(Api.class));
        when(apiRepository.search(null)).thenReturn(searchedApis, searchedApis, searchedApis, searchedApisAfterDeletion);
        when(apiRepository.search(new ApiCriteria.Builder().build()))
            .thenReturn(asList(mock(Api.class), mock(Api.class), mock(Api.class), mock(Api.class)));

        when(apiRepository.search(new ApiCriteria.Builder().ids("api-to-delete", "api-to-update", "unknown").build()))
            .thenReturn(asList(apiToUpdate, apiToDelete));

        when(apiRepository.search(new ApiCriteria.Builder().environments(asList("DEV", "DEVS")).build()))
            .thenReturn(asList(apiToUpdate, apiToDelete, apiBigName));

        when(
            apiRepository.searchIds(
                eq(new ApiCriteria.Builder().ids("api-to-delete", "api-to-update", "unknown").build()),
                eq(new ApiCriteria.Builder().environments(asList("DEV", "DEVS")).build()),
                eq(new ApiCriteria.Builder().groups(asList("api-group", "unknown")).build())
            )
        )
            .thenReturn(asList("api-to-delete", "api-to-update", "big-name", "grouped-api"));

        when(apiRepository.update(argThat(o -> o == null || o.getId().equals("unknown")))).thenThrow(new IllegalStateException());

        when(apiRepository.search(new ApiCriteria.Builder().name("api-to-findById name").build())).thenReturn(singletonList(apiToFindById));
        when(apiRepository.search(new ApiCriteria.Builder().category("my-category").build())).thenReturn(singletonList(apiToFindById));
        when(apiRepository.search(new ApiCriteria.Builder().name("api-to-findById name").version("1").build()))
            .thenReturn(singletonList(apiToFindById));
        when(
            apiRepository.search(
                new ApiCriteria.Builder().name("api-to-findById name").version("1").build(),
                new ApiFieldExclusionFilter.Builder().excludeDefinition().build()
            )
        )
            .thenReturn(singletonList(apiToFindById));
        when(apiRepository.search(new ApiCriteria.Builder().groups("api-group", "unknown").build())).thenReturn(singletonList(groupedApi));
        when(apiRepository.search(new ApiCriteria.Builder().version("1").build()))
            .thenReturn(asList(apiToFindById, groupedApi, apiToDelete, apiToUpdate));
        when(apiRepository.search(new ApiCriteria.Builder().label("label 1").build())).thenReturn(singletonList(apiToFindById));
        when(apiRepository.search(new ApiCriteria.Builder().state(STOPPED).build()))
            .thenReturn(asList(apiToFindById, groupedApi, apiToDelete, apiToUpdate));
        when(apiRepository.search(new ApiCriteria.Builder().visibility(PUBLIC).build())).thenReturn(asList(apiToFindById, groupedApi));
        when(apiRepository.search(new ApiCriteria.Builder().environmentId("DEFAULT").build()))
            .thenReturn(asList(apiToFindById, groupedApi));
        when(apiRepository.search(new ApiCriteria.Builder().version("1").build(), new PageableBuilder().pageNumber(0).pageSize(2).build()))
            .thenReturn(new io.gravitee.common.data.domain.Page<>(asList(apiToDelete, apiToFindById), 0, 2, 4));
        when(apiRepository.search(new ApiCriteria.Builder().version("1").build(), new PageableBuilder().pageNumber(1).pageSize(2).build()))
            .thenReturn(new io.gravitee.common.data.domain.Page<>(asList(apiToUpdate, groupedApi), 1, 2, 4));
        when(apiRepository.search(new ApiCriteria.Builder().version("1").build(), new PageableBuilder().build()))
            .thenReturn(new io.gravitee.common.data.domain.Page<>(asList(apiToDelete, apiToFindById, apiToUpdate, groupedApi), 0, 4, 4));

        when(apiRepository.search(new ApiCriteria.Builder().lifecycleStates(singletonList(PUBLISHED)).build()))
            .thenReturn(asList(apiToUpdate, groupedApi, apiToUpdate));

        when(apiRepository.search(new ApiCriteria.Builder().crossId("searched-crossId").build())).thenReturn(asList(apiToFindById));

        Api apiToUpdateProjection = Mockito.mock(Api.class);
        when(apiToUpdateProjection.getId()).thenReturn("api-to-update");

        Api groupedApiProjection = Mockito.mock(Api.class);
        when(groupedApiProjection.getId()).thenReturn("grouped-api");
        when(groupedApiProjection.getCategories()).thenReturn(Set.of("category-1"));

        Api groupedApiProjectionWithoutCategory = Mockito.mock(Api.class);
        when(groupedApiProjectionWithoutCategory.getId()).thenReturn("grouped-api");

        Api bigNameApiProjection = Mockito.mock(Api.class);
        when(bigNameApiProjection.getId()).thenReturn("big-name");

        when(apiRepository.search(any(), argThat((ApiFieldInclusionFilter filter) -> null != filter && filter.hasCategories())))
            .thenReturn(Set.of(apiToUpdateProjection, groupedApiProjection, bigNameApiProjection));

        when(
            apiRepository.search(
                any(),
                argThat((ApiFieldInclusionFilter filter) -> null != filter && Boolean.FALSE.equals(filter.hasCategories()))
            )
        )
            .thenReturn(Set.of(apiToUpdateProjection, groupedApiProjectionWithoutCategory, bigNameApiProjection));

        Set<String> categories = new LinkedHashSet<>();
        categories.add("category-1");
        categories.add("cycling");
        categories.add("hiking");
        categories.add("my-category");
        when(apiRepository.listCategories(eq(new ApiCriteria.Builder().build()))).thenReturn(categories);

        Set<String> apiCategories = new LinkedHashSet<>();
        apiCategories.add("my-category");
        when(apiRepository.listCategories(eq(new ApiCriteria.Builder().ids("api-to-findById").build()))).thenReturn(apiCategories);

        when(apiRepository.findByEnvironmentIdAndCrossId("ENV6", "searched-crossId2")).thenReturn(Optional.of(apiToFindByCrossId));

        when(apiRepository.findByEnvironmentIdAndCrossId("ENV6", "duplicated-crossId")).thenThrow(new TechnicalException("test exception"));
    }
}
