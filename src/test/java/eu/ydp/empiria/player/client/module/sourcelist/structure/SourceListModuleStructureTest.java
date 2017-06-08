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

package eu.ydp.empiria.player.client.module.sourcelist.structure;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.gwtutil.client.json.YJsonArray;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.spy;

@SuppressWarnings("PMD")
public class SourceListModuleStructureTest extends AbstractTestBase {

    private SourceListModuleStructure instance;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        instance = spy(injector.getInstance(SourceListModuleStructure.class));
    }

    @Test
    public void getFactoryTestNotNullTest() {
        assertNotNull(instance.getParserFactory());
    }

    @Test
    public void parseBeanTestNotNullCheck() {
        YJsonArray state = Mockito.mock(YJsonArray.class);

        instance.createFromXml(SourceListJAXBParserMock.XML, state);
        assertNotNull(instance.getBean());
    }
}
