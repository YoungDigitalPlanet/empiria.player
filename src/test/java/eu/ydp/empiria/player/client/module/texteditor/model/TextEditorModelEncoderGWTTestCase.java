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
