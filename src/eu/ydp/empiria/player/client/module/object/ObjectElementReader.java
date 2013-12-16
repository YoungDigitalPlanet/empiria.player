package eu.ydp.empiria.player.client.module.object;

import com.google.gwt.xml.client.Element;

import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ObjectElementReader {

	private static final String TYPE_ATTRIBUTE_NAME = "type";

	public String getElementType(Element element) {
		return XMLUtils.getAttributeAsString(element, TYPE_ATTRIBUTE_NAME);
	}

}