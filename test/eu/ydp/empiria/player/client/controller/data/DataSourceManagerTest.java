package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.communication.InitialData;
import eu.ydp.empiria.player.client.controller.communication.InitialItemData;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class DataSourceManagerTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}
	
	public void testDataLoadItemsCount(){
		final DataSourceManager dsm = new DataSourceManager();
		dsm.setDataLoaderEventListener(new DataLoaderEventListener() {
			
			@Override
			public void onDataReady() {
				assertEquals(1, dsm.getItemsCount());
			}
			
			@Override
			public void onAssessmentLoadingError() {
				fail("Assessment not loaded");
			}
			
			@Override
			public void onAssessmentLoaded() {
								
			}
		});
		
		processLoad(dsm, "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
				
	}

	public void testDataLoadAssessmentTitle(){
		final DataSourceManager dsm = new DataSourceManager();
		dsm.setDataLoaderEventListener(new DataLoaderEventListener() {
			
			@Override
			public void onDataReady() {
				assertEquals("Show player supported functionality", dsm.getAssessmentTitle());
			}
			
			@Override
			public void onAssessmentLoadingError() {
				fail("Assessment not loaded");
			}
			
			@Override
			public void onAssessmentLoaded() {
								
			}
		});
		
		processLoad(dsm, "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");	
	}

	public void testDataLoadItemTitle(){
		final DataSourceManager dsm = new DataSourceManager();
		dsm.setDataLoaderEventListener(new DataLoaderEventListener() {
			
			@Override
			public void onDataReady() {
				assertEquals("Interactive text", dsm.getItemTitle(0));
			}
			
			@Override
			public void onAssessmentLoadingError() {
				fail("Assessment not loaded");
			}
			
			@Override
			public void onAssessmentLoaded() {
								
			}
		});
		
		processLoad(dsm, "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");		
	}

	public void testDataLoadInitialData(){
		final DataSourceManager dsm = new DataSourceManager();
		dsm.setDataLoaderEventListener(new DataLoaderEventListener() {
			
			@Override
			public void onDataReady() {
				InitialData initData = dsm.getInitialData();
				InitialItemData initItemData = initData.getItemInitialData(0);
				
				assertEquals(1, initItemData.getOutcomes().size());
				assertEquals("2", initItemData.getOutcomes().get("TODO").values.get(0));
			}
			
			@Override
			public void onAssessmentLoadingError() {
				fail("Assessment not loaded");
			}
			
			@Override
			public void onAssessmentLoaded() {
								
			}
		});
		
		processLoad(dsm, "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><outcomeDeclaration identifier=\"TODO\" cardinality=\"single\" baseType=\"integer\"><defaultValue><value>2</value></defaultValue></outcomeDeclaration><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
		
	}

	public void testDataLoadMode(){
		final DataSourceManager dsm = new DataSourceManager();
		dsm.setDataLoaderEventListener(new DataLoaderEventListener() {
			
			@Override
			public void onDataReady() {
				assertEquals(dsm.getMode(), DataSourceManagerMode.SERVING);
			}
			
			@Override
			public void onAssessmentLoadingError() {
				fail("Assessment not loaded");
			}
			
			@Override
			public void onAssessmentLoaded() {
								
			}
		});
		
		processLoad(dsm, "<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><outcomeDeclaration identifier=\"TODO\" cardinality=\"single\" baseType=\"integer\"><defaultValue><value>2</value></defaultValue></outcomeDeclaration><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
		
	}

	protected void processLoad(DataSourceManager dsm, String itemXml){

		String assessmentXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><assessmentTest xmlns=\"http://www.ydp.eu/empiria\" identifier=\"RTEST-13\" title=\"Show player supported functionality\"><testPart><assessmentSection identifier=\"sectionA\" title=\"Section A\" visible=\"true\"><assessmentItemRef identifier=\"inline_choice\" href=\"demo/inline_choice.xml\"/></assessmentSection></testPart></assessmentTest>";
		Document assessmentDoc = XMLParser.parse(assessmentXml);
		XMLData assessmentData = new XMLData(assessmentDoc, "");
		
		Document itemDoc = XMLParser.parse(itemXml);
		XMLData itemData = new XMLData(itemDoc, "");
		XMLData[] itemDatas = new XMLData[1];
		itemDatas[0] = itemData;
		
		dsm.loadData(assessmentData, itemDatas);
	}
}
