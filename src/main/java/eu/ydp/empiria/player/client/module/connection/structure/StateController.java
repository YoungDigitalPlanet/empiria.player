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

package eu.ydp.empiria.player.client.module.connection.structure;

import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.connection.InteractionModuleVersionConverter;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.YJsonObject;
import eu.ydp.gwtutil.client.json.YJsonString;
import eu.ydp.gwtutil.client.json.YJsonValue;
import eu.ydp.gwtutil.client.json.js.YJsJsonConverter;
import eu.ydp.gwtutil.client.service.json.IJSONService;

import java.util.ArrayList;
import java.util.List;

public class StateController {

    private final YJsJsonConverter yJsJsonConverter;
    private final IJSONService ijsonService;
    private final InteractionModuleVersionConverter interactionModuleVersionConverter;

    public static final String STRUCTURE = "STRUCTURE";
    public static final String STATE = "STATE";

    @Inject
    public StateController(IJSONService ijsonService, YJsJsonConverter yJsJsonConverter, InteractionModuleVersionConverter interactionModuleVersionConverter) {
        this.ijsonService = ijsonService;
        this.yJsJsonConverter = yJsJsonConverter;
        this.interactionModuleVersionConverter = interactionModuleVersionConverter;
    }

    public YJsonArray saveStructure(List<SimpleMatchSetBean> simpleMatchSetBeans) {
        YJsonArray jsonArray = ijsonService.createArray();
        for (SimpleMatchSetBean simpleMatchSetBean : simpleMatchSetBeans) {
            jsonArray.set(jsonArray.size(), createAssociableChoices(simpleMatchSetBean));
        }
        return jsonArray;
    }

    private YJsonArray createAssociableChoices(SimpleMatchSetBean simpleMatchSetBean) {
        YJsonArray jsonArray = ijsonService.createArray();
        for (SimpleAssociableChoiceBean associableChoiceBean : simpleMatchSetBean.getSimpleAssociableChoices()) {
            String identifier = associableChoiceBean.getIdentifier();
            YJsonString yJsonId = ijsonService.createString(identifier);
            jsonArray.set(jsonArray.size(), yJsonId);
        }

        return jsonArray;
    }

    public boolean isStructureExist(YJsonArray state) {
        return state != null && state.size() != 0;
    }

    public List<SimpleMatchSetBean> loadStructure(YJsonArray structure, List<SimpleMatchSetBean> simpleMatchSets) {

        for (int i = 0; i < simpleMatchSets.size(); i++) {
            SimpleMatchSetBean simpleMatchSetBean = simpleMatchSets.get(i);
            YJsonArray orderedChoicesYJsonArray = structure.get(i).isArray();
            List<SimpleAssociableChoiceBean> orderedChoicesBean = orderChoices(simpleMatchSetBean.getSimpleAssociableChoices(), orderedChoicesYJsonArray);
            simpleMatchSetBean.setSimpleAssociableChoices(orderedChoicesBean);
        }

        return simpleMatchSets;
    }

    public YJsonArray getStructure(YJsonValue yState) {
        YJsonObject object = yState.isArray().get(0).isObject();

        return object.get(STRUCTURE).isArray();
    }

    public JSONArray getResponse(JSONArray state) {

        YJsonArray yState = yJsJsonConverter.toYJson(state);

        return getResponse(yState);
    }

    public JSONArray getResponse(YJsonValue yState) {

        YJsonObject yStateObject = yState.isArray().get(0).isObject();

        YJsonArray response = yStateObject.get(STATE).isArray();

        return yJsJsonConverter.toJson(response);
    }

    private List<SimpleAssociableChoiceBean> orderChoices(List<SimpleAssociableChoiceBean> choiceBeans, YJsonArray orderedChoicesYJsonArray) {
        List<SimpleAssociableChoiceBean> result = new ArrayList<SimpleAssociableChoiceBean>();
        for (int i = 0; i < orderedChoicesYJsonArray.size(); i++) {
            YJsonValue yJsonValue = orderedChoicesYJsonArray.get(i);
            YJsonString choicesYJsonString = yJsonValue.isString();
            String idModule = choicesYJsonString.stringValue();

            result.add(findChoiceBeanById(idModule, choiceBeans));
        }
        return result;
    }

    private SimpleAssociableChoiceBean findChoiceBeanById(String idModule, List<SimpleAssociableChoiceBean> choiceBeans) {
        for (SimpleAssociableChoiceBean simpleAssociableChoiceBean : choiceBeans) {
            if (idModule.equals(simpleAssociableChoiceBean.getIdentifier())) {
                return simpleAssociableChoiceBean;
            }
        }

        throw new IllegalArgumentException("Identifier " + idModule + " not exist");
    }

    public JSONArray getResponseWithStructure(JSONArray state, YJsonArray savedStructure) {

        YJsonObject resultObject = ijsonService.createObject();

        YJsonArray yState = yJsJsonConverter.toYJson(state);

        resultObject.put(STATE, yState);
        resultObject.put(STRUCTURE, savedStructure);
        interactionModuleVersionConverter.exportState(resultObject);

        YJsonArray result = ijsonService.createArray();
        result.set(0, resultObject);
        return yJsJsonConverter.toJson(result);
    }

    public YJsonValue updateStateAndStructureVersion(JSONArray stateAndStructure) {
        YJsonArray yStateAndStructure = yJsJsonConverter.toYJson(stateAndStructure);
        return updateStateAndStructureVersion(yStateAndStructure);
    }

    public YJsonValue updateStateAndStructureVersion(YJsonArray yStateAndStructure) {
        return interactionModuleVersionConverter.importState(yStateAndStructure);
    }
}
