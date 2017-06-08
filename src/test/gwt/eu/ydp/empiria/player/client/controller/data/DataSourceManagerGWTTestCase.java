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

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.ContentPreloader;
import eu.ydp.empiria.player.client.controller.communication.InitialData;
import eu.ydp.empiria.player.client.controller.communication.InitialItemData;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import eu.ydp.empiria.player.client.preloader.view.ProgressBundle;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;

public class DataSourceManagerGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testDataLoadItemsCount() {
        final DataSourceManager dsm = createDataSourceManager();
        dsm.setDataLoaderEventListener(new DataLoaderEventListener() {

            @Override
            public void onDataReady() {
                assertEquals(1, dsm.getItemsCount());
            }

            @Override
            public void onAssessmentLoaded() {

            }
        });

        processLoad(dsm,
                "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");

    }

    public void testDataLoadAssessmentTitle() {
        final DataSourceManager dsm = createDataSourceManager();
        dsm.setDataLoaderEventListener(new DataLoaderEventListener() {

            @Override
            public void onDataReady() {
                assertEquals("Show player supported functionality", dsm.getAssessmentTitle());
            }

            @Override
            public void onAssessmentLoaded() {

            }
        });

        processLoad(dsm,
                "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
    }

    public void testDataLoadItemTitle() {
        final DataSourceManager dsm = createDataSourceManager();
        dsm.setDataLoaderEventListener(new DataLoaderEventListener() {

            @Override
            public void onDataReady() {
                assertEquals("Interactive text", dsm.getItemTitle(0));
            }

            @Override
            public void onAssessmentLoaded() {

            }
        });

        processLoad(dsm,
                "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
    }

    public void testDataLoadInitialData() {
        final DataSourceManager dsm = createDataSourceManager();
        dsm.setDataLoaderEventListener(new DataLoaderEventListener() {

            @Override
            public void onDataReady() {
                InitialData initData = dsm.getInitialData();
                InitialItemData initItemData = initData.getItemInitialData(0);

                assertEquals(1, initItemData.getOutcomes()
                        .size());
                assertEquals("2", initItemData.getOutcomes()
                        .get("TODO").values.get(0));
            }

            @Override
            public void onAssessmentLoaded() {

            }
        });

        processLoad(
                dsm,
                "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><outcomeDeclaration identifier=\"TODO\" cardinality=\"single\" baseType=\"integer\"><defaultValue><value>2</value></defaultValue></outcomeDeclaration><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");

    }

    public void testDataLoadMode() {
        final DataSourceManager dsm = createDataSourceManager();
        dsm.setDataLoaderEventListener(new DataLoaderEventListener() {

            @Override
            public void onDataReady() {
                assertEquals(dsm.getMode(), DataSourceManagerMode.SERVING);
            }

            @Override
            public void onAssessmentLoaded() {

            }
        });

        processLoad(
                dsm,
                "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><outcomeDeclaration identifier=\"TODO\" cardinality=\"single\" baseType=\"integer\"><defaultValue><value>2</value></defaultValue></outcomeDeclaration><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");

    }

    private DataSourceManager createDataSourceManager() {
        ProgressBundle progressBundle = GWT.create(ProgressBundle.class);
        ContentPreloader contentPreloader = new ContentPreloader(progressBundle, new WindowDelegate(), new RootPanelDelegate());
        contentPreloader.setPreloader();
        return new DataSourceManager(new AssessmentDataSourceManager(), new ItemDataSourceCollectionManager(), new StyleDataSourceLoader(), contentPreloader);
    }

    protected void processLoad(DataSourceManager dsm, String itemXml) {

        String assessmentXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><assessmentTest xmlns=\"http://www.ydp.eu/empiria\" identifier=\"RTEST-13\" title=\"Show player supported functionality\"><testPart><assessmentSection identifier=\"sectionA\" title=\"Section A\" visible=\"true\"><assessmentItemRef identifier=\"inline_choice\" href=\"demo/inline_choice.xml\"/></assessmentSection></testPart></assessmentTest>";
        Document assessmentDoc = XMLParser.parse(assessmentXml);
        XmlData assessmentData = new XmlData(assessmentDoc, "");

        Document itemDoc = XMLParser.parse(itemXml);
        XmlData itemData = new XmlData(itemDoc, "");
        XmlData[] itemDatas = new XmlData[1];
        itemDatas[0] = itemData;

        dsm.loadData(assessmentData, itemDatas);
    }
}
