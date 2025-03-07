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
package io.gravitee.gateway.jupiter.reactor.processor.responsetime;

import io.gravitee.gateway.jupiter.api.context.RequestExecutionContext;
import io.gravitee.gateway.jupiter.core.processor.Processor;
import io.gravitee.reporter.api.http.Metrics;
import io.reactivex.Completable;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ResponseTimeProcessor implements Processor {

    @Override
    public String getId() {
        return "processor-response-time";
    }

    @Override
    public Completable execute(final RequestExecutionContext ctx) {
        return Completable.fromRunnable(
            () -> {
                Metrics metrics = ctx.request().metrics();
                // Compute response-time and add it to the metrics
                long proxyResponseTimeInMs = System.currentTimeMillis() - metrics.timestamp().toEpochMilli();
                metrics.setStatus(ctx.response().status());
                metrics.setProxyResponseTimeMs(proxyResponseTimeInMs);
                metrics.setProxyLatencyMs(proxyResponseTimeInMs - metrics.getApiResponseTimeMs());
            }
        );
    }
}
