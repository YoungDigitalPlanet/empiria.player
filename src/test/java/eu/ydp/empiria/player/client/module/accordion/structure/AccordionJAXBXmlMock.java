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

package eu.ydp.empiria.player.client.module.accordion.structure;

public class AccordionJAXBXmlMock {

    public static final String ACCORDION_WITH_TWO_SECTIONS = "<accordion transition=\"all\">\n" +
            "\t<section>\n" +
            "\t<title>Click me1!</title>\n" +
            "\t<content><simpleText>content</simpleText></content>" +
            "\t</section>\n" +
            "\t<section>\n" +
            "\t<title>Click me2!</title>\n" +
            "\t<content><simpleText>content2</simpleText></content>\n" +
            "\t</section>\n" +
            "\t</accordion>";

    public static final String ACCORDION_WITH_TRANSITION_WIDTH = "<accordion transition=\"width\">\n" +
            "\t<section>\n" +
            "\t<title>Click me1!</title>\n" +
            "\t<content><simpleText>content</simpleText></content>" +
            "\t</section>\n" +
            "\t</accordion>";

    public static final String ACCORDION_WITH_TRANSITION_HEIGHT = "<accordion transition=\"height\">\n" +
            "\t<section>\n" +
            "\t<title>Click me1!</title>\n" +
            "\t<content><simpleText>content</simpleText></content>" +
            "\t</section>\n" +
            "\t</accordion>";
}
