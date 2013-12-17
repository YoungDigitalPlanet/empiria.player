package eu.ydp.empiria.player.client.module.object;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ObjectElementReader {
	private static final String TEMPLATE_TAG_NAME = "template";
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
	
	public Element getDefaultTemplate(final Element element) {
		final NodeList templateNodes = element.getElementsByTagName(TEMPLATE_TAG_NAME);
		
		for (int i = 0; i < templateNodes.getLength(); i++) {
			final Element node = (Element) templateNodes.item(i);
			final String templateType = XMLUtils.getAttributeAsString(node, TYPE_ATTRIBUTE_NAME, "default");
			if ("default".equalsIgnoreCase(templateType)) {
				return node;
			}
		}
		
		return null;
	}

	public Element getFullscreenTemplate(Element element) {
		final NodeList templateNodes = element.getElementsByTagName(TEMPLATE_TAG_NAME);
		
		for (int i = 0; i < templateNodes.getLength(); i++) {
			final Element node = (Element) templateNodes.item(i);
			final String templateType = XMLUtils.getAttributeAsString(node, TYPE_ATTRIBUTE_NAME, "default");
			if ("fullscreen".equalsIgnoreCase(templateType)) {
				return node;
			}
		}
		
		return null;
	}

	public Integer getWidthOrDefault(final Element element, final int defaultValue) {
		int result = XMLUtils.getAttributeAsInt(element, "width");

		if (result == 0) {
			return defaultValue;
		} else {
			return result;
		}
	}

	public Integer getHeightOrDefault(Element element, int defaultValue) {
		int result = XMLUtils.getAttributeAsInt(element, "height");

		if (result == 0) {
			return defaultValue;
		} else {
			return result;
		}
	}
	

}