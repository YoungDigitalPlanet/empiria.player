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

package eu.ydp.empiria.player.client.module.object;

import com.google.gwt.xml.client.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static eu.ydp.empiria.player.client.module.object.MockElementFluentBuilder.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ObjectElementReaderTest {

    @InjectMocks
    private ObjectElementReader testObj;

    @Test
    public void shouldReturnType() {
        // given
        final String expected = "elementType";
        Element element = newNode().withTag("any").withAttribute("type", expected).build();

        // when
        String actual = testObj.getElementType(element);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnNarrationTexts() {
        // given
        Element input = narrationTextNode();

        // when
        String actual = testObj.getNarrationText(input);

        // then
        assertEquals("foo bar baz qux ", actual);
    }

    @Test
    public void shouldReturnDefaultTemplate() {
        // given
        Element defaultTemplateElement = newNode().withTag("template").build();
        Element fullscreenTemplateElement = newNode().withTag("template").withAttribute("type", "fullscreen").build();

        Element input = newNode().withChildrenTags("template", defaultTemplateElement, fullscreenTemplateElement).build();

        // when
        Element actual = testObj.getDefaultTemplate(input);

        // then
        assertEquals(defaultTemplateElement, actual);
    }

    @Test
    public void shouldReturnFullscreenTemplate() {
        // given
        Element defaultTemplateElement = newNode().withTag("template").build();
        Element fullscreenTemplateElement = newNode().withTag("template").withAttribute("type", "fullscreen").build();

        Element input = newNode().withChildrenTags("template", defaultTemplateElement, fullscreenTemplateElement).build();

        // when
        Element actual = testObj.getFullscreenTemplate(input);

        // then
        assertEquals(fullscreenTemplateElement, actual);
    }

    @Test
    public void shouldReturnWidthFromAttribute() {
        // given
        Element element = newNode().withAttribute("width", "100").build();

        // when
        int actual = testObj.getWidthOrDefault(element, -100);

        // then
        assertEquals(100, actual);
    }

    @Test
    public void shouldReturnDefaultWidthIfZero() {
        // given
        final int expected = 100;
        Element element = newNode().build();

        // when
        int actual = testObj.getWidthOrDefault(element, expected);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnHeightFromAttribute() {
        // given
        Element element = newNode().withAttribute("height", "100").build();

        // when
        int actual = testObj.getHeightOrDefault(element, -100);

        // then
        assertEquals(100, actual);
    }

    @Test
    public void shouldReturnDefaultHeightIfZero() {
        // given
        final int expected = 100;
        Element element = newNode().build();

        // when
        int actual = testObj.getHeightOrDefault(element, expected);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnPosterFromAttribute() {
        // given
        Element e = newNode().withAttribute("poster", "somePoster").build();

        // when
        String actual = testObj.getPoster(e);

        // then
        assertEquals("somePoster", actual);
    }

    private Element narrationTextNode() {
        Element text1 = newText("foo").build();
        Element text2 = newText("bar").build();
        Element narrationText1 = newNode().withChildren(text1, text2).build();

        Element text3 = newText("baz").build();
        Element text4 = newText("qux").build();
        Element narrationText2 = newNode().withChildren(text3, text4).build();

        Element commentNode = newComment().withValue("someComment").build();

        Element input = newNode().withChildrenTags("narrationScript", narrationText1, narrationText2, commentNode).build();
        return input;
    }

}
