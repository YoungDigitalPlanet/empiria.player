package eu.ydp.empiria.player.client.module.dictionary.external.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.xml.IXMLParser;

public class EntryFactory {
	private static final String TAG_NAME = "word";
	@Inject
	private IXMLParser xmlParser;

	public Entry createEntryFromXMLString(String xmlString, int index) {
		return createElement(xmlString, index);
	}

	private Entry createElement(String response, int index) {
		Document document = xmlParser.parse(response);
		NodeList wordElements = document.getElementsByTagName(TAG_NAME);
		Element word = (Element) wordElements.item(index);

		String from = fetchValue(word, "ang");
		String to = fetchValue(word, "pol");
		String post = fetchValue(word, "post");
		String desc = fetchValue(word, "desc");
		String fromSound = fetchValue(word, "angSound");
		String descSound = fetchValue(word, "descrSound");

		return new Entry(from, to, post, desc, fromSound, descSound);
	}

	private String fetchValue(Element element, String name) {
		return element.getAttribute(name);
	}
}
