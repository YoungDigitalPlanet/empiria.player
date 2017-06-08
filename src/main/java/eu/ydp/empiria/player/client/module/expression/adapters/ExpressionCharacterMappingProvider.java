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

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.style.StyleSocket;

import java.util.Map;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_EXPRESSION_MAPPING;

@Singleton
public class ExpressionCharacterMappingProvider {

    public static final String SELECTOR = ".qp-expression-mapping";

    private final StyleSocket styleSocket;

    private final ExpressionCharactersMappingParser parser;

    private Map<String, String> mapping;

    @Inject
    public ExpressionCharacterMappingProvider(StyleSocket styleSocket, ExpressionCharactersMappingParser parser) {
        this.styleSocket = styleSocket;
        this.parser = parser;
    }

    public Map<String, String> getMapping() {
        ensureMappingInitalized();
        return mapping;
    }

    private void ensureMappingInitalized() {
        if (mapping == null) {
            Map<String, String> styles = styleSocket.getStyles(SELECTOR);
            if (styles.containsKey(EMPIRIA_EXPRESSION_MAPPING)) {
                String replacementsRaw = styles.get(EMPIRIA_EXPRESSION_MAPPING);
                mapping = parser.parse(replacementsRaw);
            } else {
                mapping = Maps.newHashMap();
            }
        }
    }

}
