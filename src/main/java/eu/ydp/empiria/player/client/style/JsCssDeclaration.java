package eu.ydp.empiria.player.client.style;

import com.google.gwt.core.client.JavaScriptObject;

public class JsCssDeclaration extends JavaScriptObject {
    protected JsCssDeclaration() {
    }

    public final native String getProperty() /*-{
        return this.property;
    }-*/;

    public final native String getValue() /*-{
        return this.valueText;
    }-*/;
}
