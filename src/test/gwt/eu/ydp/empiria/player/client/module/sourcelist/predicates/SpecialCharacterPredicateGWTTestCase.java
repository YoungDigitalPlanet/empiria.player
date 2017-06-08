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

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class SpecialCharacterPredicateGWTTestCase extends EmpiriaPlayerGWTTestCase {
    private SpecialCharacterPredicate testObj = new SpecialCharacterPredicate();

    public void testShouldReturnTrue_whenContainsEscapedLtCharacters() {
        // given
        String escapedText = "<div>escaped &lt; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    public void testShouldReturnTrue_whenContainsEscapedGtCharacters() {
        // given
        String escapedText = "<div>escaped &gt; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    public void testShouldReturnTrue_whenContainsEscapedAmpCharacters() {
        // given
        String escapedText = "<div>escaped &amp; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    public void testShouldReturnTrue_whenContainsEscapedQuotCharacters() {
        // given
        String escapedText = "<div>escaped &quot; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    public void testShouldReturnTrue_whenContainsEscapedAposCharacters() {
        // given
        String escapedText = "<div>escaped &apos; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    private Element parseXml(String content) {
        Document parse = XMLParser.parse(content);
        return parse.getDocumentElement();
    }
}
