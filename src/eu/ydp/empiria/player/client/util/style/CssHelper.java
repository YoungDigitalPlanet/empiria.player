package eu.ydp.empiria.player.client.util.style;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style;

public class CssHelper {

	private String prepareValue(String value) {
		return Strings.nullToEmpty(value).replaceAll("[ ;]+", ""); // NOPMD
	}

	public boolean checkIfEquals(Map<String, String> styles, String attributeName, String attributeValue) {
		boolean status = false;
		if (styles.containsKey(attributeName)) {
			String fromApplication = prepareValue(attributeValue);
			String fromCss = prepareValue(styles.get(attributeName));
			status = fromApplication.equalsIgnoreCase(fromCss);
		}
		return status;
	}

	public boolean checkIfEquals(Style styles, String attributeName, String attributeValue) {
		boolean status = false;
		String value = styles.getProperty(attributeName);
		if (value != null) {
			String fromApplication = prepareValue(attributeValue);
			String fromCss = prepareValue(value);
			status = fromApplication.equalsIgnoreCase(fromCss);
		}
		return status;
	}

	public native Style getComputedStyle(JavaScriptObject element)/*-{
																	try {
																	return $wnd.getComputedStyle(element);
																	} catch (e) {
																	return element.style;
																	}
																	}-*/;
}
