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

package eu.ydp.empiria.player.client.module.conversion;

import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.YJsonValue;
import eu.ydp.gwtutil.client.service.json.NativeJSONService;
import eu.ydp.gwtutil.json.YNativeJsonFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class StateToStateAndStructureConverterTest {

    private StateToStateAndStructureConverter testObj;

    @Before
    public void before() {
        testObj = new StateToStateAndStructureConverter(new NativeJSONService());
    }

    @Test
    public void convertTest_canConvertIsTrue() {
        YJsonArray yJsonArray = YNativeJsonFactory.createArray();
        YJsonValue result = testObj.convert(yJsonArray);

        assertNotSame(result, yJsonArray);

        yJsonArray.set(0, YNativeJsonFactory.createString("test"));

        result = testObj.convert(yJsonArray);

        assertNotSame(result, yJsonArray);

    }

    @Test
    public void convertTest_canConvertIsFalse() {
        YJsonArray yJsonArray = YNativeJsonFactory.createArray();
        yJsonArray.set(0, YNativeJsonFactory.createObject());
        YJsonValue result = testObj.convert(yJsonArray);

        assertSame(result, yJsonArray);
    }
}
