package eu.ydp.empiria.player.client.controller;

import com.google.common.base.Strings;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.flow.IStateful;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;
import eu.ydp.gwtutil.client.debug.log.Logger;

import java.util.List;

public class ModulesStateLoader {

    @Inject
    private Logger logger;

    public void setState(JSONArray state, List<IModule> modules) {
        try {
            setStateOnModules(state, modules);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void setStateOnModules(JSONArray state, List<IModule> modules) {
        if (stateExists(state)) {
            JSONObject stateObj = getFirstObjectFromState(state);

            for (int i = 0; i < modules.size(); i++) {

                IModule module = modules.get(i);
                setStateOnModule(stateObj, module);
            }
        }
    }

    private boolean stateExists(JSONArray state) {
        return state.isArray() != null && state.isArray().size() > 0;
    }

    private JSONObject getFirstObjectFromState(JSONArray state) {
        return state.isArray().get(0).isObject();
    }

    private void setStateOnModule(JSONObject stateObj, IModule module) {
        if (moduleIsStatefulAndUnique(module)) {
            String moduleIdentifier = ((IUniqueModule) module).getIdentifier();

            if (identifierExistsInState(moduleIdentifier, stateObj)) {

                JSONValue moduleState = stateObj.get(moduleIdentifier);

                if (moduleStateExists(moduleState)) {
                    ((IStateful) module).setState(moduleState.isArray());
                }
            }
        }
    }

    private boolean identifierExistsInState(String moduleIdentifier, JSONObject stateObj) {
        return !Strings.isNullOrEmpty(moduleIdentifier) && stateObj.containsKey(moduleIdentifier);
    }

    private boolean moduleIsStatefulAndUnique(IModule module) {
        return module instanceof IStateful && module instanceof IUniqueModule;
    }

    private boolean moduleStateExists(JSONValue moduleState) {
        return moduleState != null && moduleState.isArray() != null;
    }
}
