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

package eu.ydp.empiria.player.client.module.texteditor.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class TextEditorModelEncoderGWTTestCase extends EmpiriaPlayerGWTTestCase {

    final static String DUMMY_TEXT = "DUMMY_TEXT";
    final TextEditorModel model = new TextEditorModel(DUMMY_TEXT);

    TextEditorModelEncoder testObj = new TextEditorModelEncoder();

    public void testEncoding() throws Exception {
        // given
        String expected = "[\"" + DUMMY_TEXT + "\"]";

        // when
        JSONArray actual = testObj.encodeModel(model);

        // then
        assertEquals(expected, actual.toString());
    }

    public void testDecoding() throws Exception {
        // given
        final String input = "[\"" + DUMMY_TEXT + "\"]";
        JSONArray incomingState = (JSONArray) JSONParser.parseLenient(input);

        // when
        TextEditorModel actual = testObj.decodeModel(incomingState);

        // then
        assertEquals(model, actual);
    }

    public void testEncodeAndDecode() throws Exception {
        // when
        JSONArray encoded = testObj.encodeModel(model);
        TextEditorModel actual = testObj.decodeModel(encoded);

        //then
        assertEquals(model, actual);
    }
}
