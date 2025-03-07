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
package io.gravitee.rest.api.services.dynamicproperties;

import io.gravitee.common.component.Lifecycle;
import io.gravitee.common.event.Event;
import io.gravitee.common.event.EventListener;
import io.gravitee.common.event.EventManager;
import io.gravitee.common.service.AbstractService;
import io.gravitee.definition.model.services.dynamicproperty.DynamicPropertyProvider;
import io.gravitee.definition.model.services.dynamicproperty.DynamicPropertyService;
import io.gravitee.node.api.Node;
import io.gravitee.rest.api.model.api.ApiEntity;
import io.gravitee.rest.api.service.ApiService;
import io.gravitee.rest.api.service.EventService;
import io.gravitee.rest.api.service.HttpClientService;
import io.gravitee.rest.api.service.converter.ApiConverter;
import io.gravitee.rest.api.service.event.ApiEvent;
import io.gravitee.rest.api.services.dynamicproperties.provider.http.HttpProvider;
import io.vertx.core.Vertx;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Alexandre FARIA (lusoalex on github.com)
 */
public class DynamicPropertiesService extends AbstractService implements EventListener<ApiEvent, ApiEntity> {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(DynamicPropertiesService.class);

    @Autowired
    private EventManager eventManager;

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiConverter apiConverter;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private Vertx vertx;

    @Autowired
    private Node node;

    final Map<ApiEntity, CronHandler> handlers = new HashMap<>();

    @Override
    protected String name() {
        return "Dynamic Properties Service";
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();

        eventManager.subscribeForEvents(this, ApiEvent.class);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

    @Override
    public void onEvent(Event<ApiEvent, ApiEntity> event) {
        final ApiEntity api = event.content();

        switch (event.type()) {
            case DEPLOY:
                startDynamicProperties(api);
                break;
            case UNDEPLOY:
                stopDynamicProperties(api);
                break;
            case UPDATE:
                update(api);
                break;
        }
    }

    private void update(ApiEntity api) {
        final ApiEntity currentApi = handlers.keySet().stream().filter(entity -> entity.equals(api)).findFirst().orElse(null);

        if (currentApi == null) {
            // There is no dynamic properties handler for this api, start the dynamic properties.
            startDynamicProperties(api);
        } else {
            // We need to check if the dynamic properties has changed. If that is the case, stop en start the dynamic properties handler.
            DynamicPropertyService currentDynamicPropertyService = currentApi.getServices().get(DynamicPropertyService.class);
            DynamicPropertyService dynamicPropertyService = api.getServices().get(DynamicPropertyService.class);

            if (currentDynamicPropertyService != null) {
                if (!Objects.equals(currentDynamicPropertyService, dynamicPropertyService)) {
                    // Configuration has changed. Need to stop the current timer before restarting it.
                    stopDynamicProperties(api);
                    startDynamicProperties(api);
                }
            } else {
                startDynamicProperties(api);
            }
        }
    }

    private void startDynamicProperties(ApiEntity api) {
        if (api.getState() == Lifecycle.State.STARTED) {
            DynamicPropertyService dynamicPropertyService = api.getServices().get(DynamicPropertyService.class);
            if (dynamicPropertyService != null && dynamicPropertyService.isEnabled()) {
                DynamicPropertyUpdater updater = new DynamicPropertyUpdater(api);

                if (dynamicPropertyService.getProvider() == DynamicPropertyProvider.HTTP) {
                    HttpProvider provider = new HttpProvider(dynamicPropertyService);
                    provider.setHttpClientService(httpClientService);
                    provider.setNode(node);

                    updater.setProvider(provider);
                    updater.setApiService(apiService);
                    updater.setApiConverter(apiConverter);
                    logger.info("Add a scheduled task to poll dynamic properties each {}", dynamicPropertyService.getSchedule());

                    // Force the first refresh, and then run it periodically
                    updater.handle(null);
                    CronHandler cronHandler = new CronHandler(vertx, dynamicPropertyService.getSchedule());
                    cronHandler.schedule(updater);
                    handlers.put(api, cronHandler);
                }
            } else {
                logger.info("Dynamic properties service is disabled for: {} [{}]", api.getName(), api.getVersion());
            }
        }
    }

    private void stopDynamicProperties(ApiEntity api) {
        CronHandler handler = handlers.remove(api);
        if (handler != null) {
            logger.info("Stop Dynamic properties service for API id[{}] name[{}]", api.getId(), api.getName());
            handler.cancel();
        }
    }
}
