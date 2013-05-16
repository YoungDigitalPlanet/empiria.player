package eu.ydp.empiria.player.client.module.ordering.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasFixed;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "simpleChoice")
public class SimpleOrderChoiceBean implements HasFixed {

	@XmlAttribute
	private String identifier;

	@XmlAttribute
	private boolean fixed;

	@XmlValue
	@XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
	private XMLContent content;

	public XMLContent getContent() {
		return content;
	}

	public void setContent(XMLContent content) {
		this.content = content;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
}
