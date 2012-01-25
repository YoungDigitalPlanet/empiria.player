package eu.ydp.empiria.player.client.controller.extensions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class DataSourceDataSocketUserExtensionTest extends ExtensionTestBase {

	protected DeliveryEngine de;
	protected DataSourceDataSupplier dsds;
	
	public void testAssessmentTitle(){
		de = initDeliveryEngine(new MockDataSourceDataSocketUserExtension());
		assertEquals("Show player supported functionality", dsds.getAssessmentTitle());
				
	}
	
	public void testItemTitle(){
		de = initDeliveryEngine(new MockDataSourceDataSocketUserExtension());
		assertEquals("Interactive text", dsds.getItemTitle(0));
				
	}
	
	public void testItemsCount(){
		de = initDeliveryEngine(new MockDataSourceDataSocketUserExtension());
		assertEquals(2, dsds.getItemsCount());
				
	}
	
	protected class MockDataSourceDataSocketUserExtension extends InternalExtension implements DataSourceDataSocketUserExtension{

		@Override
		public void init() {
		}
		@Override
		public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
			dsds = supplier;
		}
		
	}
}
