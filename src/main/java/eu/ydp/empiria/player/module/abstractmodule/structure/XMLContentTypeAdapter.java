package eu.ydp.empiria.player.module.abstractmodule.structure;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.google.gwt.xml.client.Element;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.gwtutil.xml.XMLParser;

public class XMLContentTypeAdapter extends XmlAdapter<String, XMLContent> {

	@Override
	public XMLContent unmarshal(final String value) throws Exception {
		return new XMLContent() {

			@Override
			public Element getValue() {
				return XMLParser.parse("<root_from_XMLContentTypeAdapter>" + value + "</root_from_XMLContentTypeAdapter>").getDocumentElement();
			}
		};
	}

	@Override
	public String marshal(XMLContent value) throws Exception {
		return null;
	}
}