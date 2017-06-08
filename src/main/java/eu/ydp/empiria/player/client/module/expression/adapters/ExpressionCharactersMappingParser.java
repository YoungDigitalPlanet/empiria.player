/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.expression.adapters;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.expression.PipedReplacementsParser;

import java.util.Map;

public class ExpressionCharactersMappingParser {

    private final PipedReplacementsParser expressionReplacementsParser;

    @Inject
    public ExpressionCharactersMappingParser(PipedReplacementsParser expressionReplacementsParser) {
        this.expressionReplacementsParser = expressionReplacementsParser;
    }

    public Map<String, String> parse(String raw) {
        Map<String, String> parts = expressionReplacementsParser.parse(raw);
        return findReplacements(parts);
    }

    private Map<String, String> findReplacements(Map<String, String> parts) {
        Map<String, String> replacements = Maps.newHashMap();
        for (Map.Entry<String, String> part : parts.entrySet()) {
            Map<String, String> mappings = findMappings(part.getKey(), part.getValue());
            replacements.putAll(mappings);
        }
        return replacements;
    }

    private Map<String, String> findMappings(String fromChars, String toChar) {
        Map<String, String> mappings = Maps.newHashMap();
        Iterable<String> fromSplitted = Splitter.on(",").split(fromChars);
        for (String from : fromSplitted) {
            mappings.put(from, toChar);
        }
        return mappings;
    }
}
