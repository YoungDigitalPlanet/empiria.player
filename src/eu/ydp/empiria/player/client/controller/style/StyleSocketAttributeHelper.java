package eu.ydp.empiria.player.client.controller.style;

import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.util.BooleanUtils;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class StyleSocketAttributeHelper {

	@Inject
	XMLParser xmlParser;

	@Inject
	BooleanUtils booleanUtil;

	private Map<String, String> getStyleValue(StyleSocket styleSocket, String attribute) {
		String xml = "<root><" + attribute + " class=\"" + attribute + "\"/></root>";
		return styleSocket.getStyles((Element) xmlParser.parse(xml).getDocumentElement().getFirstChild());
	}

	public String getString(StyleSocket styleSocket, String nodeName, String attribute) {
		Map<String, String> styles = getStyleValue(styleSocket, nodeName);
		return styles.get(attribute);
	}

	public boolean getBoolean(StyleSocket styleSocket, String nodeName, String attribute) {
		String value = getString(styleSocket, nodeName, attribute);
		return booleanUtil.getBoolean(value);
	}
}
