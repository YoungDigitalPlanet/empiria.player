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

package eu.ydp.empiria.player.client.module.gap;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.expression.ExpressionReplacer;
import eu.ydp.empiria.player.client.module.expression.PipedReplacementsParser;

import java.util.Map;

public class GapExpressionReplacer {

    @Inject
    private ExpressionReplacer replacer;
    @Inject
    private PipedReplacementsParser parser;

    public void useCharacters(String charactersSet) {
        Map<String, String> replacements = parser.parse(charactersSet);
        replacer.useReplacements(replacements);
    }

    public ExpressionReplacer getReplacer() {
        return replacer;
    }

    public String ensureReplacement(String text) {
        if (replacer.isEligibleForReplacement(text)) {
            return replacer.replace(text);
        }
        return text;
    }

}
