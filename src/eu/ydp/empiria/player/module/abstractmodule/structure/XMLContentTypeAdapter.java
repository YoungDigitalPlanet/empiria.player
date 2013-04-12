package eu.ydp.empiria.player.module.abstractmodule.structure;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

public class XMLContentTypeAdapter extends XmlAdapter<String,  XMLContent> {

	@Override
	public XMLContent unmarshal(String value) throws Exception {
		return null;
	}

	@Override
	public String marshal(XMLContent value) throws Exception {
		return null;
	}
}