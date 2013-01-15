package eu.ydp.empiria.player.client.util.style;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;

/**
 * Klasa mapujaca atrybuty z plikow css na property JavaScriptObject
 *
 */
public class StyleToPropertyMappingHelper {
	/**
	 * wywolanie funkcji w css musi miec taki suffix
	 */
	private static final String FN_PREFFIX = "fn-";
	private Set<String> functionWhiteList = new HashSet<String>();

	@Inject
	protected NativeStyleHelper nativeStyleHelper;

	/**
	 * dodaje funkcje do listy funkcji jakie mozna wykonywac
	 * @param functionName
	 */
	public void addFunctionToWhiteList(String functionName) {
		functionWhiteList.add(functionName);
	}

	public void setFunctionWhiteList(Set<String> functionWhiteList) {
		this.functionWhiteList = functionWhiteList;
	}

	protected boolean isFunctionSupported(String functionName) {
		return functionWhiteList.contains(functionName);
	}

	public void applyStyles(JavaScriptObject element, Map<String, String> styles) {
		for (Map.Entry<String, String> style : styles.entrySet()) {
			String property = style.getKey();
			if (property.startsWith(FN_PREFFIX)) {
				property = property.substring(3);
				if (isFunctionSupported(property)) {
					nativeStyleHelper.callFunction(element, property, style.getValue());
				} else {
					//Debug.log("function " + property + " not supported");
				}
			} else {
				nativeStyleHelper.applyProperty(element, property, style.getValue());
			}
		}
	}

}
