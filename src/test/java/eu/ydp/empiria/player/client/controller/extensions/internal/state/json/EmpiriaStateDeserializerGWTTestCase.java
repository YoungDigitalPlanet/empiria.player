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

package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateType;

public class EmpiriaStateDeserializerGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private EmpiriaStateDeserializer testObj = new EmpiriaStateDeserializer();

    public void testShouldDeserializeStateWithNewFormat() throws Exception {
        // given
        String givenState = "givenState";

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put(EmpiriaState.STATE, new JSONString(givenState));
        givenStateObject.put(EmpiriaState.TYPE, new JSONString("LZ_GWT"));

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getFormatType(), EmpiriaStateType.LZ_GWT);
        assertEquals(result.getState(), givenState);
    }

    public void testShouldDeserializeStateWithDefaultType_andIdentifier_whenStateIsInOldFormat() throws Exception {
        // given
        String givenOldState = "old state";
        JSONString oldState = new JSONString(givenOldState);

        // when
        EmpiriaState result = testObj.deserialize(oldState);

        // then
        assertEquals(result.getState(), oldState.toString());
        assertEquals(result.getFormatType(), EmpiriaStateType.OLD);
        assertEquals(result.getLessonIdentifier(), "");
    }

    public void testShouldReturnUnknownStateType_whenStateHasNewFormat_butTypeDoesNotExist() {
        // given
        String givenState = "givenState";

        JSONObject newStateObject = new JSONObject();
        newStateObject.put(EmpiriaState.STATE, new JSONString(givenState));
        newStateObject.put(EmpiriaState.TYPE, new JSONString("some unknown state type"));

        // when
        EmpiriaState result = testObj.deserialize(newStateObject);

        // then
        assertEquals(result.getFormatType(), EmpiriaStateType.UNKNOWN);
        assertEquals(result.getState(), givenState);
    }

    public void testShouldDeserializeStateWithDefaultType_whenStateHasNoTypeField() throws Exception {
        // given
        String givenState = "givenState";
        JSONString stateString = new JSONString(givenState);

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put("some_field", stateString);

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getState(), givenStateObject.toString());
    }

    public void testShouldReturnEmptyIdentifier_whenItsMissing() throws Exception {
        // given
        String givenState = "givenState";

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put(EmpiriaState.STATE, new JSONString(givenState));
        givenStateObject.put(EmpiriaState.TYPE, new JSONString("LZ_GWT"));

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getState(), givenState);
        assertEquals(result.getLessonIdentifier(), "");
    }

    public void testShouldReturnIdentifierFromState() throws Exception {
        // given
        String givenState = "givenState";
        String identifier = "identifier";

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put(EmpiriaState.STATE, new JSONString(givenState));
        givenStateObject.put(EmpiriaState.LESSON_IDENTIFIER, new JSONString(identifier));
        givenStateObject.put(EmpiriaState.TYPE, new JSONString("LZ_GWT"));

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getState(), givenState);
        assertEquals(result.getLessonIdentifier(), identifier);
    }
}