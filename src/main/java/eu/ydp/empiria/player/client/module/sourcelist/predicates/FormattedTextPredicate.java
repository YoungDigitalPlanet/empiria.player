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
import com.google.common.collect.Iterables;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.module.ModuleTagName;

import java.util.Arrays;
import java.util.List;

public class FormattedTextPredicate implements Predicate<Element>{
    private static final String BOLD = "b";
    private static final String ITALIC = "i";
    private static final String UNDERLINE = "u";
    private static final String SUB = ModuleTagName.SUB.toString();
    private static final String SUP = ModuleTagName.SUP.toString();
    private static final String MATH_JAX = ModuleTagName.INLINE_MATH_JAX.toString();
    private static final String SPAN = ModuleTagName.SPAN.toString();
    private static final String END_OF_LINE = "br";

    private static final List<String> formatters = Arrays.asList(BOLD, ITALIC, UNDERLINE, SUB, SUP, MATH_JAX, SPAN, END_OF_LINE);

    @Override
    public boolean apply(Element element) {
        Predicate<String> hasFormatter = hasFormatter(element);
        return Iterables.any(formatters, hasFormatter);
    }


    private Predicate<String> hasFormatter(final Element element) {
        return new Predicate<String>() {
            @Override
            public boolean apply(String formatter) {
                NodeList elements = element.getElementsByTagName(formatter);
                return elements.getLength() > 0;
            }
        };
    }
}
