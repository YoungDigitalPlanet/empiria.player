package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.communication.InitialData;
import eu.ydp.empiria.player.client.controller.communication.InitialItemData;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

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
        return new DataSourceManager(new AssessmentDataSourceManager(), new ItemDataSourceCollectionManager());
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
