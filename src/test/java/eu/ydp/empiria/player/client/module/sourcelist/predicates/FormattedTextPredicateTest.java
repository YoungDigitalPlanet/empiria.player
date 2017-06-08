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
import eu.ydp.empiria.player.client.module.sourcelist.predicates.FormattedTextPredicate;
import eu.ydp.gwtutil.xml.XMLParser;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;


public class FormattedTextPredicateTest {

    private FormattedTextPredicate testObj = new FormattedTextPredicate();

    @Test
    public void shouldReturnFalse_whenIsSimpleText() {
        // given
        String simpleText = "<div>simpleText</div>";
        Element element = parseXml(simpleText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrue_whenContainsBoldText() {
        // given
        String boldText = "<div><b>bold</b> text</div>";
        Element element = parseXml(boldText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsItalicText() {
        // given
        String italicText = "<div><i>italic</i> text</div>";
        Element element = parseXml(italicText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsUnderlineText() {
        // given
        String underlineText = "<div><u>underline</u> text</div>";
        Element element = parseXml(underlineText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsSupText() {
        // given
        String supText = "<div><sup>sup</sup> text</div>";
        Element element = parseXml(supText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsSubText() {
        // given
        String subText = "<div><sub>sub</sub> text</div>";
        Element element = parseXml(subText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsInlineMathJaxText() {
        // given
        String inlineMathJax = "<div><inlineMathJax>sub</inlineMathJax> text</div>";
        Element element = parseXml(inlineMathJax);

        // when
        boolean result = testObj.apply(element);

        // then
        assertThat(result).isTrue();
    }

    private Element parseXml(String content) {
        Document parse = XMLParser.parse(content);
        return parse.getDocumentElement();
    }
}