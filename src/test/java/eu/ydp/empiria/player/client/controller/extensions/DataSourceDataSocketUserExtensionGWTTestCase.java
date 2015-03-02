package eu.ydp.empiria.player.client.controller.extensions;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;

public class DataSourceDataSocketUserExtensionGWTTestCase extends ExtensionTestGWTTestCase {

	protected DeliveryEngine de;
	protected DataSourceDataSupplier dsds;

	public void testAssessmentTitle() {
		de = initDeliveryEngine(new MockDataSourceDataSocketUserExtension());
		assertEquals("Show player supported functionality", dsds.getAssessmentTitle());

	}

	public void testItemTitle() {
		de = initDeliveryEngine(new MockDataSourceDataSocketUserExtension());
		assertEquals("Interactive text", dsds.getItemTitle(0));

	}

	public void testItemsCount() {
		de = initDeliveryEngine(new MockDataSourceDataSocketUserExtension());
		assertEquals(2, dsds.getItemsCount());

	}

	protected class MockDataSourceDataSocketUserExtension extends InternalExtension implements DataSourceDataSocketUserExtension {

		@Override
		public void init() {
		}

		@Override
		public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
			dsds = supplier;
		}

	}
}
