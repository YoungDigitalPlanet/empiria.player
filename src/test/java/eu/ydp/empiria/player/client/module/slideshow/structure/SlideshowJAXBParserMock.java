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

package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;

public class SlideshowJAXBParserMock implements SlideshowJAXBParser {

    public static final String FULL_SLIDESHOW = "<slideshowPlayer id=\"dummy1_1\"><slideshow><title>title</title>"
            + "<slide><slideTitle>slide <bold>title1</bold></slideTitle>"
            + "<narration>narration1</narration> <source src=\"source1\"/>"
            + "<sound src=\"test1.mp3\"/></slide>"
            + "<slide><slideTitle>slide title2</slideTitle>"
            + "<narration>narration2</narration> <source src=\"source2\"/>"
            + "<sound src=\"test2.mp3\"/></slide>"
            + "</slideshow></slideshowPlayer>";

    public static final String SLIDESHOW_WITHOUT_SOUND = "<slideshowPlayer id=\"dummy1_1\"><slideshow><title>title</title>"
            + "<slide><slideTitle>slide title1</slideTitle>"
            + "<narration>narration1</narration> <source src=\"source1\"/></slide>"
            + "</slideshow></slideshowPlayer>";

    public static final String SLIDESHOW_WITHOUT_NARRATION = "<slideshowPlayer id=\"dummy1_1\"><slideshow><title>title</title>"
            + "<slide><slideTitle>slide title1</slideTitle>"
            + "<source src=\"source1\"/>"
            + "<sound src=\"test1.mp3\"/></slide>"
            + "</slideshow></slideshowPlayer>";

    public static final String FULL_SLIDESHOW_WITHOUT_TITLE = "<slideshowPlayer id=\"dummy1_1\"><slideshow><title/>"
            + "<slide><slideTitle>slide title1</slideTitle>"
            + "<narration>narration1</narration> <source src=\"source1\"/>"
            + "<sound src=\"test1.mp3\"/></slide>"
            + "<slide><slideTitle>slide title2</slideTitle>"
            + "<narration>narration2</narration> <source src=\"source2\"/>"
            + "<sound src=\"test2.mp3\"/></slide>"
            + "</slideshow></slideshowPlayer>";

    public static final String FULL_SLIDESHOW_WITH_TEMPLATE = "<slideshowPlayer id=\"dummy1_1\"><slideshow><title>title</title>"
            + "<slide><slideTitle>slide title1</slideTitle>"
            + "<narration>narration1</narration> <source src=\"source1\"/>"
            + "<sound src=\"test1.mp3\"/></slide>"
            + "<slide><slideTitle>slide title2</slideTitle>"
            + "<narration>narration2</narration> <source src=\"source2\"/>"
            + "<sound src=\"test2.mp3\"/></slide>"
            + "</slideshow><template><slideshowPager/></template></slideshowPlayer>";

    @Override
    public JAXBParser<SlideshowPlayerBean> create() {
        return new JAXBParserImpl<SlideshowPlayerBean>(SlideshowJAXBParser.class);
    }
}
