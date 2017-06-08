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

package eu.ydp.empiria.player.client.inject;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.AbstractTestBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("PMD")
public class InstanceJUnitTest extends AbstractTestBase {

    private static class InstanceToInject {

    }

    private static class ObjectWithInstance {
        @Inject
        Instance<InstanceToInject> instance;

        public InstanceToInject getInstance() {
            return instance.get();
        }
    }

    ObjectWithInstance instance;

    @Before
    public void before() {
        instance = injector.getInstance(ObjectWithInstance.class);
    }

    @Test
    public void testGet() {
        InstanceToInject injectInstance = instance.getInstance();
        InstanceToInject injectInstance2 = instance.getInstance();
        assertTrue(injectInstance == injectInstance2);
    }

}
