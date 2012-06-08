package eu.ydp.empiria.player.client.controller.extensions;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.DeliveryEngineSocketUserExtensionTest.MockDeliveryEngineSocketUserExtension;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public abstract class ExtensionTestBase extends GWTTestCase {

	@Override
	public String getModuleName() {
	    return "eu.ydp.empiria.player.Player";
	}
	
	protected DeliveryEngine initDeliveryEngine(Extension ext){
		return initDeliveryEngine(ext, true);
	}
	
	protected DeliveryEngine initDeliveryEngine(Extension ext, boolean showTocAndSummary){
		List<Extension> exts = new ArrayList<Extension>();
		exts.add(ext);
		return initDeliveryEngine(exts, showTocAndSummary);
	}
	
	protected DeliveryEngine initDeliveryEngine(List<Extension> exts, boolean showTocAndSummary){
		PlayerGinjector injector = GWT.create( PlayerGinjector.class );
		DeliveryEngine de = injector.getDeliveryEngine();
		de.init(JavaScriptObject.createObject());
		de.setFlowOptions(new FlowOptions(showTocAndSummary, showTocAndSummary, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
		for (Extension ext : exts)
			de.loadExtension(ext);
		de.load(getAssessmentXMLData(), getItemXMLDatas());
		return de;
	}
	
	protected XmlData getAssessmentXMLData(){

		String assessmentXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><assessmentTest xmlns=\"http://www.ydp.eu/empiria\" identifier=\"RTEST-13\" title=\"Show player supported functionality\"><testPart><assessmentSection identifier=\"sectionA\" title=\"Section A\" visible=\"true\"><assessmentItemRef identifier=\"inline_choice\" href=\"demo/inline_choice.xml\"/><assessmentItemRef identifier=\"inline_choice\" href=\"demo/inline_choice2.xml\"/></assessmentSection></testPart></assessmentTest>";
		Document assessmentDoc = XMLParser.parse(assessmentXml);
		return new XmlData(assessmentDoc, "");
	}

	protected XmlData[] getItemXMLDatas(){
	
		Document itemDoc = XMLParser.parse("<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
		XmlData itemData = new XmlData(itemDoc, "");
		Document itemDoc2 = XMLParser.parse("<assessmentItem identifier=\"inlineChoice2\" title=\"Interactive text 2\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
		XmlData itemData2 = new XmlData(itemDoc, "");
		
		XmlData[] itemDatas = new XmlData[2];
		itemDatas[0] = itemData;
		itemDatas[1] = itemData2;
		
		return itemDatas;
	}

}
