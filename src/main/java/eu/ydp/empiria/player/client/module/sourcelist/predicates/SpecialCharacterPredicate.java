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

package eu.ydp.empiria.player.client.module.sourcelist.predicates;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.gwt.xml.client.Element;

import java.util.Arrays;
import java.util.List;

public class SpecialCharacterPredicate implements Predicate<Element> {

    private static final List<String> characters = Arrays.asList("&lt;", "&gt;", "&amp;", "&quot;", "&apos;");

    @Override
    public boolean apply(Element element) {
        String stringElementValue = element.getChildNodes().toString();
        if (Strings.isNullOrEmpty(stringElementValue)) {
            return false;
        }

        Predicate<String> predicate = containsSpecialCharacter(stringElementValue);
        return Iterables.any(characters, predicate);
    }

    private Predicate<String> containsSpecialCharacter(final String elementValue) {
        return new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return elementValue.contains(input);
            }
        };
    }
}
