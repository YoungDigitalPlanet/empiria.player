package eu.ydp.empiria.player.client.controller.extensions;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;

public class StatefulExtensionGWTTestCase extends ExtensionGWTTestCase {

    protected DeliveryEngine de;
    protected boolean passed1 = false;
    protected boolean passed2 = false;

    public void testExtensionState() {

        PlayerGinjector injector = PlayerGinjectorFactory.getNewPlayerGinjectorForGWTTestCase();
        de = injector.getDeliveryEngine();
        de.init(JavaScriptObject.createObject());
        de.setFlowOptions(new FlowOptions(false, false, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
        de.loadExtension(new MockStatefulExtension());
        de.load(getAssessmentXMLData(), getItemXMLDatas());
        String stateX = de.getStateString();
        checkGetState(stateX);

        de = injector.getDeliveryEngine();
        de.init(JavaScriptObject.createObject());
        de.setFlowOptions(new FlowOptions(false, false, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
        de.loadExtension(new MockStatefulExtension());
        de.setStateString(stateX);
        de.load(getAssessmentXMLData(), getItemXMLDatas());

        assertTrue(passed1);
        assertTrue(passed2);
    }

    protected void checkSetState(JSONArray newState) {
        assertTrue(newState.isArray() != null);
        assertEquals(1, newState.isArray().size());
        assertTrue(newState.isArray().get(0).isString() != null);
        assertEquals("X78675764320934897982", newState.isArray().get(0).isString().stringValue());
        passed1 = true;
    }

    protected void checkGetState(String stateString) {
        assertTrue(stateString.contains("X78675764320934897982"));
        passed2 = true;
    }

    protected class MockStatefulExtension extends InternalExtension implements StatefulExtension {

        @Override
        public void init() {

        }

        @Override
        public JSONArray getState() {
            JSONArray arr = new JSONArray();
            arr.set(0, new JSONString("X78675764320934897982"));
            return arr;
        }

        @Override
        public void setState(JSONArray newState) {
            checkSetState(newState);
        }

    }
}
