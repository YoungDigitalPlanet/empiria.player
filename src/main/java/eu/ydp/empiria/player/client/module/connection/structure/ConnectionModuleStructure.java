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

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.xml.XMLParser;

import java.util.List;

public class ConnectionModuleStructure extends AbstractModuleStructure<MatchInteractionBean, ConnectionModuleJAXBParser> {

    @Inject
    ConnectionModuleJAXBParser connectionModuleJAXBParser;

    @Inject
    private XMLParser xmlParser;

    @Inject
    private ShuffleHelper shuffleHelper;

    @Inject
    private StateController stateController;
    private YJsonArray savedStructure;

    @Override
    protected ConnectionModuleJAXBParser getParserFactory() {
        return connectionModuleJAXBParser;
    }

    @Override
    protected void prepareStructure(YJsonArray structure) {

        if (stateController.isStructureExist(structure)) {
            List<SimpleMatchSetBean> simpleMatchSets = bean.getSimpleMatchSets();
            bean.setSimpleMatchSets(stateController.loadStructure(structure, simpleMatchSets));

        } else {
            randomizeSets();
        }
        savedStructure = stateController.saveStructure(bean.getSimpleMatchSets());

    }

    private void randomizeSets() {
        List<SimpleMatchSetBean> simpleMatchSets = bean.getSimpleMatchSets();

        for (SimpleMatchSetBean simpleMatchSetBean : simpleMatchSets) {
            simpleMatchSetBean.setSimpleAssociableChoices(randomizeChoices(simpleMatchSetBean.getSimpleAssociableChoices()));
        }
    }

    private List<SimpleAssociableChoiceBean> randomizeChoices(List<SimpleAssociableChoiceBean> associableChoices) {
        return shuffleHelper.randomizeIfShould(bean, associableChoices);
    }

    @Override
    protected XMLParser getXMLParser() {
        return xmlParser;
    }

    @Override
    protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
        return null;
    }

    @Override
    public YJsonArray getSavedStructure() {
        return savedStructure;
    }

}
