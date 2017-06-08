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

package eu.ydp.empiria.player.client.module.selection.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class SelectionModuleStructure extends AbstractModuleStructure<SelectionInteractionBean, SelectionModuleJAXBParser> {

    @Inject
    private SelectionModuleJAXBParser parserFactory;
    @Inject
    private XMLParser xmlParser;
    @Inject
    private ShuffleHelper shuffleHelper;
    @Inject
    private IJSONService ijsonService;

    @Override
    protected SelectionModuleJAXBParser getParserFactory() {
        return parserFactory;
    }

    @Override
    protected void prepareStructure(YJsonArray structure) {
        shuffleHelper.randomizeIfShould(bean, bean.getItems());
    }

    @Override
    protected XMLParser getXMLParser() {
        return xmlParser;
    }

    @Override
    protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
        return xmlDocument.getElementsByTagName(EmpiriaTagConstants.NAME_SIMPLE_CHOICE);
    }

    @Override
    public YJsonArray getSavedStructure() {
        return ijsonService.createArray();
    }

}
