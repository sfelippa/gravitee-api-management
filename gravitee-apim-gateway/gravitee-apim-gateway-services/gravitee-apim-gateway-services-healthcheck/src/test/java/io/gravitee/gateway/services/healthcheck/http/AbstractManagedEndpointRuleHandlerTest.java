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
package io.gravitee.gateway.services.healthcheck.http;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.gravitee.common.http.HttpMethod;
import io.gravitee.definition.model.Endpoint;
import io.gravitee.definition.model.HttpClientOptions;
import io.gravitee.definition.model.endpoint.HttpEndpoint;
import io.gravitee.definition.model.services.healthcheck.HealthCheckRequest;
import io.gravitee.definition.model.services.healthcheck.HealthCheckResponse;
import io.gravitee.definition.model.services.healthcheck.HealthCheckStep;
import io.gravitee.el.TemplateEngine;
import io.gravitee.gateway.services.healthcheck.EndpointRule;
import io.gravitee.reporter.api.health.EndpointStatus;
import io.gravitee.reporter.api.health.Step;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.core.env.Environment;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class AbstractManagedEndpointRuleHandlerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    private Environment environment;
    private Vertx vertx;

    @Mock
    private TemplateEngine templateEngine;

    @Before
    public void before() {
        vertx = Vertx.vertx();
        environment = mock(Environment.class);
        when(environment.getProperty("http.ssl.openssl", Boolean.class, false)).thenReturn(useOpenSsl());
    }

    protected abstract Boolean useOpenSsl();

    @Test
    public void shouldNotValidate_invalidEndpoint(TestContext context) throws Exception {
        // Prepare HTTP endpoint
        stubFor(get(urlEqualTo("/")).willReturn(notFound()));

        EndpointRule rule = createEndpointRule();

        HealthCheckStep step = new HealthCheckStep();
        HealthCheckRequest request = new HealthCheckRequest("/", HttpMethod.GET);

        step.setRequest(request);
        HealthCheckResponse response = new HealthCheckResponse();
        response.setAssertions(Collections.singletonList(HealthCheckResponse.DEFAULT_ASSERTION));
        step.setResponse(response);

        when(rule.steps()).thenReturn(Collections.singletonList(step));

        HttpEndpointRuleHandler runner = new HttpEndpointRuleHandler(vertx, rule, templateEngine, environment);
        Async async = context.async();

        // Verify
        runner.setStatusHandler(
            new Handler<EndpointStatus>() {
                @Override
                public void handle(EndpointStatus status) {
                    Assert.assertFalse(status.isSuccess());
                    verify(getRequestedFor(urlEqualTo("/")));
                    async.complete();
                }
            }
        );

        // Run
        runner.handle(null);

        // Wait until completion
        async.awaitSuccess();
    }

    @Test
    public void shouldValidate(TestContext context) throws Exception {
        // Prepare HTTP endpoint
        stubFor(get(urlEqualTo("/")).willReturn(ok("{\"status\": \"green\"}")));

        // Prepare
        EndpointRule rule = createEndpointRule();

        HealthCheckStep step = new HealthCheckStep();
        HealthCheckRequest request = new HealthCheckRequest("/", HttpMethod.GET);

        step.setRequest(request);
        HealthCheckResponse response = new HealthCheckResponse();
        response.setAssertions(Collections.singletonList(HealthCheckResponse.DEFAULT_ASSERTION));
        step.setResponse(response);
        when(rule.steps()).thenReturn(Collections.singletonList(step));

        HttpEndpointRuleHandler runner = new HttpEndpointRuleHandler(vertx, rule, templateEngine, environment);

        Async async = context.async();

        // Verify
        runner.setStatusHandler(
            new Handler<EndpointStatus>() {
                @Override
                public void handle(EndpointStatus status) {
                    Assert.assertTrue(status.isSuccess());
                    verify(getRequestedFor(urlEqualTo("/")));
                    async.complete();
                }
            }
        );

        // Run
        runner.handle(null);

        // Wait until completion
        async.awaitSuccess();
    }

    @Test
    public void shouldNotValidate_invalidResponseBody(TestContext context) throws Exception {
        // Prepare HTTP endpoint
        stubFor(get(urlEqualTo("/")).willReturn(ok("{\"status\": \"yellow\"}")));

        // Prepare
        EndpointRule rule = createEndpointRule();

        HealthCheckStep step = new HealthCheckStep();
        HealthCheckRequest request = new HealthCheckRequest("/", HttpMethod.GET);

        step.setRequest(request);
        HealthCheckResponse response = new HealthCheckResponse();
        response.setAssertions(Collections.singletonList("#jsonPath(#response.content, '$.status') == 'green'"));
        step.setResponse(response);
        when(rule.steps()).thenReturn(Collections.singletonList(step));

        HttpEndpointRuleHandler runner = new HttpEndpointRuleHandler(vertx, rule, templateEngine, environment);

        Async async = context.async();

        // Verify
        runner.setStatusHandler(
            new Handler<EndpointStatus>() {
                @Override
                public void handle(EndpointStatus status) {
                    Assert.assertFalse(status.isSuccess());
                    verify(getRequestedFor(urlEqualTo("/")));

                    // When health-check is false, we store both request and response
                    Step result = status.getSteps().get(0);
                    Assert.assertEquals(HttpMethod.GET, result.getRequest().getMethod());
                    Assert.assertNotNull(result.getResponse().getBody());

                    async.complete();
                }
            }
        );

        // Run
        runner.handle(null);

        // Wait until completion
        async.awaitSuccess();
    }

    @Test
    public void shouldValidateFromRoot(TestContext context) throws Exception {
        // Prepare HTTP endpoint
        stubFor(get(urlEqualTo("/")).willReturn(ok()));

        // Prepare
        EndpointRule rule = createEndpointRule("/additional-but-unused-path-for-hc");

        HealthCheckStep step = new HealthCheckStep();
        HealthCheckRequest request = new HealthCheckRequest("/", HttpMethod.GET);
        request.setFromRoot(true);

        step.setRequest(request);
        HealthCheckResponse response = new HealthCheckResponse();
        response.setAssertions(Collections.singletonList(HealthCheckResponse.DEFAULT_ASSERTION));
        step.setResponse(response);
        when(rule.steps()).thenReturn(Collections.singletonList(step));

        HttpEndpointRuleHandler runner = new HttpEndpointRuleHandler(vertx, rule, templateEngine, environment);

        Async async = context.async();

        // Verify
        runner.setStatusHandler(
            new Handler<EndpointStatus>() {
                @Override
                public void handle(EndpointStatus status) {
                    verify(getRequestedFor(urlEqualTo("/")));
                    verify(0, getRequestedFor(urlEqualTo("/additional-but-unused-path-for-hc")));
                    Assert.assertTrue(status.isSuccess());
                    async.complete();
                }
            }
        );

        // Run
        runner.handle(null);

        // Wait until completion
        async.awaitSuccess();
    }

    private Endpoint createEndpoint(String targetPath) {
        HttpEndpoint aDefault = new HttpEndpoint(
            "default",
            "http://localhost:" + wireMockRule.port() + (targetPath != null ? targetPath : "")
        );
        aDefault.setHttpClientOptions(new HttpClientOptions());
        return aDefault;
    }

    private EndpointRule createEndpointRule() {
        return createEndpointRule(null);
    }

    private EndpointRule createEndpointRule(String targetPath) {
        EndpointRule rule = mock(EndpointRule.class);
        when(rule.endpoint()).thenReturn(createEndpoint(targetPath));
        when(rule.schedule()).thenReturn("*/5 * * * * *");
        return rule;
    }
}
