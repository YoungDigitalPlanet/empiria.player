package eu.ydp.empiria.player.client.util.style;

import java.util.Map;

import com.google.common.base.Strings;

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
}
