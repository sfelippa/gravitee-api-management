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
package io.gravitee.gateway.jupiter.debug.reactor.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gravitee.definition.model.Api;
import io.gravitee.definition.model.HttpResponse;
import io.gravitee.definition.model.PolicyScope;
import io.gravitee.definition.model.debug.DebugMetrics;
import io.gravitee.definition.model.debug.PreprocessorStep;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.debug.definition.DebugApi;
import io.gravitee.gateway.jupiter.api.ExecutionPhase;
import io.gravitee.gateway.jupiter.api.context.RequestExecutionContext;
import io.gravitee.gateway.jupiter.core.processor.Processor;
import io.gravitee.gateway.jupiter.debug.policy.steps.PolicyStep;
import io.gravitee.gateway.jupiter.debug.reactor.context.DebugRequestExecutionContext;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.EventRepository;
import io.gravitee.repository.management.model.ApiDebugStatus;
import io.gravitee.repository.management.model.Event;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public class DebugCompletionProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugCompletionProcessor.class);
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public DebugCompletionProcessor(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getId() {
        return "processor-debug-completion";
    }

    @Override
    public Completable execute(final RequestExecutionContext ctx) {
        return Completable.defer(
            () -> {
                final DebugRequestExecutionContext debugContext = (DebugRequestExecutionContext) ctx;
                final DebugApi debugApi = (DebugApi) debugContext.getComponent(Api.class);

                Optional<Event> eventOptional = eventRepository.findById(debugApi.getEventId());
                if (eventOptional.isPresent()) {
                    final Event event = eventOptional.get();
                    return computeDebugApiEventPayload(debugContext, debugApi)
                        .doOnSuccess(
                            definitionDebugApi -> {
                                event.setPayload(objectMapper.writeValueAsString(definitionDebugApi));
                                updateEvent(event, ApiDebugStatus.SUCCESS);
                            }
                        )
                        .ignoreElement()
                        .onErrorResumeNext(
                            throwable -> {
                                LOGGER.error("Error occurs while saving debug event", throwable);
                                failEvent(event);
                                return Completable.complete();
                            }
                        );
                }
                return Completable.complete();
            }
        );
    }

    private Single<io.gravitee.definition.model.debug.DebugApi> computeDebugApiEventPayload(
        DebugRequestExecutionContext debugContext,
        DebugApi debugApi
    ) {
        return Single.defer(
            () -> {
                final io.gravitee.definition.model.debug.DebugApi definitionDebugApi = convert(debugApi);
                PreprocessorStep preprocessorStep = createPreprocessorStep(debugContext);
                definitionDebugApi.setPreprocessorStep(preprocessorStep);
                definitionDebugApi.setDebugSteps(convert(debugContext.getDebugSteps()));
                HttpResponse invokerResponse = createResponse(
                    debugContext.getInvokerResponse().getHeaders(),
                    debugContext.getInvokerResponse().getStatus(),
                    debugContext.getInvokerResponse().getBuffer()
                );
                definitionDebugApi.setBackendResponse(invokerResponse);
                definitionDebugApi.setMetrics(createMetrics(debugContext.request().metrics()));
                return debugContext
                    .response()
                    .bodyOrEmpty()
                    .map(
                        buffer -> {
                            HttpResponse response = createResponse(
                                debugContext.response().headers(),
                                debugContext.response().status(),
                                buffer
                            );
                            definitionDebugApi.setResponse(response);
                            return definitionDebugApi;
                        }
                    );
            }
        );
    }

    private DebugMetrics createMetrics(io.gravitee.reporter.api.http.Metrics requestMetrics) {
        final DebugMetrics metrics = new DebugMetrics();
        if (requestMetrics != null) {
            metrics.setApiResponseTimeMs(requestMetrics.getApiResponseTimeMs());
            metrics.setProxyLatencyMs(requestMetrics.getProxyLatencyMs());
            metrics.setProxyResponseTimeMs(requestMetrics.getProxyResponseTimeMs());
        }
        return metrics;
    }

    private PreprocessorStep createPreprocessorStep(DebugRequestExecutionContext debugContext) {
        final PreprocessorStep preprocessorStep = new PreprocessorStep();
        preprocessorStep.setAttributes(debugContext.getInitialAttributes());
        preprocessorStep.setHeaders(debugContext.getInitialHeaders().toListValuesMap());
        return preprocessorStep;
    }

    private HttpResponse createResponse(HttpHeaders httpHeaders, int statusCode, Buffer bodyBuffer) {
        HttpResponse response = new HttpResponse();
        if (httpHeaders != null) {
            response.setHeaders(httpHeaders.toListValuesMap());
        }
        response.statusCode(statusCode);
        if (bodyBuffer != null) {
            response.setBody(bodyBuffer.toString());
        }
        return response;
    }

    private void failEvent(@NonNull Event debugEvent) {
        try {
            updateEvent(debugEvent, ApiDebugStatus.ERROR);
        } catch (TechnicalException e) {
            LOGGER.error("Error when updating event {} with ERROR status", debugEvent.getId());
        }
    }

    private void updateEvent(@NonNull Event debugEvent, ApiDebugStatus apiDebugStatus) throws TechnicalException {
        debugEvent.getProperties().put(Event.EventProperties.API_DEBUG_STATUS.getValue(), apiDebugStatus.name());
        eventRepository.update(debugEvent);
    }

    private io.gravitee.definition.model.debug.DebugApi convert(DebugApi content) {
        io.gravitee.definition.model.debug.DebugApi debugAPI = new io.gravitee.definition.model.debug.DebugApi();
        debugAPI.setName(content.getName());
        debugAPI.setId(content.getId());
        debugAPI.setDefinitionVersion(content.getDefinitionVersion());
        debugAPI.setResponse(content.getResponse());
        debugAPI.setRequest(content.getRequest());
        debugAPI.setFlowMode(content.getFlowMode());
        debugAPI.setFlows(content.getFlows());
        debugAPI.setPathMappings(content.getPathMappings());
        debugAPI.setPlans(content.getPlans());
        debugAPI.setPaths(content.getPaths());
        debugAPI.setServices(content.getServices());
        debugAPI.setProxy(content.getProxy());
        debugAPI.setProperties(content.getProperties());
        debugAPI.setResources(content.getResources());
        debugAPI.setServices(content.getServices());
        debugAPI.setResponseTemplates(content.getResponseTemplates());
        debugAPI.setExecutionMode(content.getExecutionMode());
        return debugAPI;
    }

    private List<io.gravitee.definition.model.debug.DebugStep> convert(List<PolicyStep<?>> policySteps) {
        return policySteps.stream().map(this::convert).collect(Collectors.toList());
    }

    private io.gravitee.definition.model.debug.DebugStep convert(PolicyStep<?> ds) {
        final io.gravitee.definition.model.debug.DebugStep debugStep = new io.gravitee.definition.model.debug.DebugStep();
        debugStep.setPolicyInstanceId(ds.getId());
        debugStep.setPolicyId(ds.getPolicyId());
        debugStep.setDuration(ds.elapsedTime().toNanos());
        debugStep.setStatus(ds.getStatus());
        debugStep.setCondition(ds.getCondition());
        debugStep.setError(ds.getError());
        debugStep.setStage(ds.getFlowPhase());
        if (ds.getExecutionPhase() == ExecutionPhase.REQUEST) {
            debugStep.setScope(PolicyScope.ON_REQUEST);
        } else if (ds.getExecutionPhase() == ExecutionPhase.RESPONSE) {
            debugStep.setScope(PolicyScope.ON_RESPONSE);
        }

        Map<String, Object> result = new HashMap<>();
        ds
            .getDiff()
            .forEach(
                (key, value) -> {
                    if (PolicyStep.DIFF_KEY_HEADERS.equals(key)) {
                        // Headers are converted to Map<String, List<String>>
                        result.put(PolicyStep.DIFF_KEY_HEADERS, ((HttpHeaders) value).toListValuesMap());
                    } else if (PolicyStep.DIFF_KEY_BODY_BUFFER.equals(key)) {
                        // Body is converted from Buffer to String
                        result.put(PolicyStep.DIFF_KEY_BODY, value.toString());
                    } else {
                        // Everything else is kept as is
                        result.put(key, value);
                    }
                }
            );

        debugStep.setResult(result);
        return debugStep;
    }
}
