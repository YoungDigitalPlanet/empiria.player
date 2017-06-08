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
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngineSocket;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEngineSocketUserExtension;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;

public class DeliveryEngineSocketUserExtensionGWTTestCase extends ExtensionGWTTestCase {

    protected DeliveryEngine de;
    protected DeliveryEngineSocket des;

    public void testStateInitialPageToc() {
        testStateInitialPage("\"TOC\"");
    }

    public void testStateInitialPageItem() {
        testStateInitialPage("0");
    }

    public void testStateInitialPageSummary() {
        testStateInitialPage("\"SUMMARY\"");
    }

    protected void testStateInitialPage(String expectedPage) {
        PlayerGinjector injector = PlayerGinjectorFactory.getNewPlayerGinjectorForGWTTestCase();
        de = injector.getDeliveryEngine();
        de.init(JavaScriptObject.createObject());
        Extension ext = new MockDeliveryEngineSocketUserExtension();
        de.setFlowOptions(new FlowOptions(true, true, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
        de.loadExtension(ext);
        des.setStateString("[" + expectedPage + ",[[[[],[],0]]],[]]");
        de.load(getAssessmentXMLData(), getItemXMLDatas());
        String stateRetrieved = des.getStateString();
        String currPage = des.getStateString().substring(1, stateRetrieved.indexOf(","));
        assertEquals(expectedPage, currPage);
    }

    protected class MockDeliveryEngineSocketUserExtension extends InternalExtension implements DeliveryEngineSocketUserExtension {

        @Override
        public void init() {
        }

        @Override
        public void setDeliveryEngineSocket(DeliveryEngineSocket d) {
            des = d;
        }

    }
}
