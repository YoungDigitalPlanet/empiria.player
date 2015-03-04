package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.google.gwt.xml.client.Element;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.gwtutil.client.xml.EmptyElement;

public class XmlContentImpl implements XMLContent {

	private String stringContent;
	
	@Override
	public Element getValue() {
		return new EmptyElement("elementName");
	}
	
	public void setStringElement(String str) {
		stringContent = str;
	}

	@Override
	public String toString() {
		return stringContent;
	}
}
