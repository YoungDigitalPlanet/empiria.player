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

package eu.ydp.empiria.player.client.controller.extensions;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;

public class DataSourceDataSocketUserExtensionGWTTestCase extends ExtensionGWTTestCase {

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
