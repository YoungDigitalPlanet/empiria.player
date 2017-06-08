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

package eu.ydp.empiria.player.client.module.expression;

import com.google.common.collect.Lists;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import java.util.List;

public class IdentifiersFromExpressionExtractor {

    private static final String REGEX = "'([^'])*'";
    private static final RegExp PATTERN = RegExp.compile(REGEX, "g");

    public List<String> extractResponseIdentifiersFromTemplate(String template) {
        MatchResult matchResult = PATTERN.exec(template);

        List<String> identifiers = Lists.newArrayList();
        while (matchResult != null) {
            String group = matchResult.getGroup(0);
            String identifier = group.substring(1, group.length() - 1);
            identifiers.add(identifier);

            matchResult = PATTERN.exec(template);
        }

        return identifiers;
    }

}
