package eu.ydp.empiria.player.client.overlaytypes;

import com.google.gwt.core.client.JavaScriptObject;

public class OverlayTypesParserMock extends OverlayTypesParser {
    @Override
    public <T extends JavaScriptObject> T get() {
        return null;
    }

    @Override
    public boolean isValidJSON(String json) {
        return true;
    }

    @Override
    public <T extends JavaScriptObject> T get(String json) {
        return null;
    }
}
