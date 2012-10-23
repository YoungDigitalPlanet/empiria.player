package eu.ydp.empiria.player.client.module.abstractmodule.structure;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

public class XMLContentTypeAdapter extends XmlAdapter<String,  XMLContent> {// implements DomHandler<ElementT, Result> {

	@Override
	public XMLContent unmarshal(String v) throws Exception {
		return null;
	}

	@Override
	public String marshal(XMLContent v) throws Exception {
		return null;
	}
}