package eu.ydp.empiria.player.client.controller;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.flow.StatefulModule;

import java.util.List;

public class ModulesStateLoaderGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private ModulesStateLoader loader;

    final String FIRST_IDENTIFIER = "EDIT_RESPONSE_1";
    final String SECOND_IDENTIFIER = "EDIT_RESPONSE_2";

    final String FIRST_STATE = "[\"1\"]";
    final String SECOND_STATE = "[\"2\"]";

    private ModuleMock firstModule;

    private ModuleMock secondModule;

    private List<IModule> modules;


    interface TestGinjector extends PlayerGinjector {
        ModulesStateLoader getModulesStateLoader();
    }

    @Override
    protected void gwtSetUp() throws Exception {

        TestGinjector injector = GWT.create(TestGinjector.class);
        loader = injector.getModulesStateLoader();

        firstModule = new ModuleMock(FIRST_IDENTIFIER);
        secondModule = new ModuleMock(SECOND_IDENTIFIER);

        modules = Lists.newArrayList((IModule) firstModule, (IModule) secondModule);
    }

    public void testDoesntSetStateOnModulesBecauseIsNull() { // given //
        // given
        JSONArray state = null;

        // when
        loader.setState(state, modules);

        // then
        assertNull(firstModule.getState());
        assertNull(secondModule.getState());
    }

    public void testDoesntSetStateOnModulesBecauseIsEmpty() { // given //
        // given
        String stateString = "{}";

        JSONObject stateObject = getStateObjectByString(stateString);
        JSONArray state = getJSONArrayWithObject(stateObject);

        // when
        loader.setState(state, modules);

        // then
        assertNull(firstModule.getState());
        assertNull(secondModule.getState());
    }

    public void testSetStateOnFirstModuleOnly() { // given //
        // given
        // @formatter:off
        String stateString = "{\"" +
                FIRST_IDENTIFIER + "\":" + FIRST_STATE +
                "}";
        // @formatter:on

        JSONObject stateObject = getStateObjectByString(stateString);
        JSONArray state = getJSONArrayWithObject(stateObject);

        // when
        loader.setState(state, modules);

        // then
        assertEquals(stateObject.get(FIRST_IDENTIFIER).isArray(), firstModule.getState());
        assertNull(secondModule.getState());
    }

    public void testSetStateOnTwoModules() {
        // given
        // @formatter:off
        String stateString = "{\"" +
                FIRST_IDENTIFIER + "\":" + FIRST_STATE + ", \"" +
                SECOND_IDENTIFIER + "\":" + SECOND_STATE +
                "}";
        // @formatter:on

        JSONObject stateObject = getStateObjectByString(stateString);
        JSONArray state = getJSONArrayWithObject(stateObject);

        // when
        loader.setState(state, modules);

        // then
        assertEquals(stateObject.get(FIRST_IDENTIFIER).isArray(), firstModule.getState());
        assertEquals(stateObject.get(SECOND_IDENTIFIER).isArray(), secondModule.getState());
    }

    private JSONObject getStateObjectByString(String stateString) {
        JSONObject stateObject = (JSONObject) JSONParser.parseStrict(stateString);
        return stateObject;
    }

    private JSONArray getJSONArrayWithObject(JSONObject stateObject) {
        JSONArray state = new JSONArray();
        state.set(0, stateObject);
        return state;
    }

    public class ModuleMock implements StatefulModule {

        private final String identifier;
        private JSONArray newState;

        public ModuleMock(String identifier) {
            this.identifier = identifier;
        }

        @Override
        @Deprecated
        public List<IModule> getChildren() {
            return null;
        }

        @Override
        public HasChildren getParentModule() {
            return null;
        }

        @Override
        public List<HasChildren> getNestedParents() {
            return null;
        }

        @Override
        public JSONArray getState() {
            return newState;
        }

        @Override
        public void setState(JSONArray newState) {
            this.newState = newState;
        }

        @Override
        public String getIdentifier() {
            return identifier;
        }
    }
}
