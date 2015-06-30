package eu.ydp.empiria.player.client.style;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class JsCssRule extends JavaScriptObject {

    protected JsCssRule() {
    }

    public final native int getType() /*-{
        return this.type;
    }-*/;

    public final boolean isStyleRule() {
        return getType() == JsCssRuleType.STYLE_RULE.type;
    }

    public final native String getSelector() /*-{
        return this.selectorText();
    }-*/;

    public final native JsArray<JsCssDeclaration> getDeclarations() /*-{
        return this.declarations;
    }-*/;

}
