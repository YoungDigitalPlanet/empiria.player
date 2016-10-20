package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import javax.inject.Singleton;

@Singleton
public class JsonParserWrapper {

    public JSONValue parse(String value) {
        return JSONParser.parseLenient(value);
    }

}
