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

package eu.ydp.empiria.player.client;

import com.google.gwt.junit.GWTMockUtilities;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.gwtutil.client.json.YJsonArray;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.mockito.Mockito.spy;

public abstract class AbstractModuleStructureTestBase<M extends AbstractModuleStructure<B, P>, B extends ModuleBean, P extends JAXBParserFactory<B>> {

    private AbstractModuleStructure<B, P> moduleStructure;

    protected abstract M createModuleStructure();

    @Before
    public void init() {
        moduleStructure = spy(createModuleStructure());
    }

    public B createFromXML(String xmlString, YJsonArray state) {
        moduleStructure.createFromXml(xmlString, state);
        return moduleStructure.getBean();
    }

    @BeforeClass
    public static void prepareTestEnviroment() {
        /**
         * disable GWT.create() behavior for pure JUnit testing
         */
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restoreEnviroment() {
        /**
         * restore GWT.create() behavior
         */
        GWTMockUtilities.restore();
    }

}
