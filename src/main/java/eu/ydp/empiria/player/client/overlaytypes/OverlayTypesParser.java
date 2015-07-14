package eu.ydp.empiria.player.client.overlaytypes;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONParser;

public class OverlayTypesParser {
    @SuppressWarnings("unchecked")
    public <T extends JavaScriptObject> T get() {
        return (T) JSONParser.parseLenient("{}").isObject().getJavaScriptObject();
    }

    @SuppressWarnings("unchecked")
    public <T extends JavaScriptObject> T get(String json) {
        return (T) JSONParser.parseStrict(json).isObject().getJavaScriptObject();
    }

    public boolean isValidJSON(String json) {
        boolean valid = true;
        try {
            JSONParser.parseStrict(json);
        } catch (Exception exc) {
            valid = false;
        }
        return valid;
    }
}
