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
package io.gravitee.gateway.jupiter.http.vertx;

import io.gravitee.common.http.HttpMethod;
import io.gravitee.common.http.HttpVersion;
import io.gravitee.common.http.IdGenerator;
import io.gravitee.common.util.LinkedMultiValueMap;
import io.gravitee.common.util.MultiValueMap;
import io.gravitee.common.util.URIUtils;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.http.HttpHeaders;
import io.gravitee.gateway.api.ws.WebSocket;
import io.gravitee.gateway.http.utils.WebSocketUtils;
import io.gravitee.gateway.http.vertx.VertxHttpHeaders;
import io.gravitee.gateway.jupiter.core.context.MutableRequest;
import io.gravitee.gateway.jupiter.http.vertx.ws.VertxWebSocket;
import io.gravitee.reporter.api.http.Metrics;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.core.net.SocketAddress;
import javax.net.ssl.SSLSession;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public class VertxHttpServerRequest extends AbstractHttpChunks implements MutableRequest {

    protected final long timestamp;
    protected final Metrics metrics;
    protected final HttpServerRequest nativeRequest;
    protected String contextPath;
    protected String pathInfo;
    protected String id;
    protected String transactionId;
    protected String remoteAddress;
    protected String localAddress;
    protected Boolean isWebSocket = null;
    protected WebSocket webSocket;
    protected MultiValueMap<String, String> queryParameters = null;
    protected MultiValueMap<String, String> pathParameters = null;
    protected HttpHeaders headers;

    public VertxHttpServerRequest(HttpServerRequest nativeRequest, IdGenerator idGenerator) {
        this.nativeRequest = nativeRequest;
        this.timestamp = System.currentTimeMillis();
        this.id = idGenerator.randomString();
        this.headers = new VertxHttpHeaders(nativeRequest.headers().getDelegate());
        this.metrics = Metrics.on(timestamp).build();
        this.metrics.setRequestId(id());
        this.metrics.setHttpMethod(method());
        this.metrics.setLocalAddress(localAddress());
        this.metrics.setRemoteAddress(remoteAddress());
        this.metrics.setHost(nativeRequest.host());
        this.metrics.setUri(uri());
        this.metrics.setUserAgent(nativeRequest.getHeader(io.vertx.reactivex.core.http.HttpHeaders.USER_AGENT));
        this.chunks =
            nativeRequest
                .toFlowable()
                .doOnNext(buffer -> metrics.setRequestContentLength(metrics.getRequestContentLength() + buffer.length()))
                .map(Buffer::buffer);
    }

    public VertxHttpServerResponse response() {
        return new VertxHttpServerResponse(this);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String transactionId() {
        return this.transactionId;
    }

    @Override
    public MutableRequest transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    @Override
    public String uri() {
        return nativeRequest.uri();
    }

    @Override
    public String path() {
        return nativeRequest.path();
    }

    @Override
    public String pathInfo() {
        return pathInfo;
    }

    @Override
    public MutableRequest pathInfo(final String pathInfo) {
        this.pathInfo = pathInfo;
        return this;
    }

    @Override
    public MutableRequest contextPath(String contextPath) {
        this.contextPath = contextPath;
        this.pathInfo = path().substring((contextPath.length() == 1) ? 0 : contextPath.length() - 1);
        return this;
    }

    @Override
    public String contextPath() {
        return contextPath;
    }

    @Override
    public MultiValueMap<String, String> parameters() {
        if (queryParameters == null) {
            queryParameters = URIUtils.parameters(nativeRequest.uri());
        }

        return queryParameters;
    }

    @Override
    public MultiValueMap<String, String> pathParameters() {
        if (pathParameters == null) {
            pathParameters = new LinkedMultiValueMap<>();
        }

        return pathParameters;
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }

    @Override
    public HttpMethod method() {
        try {
            return HttpMethod.valueOf(nativeRequest.method().name());
        } catch (IllegalArgumentException iae) {
            return HttpMethod.OTHER;
        }
    }

    @Override
    public String scheme() {
        return nativeRequest.scheme();
    }

    @Override
    public HttpVersion version() {
        return HttpVersion.valueOf(nativeRequest.version().name());
    }

    @Override
    public long timestamp() {
        return timestamp;
    }

    @Override
    public String remoteAddress() {
        if (remoteAddress == null) {
            SocketAddress nativeRemoteAddress = nativeRequest.remoteAddress();
            this.remoteAddress = extractAddress(nativeRemoteAddress);
        }
        return remoteAddress;
    }

    @Override
    public MutableRequest remoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    @Override
    public String localAddress() {
        if (localAddress == null) {
            this.localAddress = extractAddress(nativeRequest.localAddress());
        }
        return localAddress;
    }

    private String extractAddress(SocketAddress address) {
        if (address != null) {
            //TODO Could be improve to a better compatibility with geoIP
            int ipv6Idx = address.host().indexOf("%");
            return (ipv6Idx != -1) ? address.host().substring(0, ipv6Idx) : address.host();
        }
        return null;
    }

    @Override
    public SSLSession sslSession() {
        return nativeRequest.sslSession();
    }

    @Override
    public Metrics metrics() {
        return metrics;
    }

    @Override
    public boolean ended() {
        return nativeRequest.isEnded();
    }

    @Override
    public boolean isWebSocket() {
        if (isWebSocket == null) {
            String connectionHeader = nativeRequest.getHeader(io.vertx.reactivex.core.http.HttpHeaders.CONNECTION);
            String upgradeHeader = nativeRequest.getHeader(io.vertx.reactivex.core.http.HttpHeaders.UPGRADE);
            final io.vertx.core.http.HttpVersion httpVersion = nativeRequest.version();
            isWebSocket =
                (httpVersion == io.vertx.core.http.HttpVersion.HTTP_1_0 || httpVersion == io.vertx.core.http.HttpVersion.HTTP_1_1) &&
                WebSocketUtils.isWebSocket(method().name(), connectionHeader, upgradeHeader);
        }
        return isWebSocket;
    }

    @Override
    public WebSocket webSocket() {
        if (isWebSocket() && webSocket == null) {
            webSocket = new VertxWebSocket(nativeRequest);
        }
        return webSocket;
    }

    /**
     * Indicates if the request is a websocket request and the connection has been upgraded (meaning, a websocket connection has been created).
     *
     * @return <code>true</code> if the connection has been upgraded to websocket, <code>false</code> else.
     * @see #webSocket()
     */
    public boolean isWebSocketUpgraded() {
        return webSocket != null && webSocket.upgraded();
    }

    @Override
    public String host() {
        return this.nativeRequest.host();
    }
}
