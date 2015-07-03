package eu.ydp.empiria.player.client.style;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides access to jscsspStylesheet object from http://glazman.org/JSCSSP/
 *
 * @author MSamujlo
 */
public class StyleSheet {

    private JavaScriptObject styleSheet;

    protected StyleSheet(JavaScriptObject jsCssModel) {
        this.styleSheet = jsCssModel;
    }

    public final native JsArray<JsCssRule> getCssRules(JavaScriptObject styleSheet) /*-{
        return styleSheet.cssRules;
    }-*/;

    /**
     * Adds style properties to existing JSOModel. All style properties are converted to lowercase.
     */
    public final Map<String, String> getDeclarationsForSelectors(List<String> selectors) {
        JsArray<JsCssRule> rules = getCssRules(styleSheet);
        Map<String, String> result = new HashMap<String, String>();
        int ln = rules.length();
        for (int i = 0; i < ln; i++) {
            JsCssRule rule = rules.get(i);
            if (rule.isStyleRule() && selectors.contains(rule.getSelector())) {
                JsArray<JsCssDeclaration> declarations = rule.getDeclarations();
                int dln = declarations.length();
                for (int j = 0; j < dln; j++) {
                    JsCssDeclaration declaration = declarations.get(j);
                    if (declaration.getProperty() != null && declaration.getValue() != null)
                        result.put(declaration.getProperty(), declaration.getValue());
                }
            }
        }
        return result;
    }

    ;

}
