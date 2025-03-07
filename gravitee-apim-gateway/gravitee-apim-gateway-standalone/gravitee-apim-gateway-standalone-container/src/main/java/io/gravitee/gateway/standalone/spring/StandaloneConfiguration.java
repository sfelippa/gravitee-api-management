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
package io.gravitee.gateway.standalone.spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gravitee.common.event.EventManager;
import io.gravitee.common.event.impl.EventManagerImpl;
import io.gravitee.definition.jackson.datatype.GraviteeMapper;
import io.gravitee.el.ExpressionLanguageInitializer;
import io.gravitee.gateway.connector.spring.ConnectorConfiguration;
import io.gravitee.gateway.dictionary.spring.DictionaryConfiguration;
import io.gravitee.gateway.env.GatewayConfiguration;
import io.gravitee.gateway.handlers.api.spring.ApiHandlerConfiguration;
import io.gravitee.gateway.platform.spring.PlatformConfiguration;
import io.gravitee.gateway.policy.spring.PolicyConfiguration;
import io.gravitee.gateway.reactor.spring.ReactorConfiguration;
import io.gravitee.gateway.report.spring.ReporterConfiguration;
import io.gravitee.gateway.repository.plugins.GatewayRepositoryScopeProvider;
import io.gravitee.gateway.standalone.node.GatewayNode;
import io.gravitee.gateway.standalone.node.GatewayNodeMetadataResolver;
import io.gravitee.gateway.standalone.vertx.VertxReactorConfiguration;
import io.gravitee.node.api.NodeMetadataResolver;
import io.gravitee.node.certificates.spring.NodeCertificatesConfiguration;
import io.gravitee.node.cluster.spring.ClusterConfiguration;
import io.gravitee.node.container.NodeFactory;
import io.gravitee.node.kubernetes.spring.NodeKubernetesConfiguration;
import io.gravitee.node.vertx.spring.VertxConfiguration;
import io.gravitee.platform.repository.api.RepositoryScopeProvider;
import io.gravitee.plugin.alert.spring.AlertPluginConfiguration;
import io.gravitee.plugin.core.spring.PluginConfiguration;
import io.gravitee.plugin.discovery.spring.ServiceDiscoveryPluginConfiguration;
import io.gravitee.plugin.policy.spring.PolicyPluginConfiguration;
import io.gravitee.plugin.resource.spring.ResourcePluginConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@Configuration
@Import(
    {
        VertxConfiguration.class,
        ReactorConfiguration.class,
        NodeCertificatesConfiguration.class,
        NodeKubernetesConfiguration.class,
        VertxReactorConfiguration.class,
        PluginConfiguration.class,
        PolicyPluginConfiguration.class,
        ResourcePluginConfiguration.class,
        ReporterConfiguration.class,
        ApiHandlerConfiguration.class,
        DictionaryConfiguration.class,
        AlertPluginConfiguration.class,
        ServiceDiscoveryPluginConfiguration.class,
        PolicyConfiguration.class,
        PlatformConfiguration.class,
        ConnectorConfiguration.class,
        ClusterConfiguration.class,
    }
)
public class StandaloneConfiguration {

    @Bean
    public EventManager eventManager() {
        return new EventManagerImpl();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new GraviteeMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public ExpressionLanguageInitializer expressionLanguageInitializer() {
        return new ExpressionLanguageInitializer();
    }

    @Bean
    public NodeFactory node() {
        return new NodeFactory(GatewayNode.class);
    }

    @Bean
    public RepositoryScopeProvider repositoryScopeProvider() {
        return new GatewayRepositoryScopeProvider();
    }

    @Bean
    public static GatewayConfiguration gatewayConfiguration() {
        return new GatewayConfiguration();
    }

    @Bean
    public NodeMetadataResolver nodeMetadataResolver() {
        return new GatewayNodeMetadataResolver();
    }
}
