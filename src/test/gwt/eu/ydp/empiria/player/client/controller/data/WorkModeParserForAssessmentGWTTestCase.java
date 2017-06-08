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

package eu.ydp.empiria.player.client.controller.data;

import com.google.common.base.Optional;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class WorkModeParserForAssessmentGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private WorkModeParserForAssessment testObj;

    @Override
    protected void gwtSetUp() {
        testObj = new WorkModeParserForAssessment();
    }

    public void test_shouldReturnModeWhenModeIsCorrect() {
        // given
        XmlData xmlData = getXmlData("<assessmentTest compilerVersion=\"3.3.2.117\" mode=\"test\"/>");
        PlayerWorkMode expected = PlayerWorkMode.TEST;

        // when
        Optional<PlayerWorkMode> actual = testObj.parse(xmlData);

        // then
        assertEquals(actual.get(), expected);
    }

    public void test_shouldReturnAbsentWhenModeIsNotCorrect() {
        // given
        XmlData xmlData = getXmlData("<assessmentTest compilerVersion=\"3.3.2.117\" mode=\"not_existing_mode\"/>");

        // when
        Optional<PlayerWorkMode> actual = testObj.parse(xmlData);

        // then
        assertFalse(actual.isPresent());
    }

    public void test_shouldReturnAbsentWhenModeIsNotSpecified() {
        // given
        XmlData xmlData = getXmlData("<assessmentTest compilerVersion=\"3.3.2.117\"/>");

        // when
        Optional<PlayerWorkMode> actual = testObj.parse(xmlData);

        // then
        assertFalse(actual.isPresent());
    }

    private XmlData getXmlData(String source) {
        Document doc = XMLParser.parse(source);
        String url = "url";
        return new XmlData(doc, url);
    }
}
