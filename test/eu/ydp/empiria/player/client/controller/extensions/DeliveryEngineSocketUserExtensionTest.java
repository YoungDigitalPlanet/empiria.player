package eu.ydp.empiria.player.client.controller.extensions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngineSocket;
import eu.ydp.empiria.player.client.controller.extensions.DataSourceDataSocketUserExtensionTest.MockDataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEngineSocketUserExtension;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;


public class DeliveryEngineSocketUserExtensionTest extends ExtensionTestBase {

	protected DeliveryEngine de;
	protected DeliveryEngineSocket des;
	
	public void testStateInitialPageToc(){
		testStateInitialPage("\"TOC\"");
	}
	
	public void testStateInitialPageItem(){
		testStateInitialPage("0");
	}
	
	public void testStateInitialPageSummary(){
		testStateInitialPage("\"SUMMARY\"");
	}
	
	protected void testStateInitialPage(String expectedPage){
		PlayerGinjector injector = GWT.create( PlayerGinjector.class );
		de = injector.getDeliveryEngine();
		de.init(JavaScriptObject.createObject());
		Extension ext = new MockDeliveryEngineSocketUserExtension();
		de.setFlowOptions(new FlowOptions(true, true, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
		de.loadExtension(ext);		
		des.setStateString("["+expectedPage+",[[[[],[],0]]],[]]");
		de.load(getAssessmentXMLData(), getItemXMLDatas());
		String stateRetrieved = des.getStateString();
		String currPage = des.getStateString().substring(1,stateRetrieved.indexOf(","));
		assertEquals(expectedPage, currPage);		
	}
	
	protected class MockDeliveryEngineSocketUserExtension extends InternalExtension implements DeliveryEngineSocketUserExtension{

		@Override
		public void init() {
		}

		@Override
		public void setDeliveryEngineSocket(DeliveryEngineSocket d) {
			des = d;
		}
		
	}

}
