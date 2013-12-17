package eu.ydp.empiria.player.client.module.object;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ObjectElementReader {
	private static final String AUDIO_TYPE_NAME = "audio";
	private static final String AUDIO_PLAYER_TAG_NAME = "audioPlayer";
	private static final String NARRATION_SCRIPT_TAG_NAME = "narrationScript";
	private static final String TYPE_ATTRIBUTE_NAME = "type";

	public String getElementType(final Element element) {
		if (element.getTagName().equals(AUDIO_PLAYER_TAG_NAME)) {
			return AUDIO_TYPE_NAME;
		} else {
			return XMLUtils.getAttributeAsString(element, TYPE_ATTRIBUTE_NAME);
		}
	}

	public String getNarrationText(final Element element) {
		StringBuilder builder = new StringBuilder();
		NodeList nodeList = element.getElementsByTagName(NARRATION_SCRIPT_TAG_NAME);

		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				final String text = XMLUtils.getText((Element) nodeList.item(i));
				builder.append(text).append(' ');
			}
		}

		return builder.toString();
	}

}