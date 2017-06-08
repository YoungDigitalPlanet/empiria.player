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

package eu.ydp.empiria.player.client.controller.extensions;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

import java.util.ArrayList;
import java.util.List;

public abstract class ExtensionGWTTestCase extends EmpiriaPlayerGWTTestCase {

    protected DeliveryEngine initDeliveryEngine(Extension ext) {
        return initDeliveryEngine(ext, true);
    }

    protected DeliveryEngine initDeliveryEngine(Extension ext, boolean showTocAndSummary) {
        List<Extension> exts = new ArrayList<Extension>();
        exts.add(ext);
        return initDeliveryEngine(exts, showTocAndSummary);
    }

    protected DeliveryEngine initDeliveryEngine(List<Extension> exts, boolean showTocAndSummary) {
        PlayerGinjector injector = PlayerGinjectorFactory.getNewPlayerGinjectorForGWTTestCase();
        DeliveryEngine de = injector.getDeliveryEngine();
        de.init(JavaScriptObject.createObject());
        de.setFlowOptions(new FlowOptions(showTocAndSummary, showTocAndSummary, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
        for (Extension ext : exts) {
            de.loadExtension(ext);
        }
        de.load(getAssessmentXMLData(), getItemXMLDatas());
        return de;
    }

    protected XmlData getAssessmentXMLData() {

        String assessmentXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><assessmentTest xmlns=\"http://www.ydp.eu/empiria\" identifier=\"RTEST-13\" title=\"Show player supported functionality\"><testPart><assessmentSection identifier=\"sectionA\" title=\"Section A\" visible=\"true\"><assessmentItemRef identifier=\"inline_choice\" href=\"demo/inline_choice.xml\"/><assessmentItemRef identifier=\"inline_choice\" href=\"demo/inline_choice2.xml\"/></assessmentSection></testPart></assessmentTest>";
        Document assessmentDoc = XMLParser.parse(assessmentXml);
        return new XmlData(assessmentDoc, "");
    }

    protected XmlData[] getItemXMLDatas() {

        Document itemDoc = XMLParser
                .parse("<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
        XmlData itemData = new XmlData(itemDoc, "");
        Document itemDoc2 = XMLParser
                .parse("<assessmentItem identifier=\"inlineChoice2\" title=\"Interactive text 2\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
        XmlData itemData2 = new XmlData(itemDoc2, "");

        XmlData[] itemDatas = new XmlData[2];
        itemDatas[0] = itemData;
        itemDatas[1] = itemData2;

        return itemDatas;
    }

}
