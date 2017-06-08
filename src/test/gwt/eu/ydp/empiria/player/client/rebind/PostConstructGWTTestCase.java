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

package eu.ydp.empiria.player.client.rebind;

import com.google.gwt.core.client.GWT;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

@SuppressWarnings("PMD")
public class PostConstructGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testSimpleInject() {
        TestGinjector injector = GWT.create(TestGinjector.class);
        SimpleInject simpleInject = injector.getSimpleInject();
        assertTrue(simpleInject.getInject().isPostConstructFire());
    }

    public void testProviderInject() {
        TestGinjector injector = GWT.create(TestGinjector.class);
        ProviderInject inject = injector.getProviderInject();
        assertTrue(inject.getInject().isPostConstructFire());
    }

    public void testFactoryInject() {
        TestGinjector injector = GWT.create(TestGinjector.class);
        SimpleFactory inject = injector.getSimpleFactory();
        assertTrue(inject.getSimpleInject().getInject().isPostConstructFire());
    }

    public void testFactoryAssistedInjectInject() {
        TestGinjector injector = GWT.create(TestGinjector.class);
        SimpleFactory inject = injector.getSimpleFactory();
        assertTrue(inject.getSimpleInject("module").getInject().isPostConstructFire());
    }

    public void testFactoryByInterfaceInject() {
        TestGinjector injector = GWT.create(TestGinjector.class);
        InterfaceFactory inject = injector.getInterfaceFactory();
        assertTrue(inject.getSimpleInject().isPostConstructFire());
    }

    public void testFactoryByInterfaceWithAssistedInject() {
        TestGinjector injector = GWT.create(TestGinjector.class);
        InterfaceFactory inject = injector.getInterfaceFactory();
        assertTrue(inject.getSimpleInject("sss").isPostConstructFire());
    }
}
