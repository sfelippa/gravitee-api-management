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
package io.gravitee.definition.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;

/**
 * @author Guillaume LAMIRAND (guillaume.lamirand at graviteesource.com)
 * @author GraviteeSource Team
 */
public enum ExecutionMode {
    @JsonEnumDefaultValue
    V3("v3"),
    JUPITER("jupiter");

    private static final Map<String, ExecutionMode> BY_LABEL = Map.of(V3.label, V3, JUPITER.label, JUPITER);

    @JsonValue
    private final String label;

    ExecutionMode(final String label) {
        this.label = label;
    }

    @JsonCreator
    public static ExecutionMode fromLabel(final String label) {
        return BY_LABEL.getOrDefault(label, V3);
    }

    public String getLabel() {
        return label;
    }
}
