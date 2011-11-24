package eu.ydp.empiria.player.client.style;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import eu.ydp.empiria.player.client.util.js.JSOModel;

/**
 * Provides access to jscsspStylesheet object from http://glazman.org/JSCSSP/
 * @author MSamujlo
 */
public class JsCssModel extends JavaScriptObject {
	
	protected JsCssModel() {
	}
	
	public final native JsArray<JsCssRule> getCssRules() /*-{
		return this.cssRules;
	}-*/;
	
	/**
	 * Adds style properties to existing JSOModel. All style properties are converted to lowercase.
	 * 
	 * @param selector
	 * @param result
	 * @return JSOModel object[stylename] = stylename
	 */
	public final JSOModel getDeclarationsForSelector( String selector, JSOModel result ) {
		JsArray<JsCssRule> rules = getCssRules();
		int ln = rules.length();
		for (int i=0;i<ln;i++) {
			JsCssRule rule = rules.get( i );
			if (rule.isStyleRule() && rule.getSelector().equals(selector)) {
				JsArray<JsCssDeclaration> declarations = rule.getDeclarations();
				int dln = declarations.length();
				for (int j=0;j<dln;j++) {
					JsCssDeclaration declaration = declarations.get(j);
					result.set( declaration.getProperty(), declaration.getValue() );
				}
			}
		}
		return result;
	};
	
	/**
	 * Returns map of style properties. All style properties are converted to lowercase.
	 * 
	 * @param selector 
	 * @return JSOModel object[stylename] = stylevalue 
	 */
	public final JSOModel getDeclarationsForSelector( String selector ) {
		JSOModel result = createObject().cast();
		return getDeclarationsForSelector(selector, result);
	};

}
