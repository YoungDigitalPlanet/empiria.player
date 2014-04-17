package eu.ydp.empiria.player.client.module.dictionary.external.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class Entry {

	private final String ang;
	private final String pol;
	private final String post;
	private final String desc;
	private final String angSound;
	private final String descrSound;

	public Entry(Element element) {
		ang = fetchValue(element, "ang", false);
		pol = fetchValue(element, "pol", true);
		post = fetchValue(element, "post", false);
		desc = fetchValue(element, "desc", false);
		angSound = fetchValue(element, "angSound", false);
		descrSound = fetchValue(element, "descrSound", false);
	}

	String fetchValue(Element element, String name, boolean checkChildNode) {
		String value = null;
		if (checkChildNode) {
			NodeList polNodes = element.getElementsByTagName(name);
			if (polNodes.getLength() > 0) {
				value = polNodes.item(0).toString();
				value = value.substring(value.indexOf(">") + 1,
						value.lastIndexOf("</"));
			}
		}
		if (value == null) {
			value = element.getAttribute(name);
		}
		return value;
	}

	public String getAng() {
		return ang;
	}

	public String getPol() {
		return pol;
	}

	public String getPost() {
		return post;
	}

	public String getDesc() {
		return desc;
	}

	public String getAngSound() {
		return angSound;
	}

	public String getDescrSound() {
		return descrSound;
	}
}
