package eu.ydp.empiria.player.client.module.connection.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

@XmlRootElement(name="simpleAssociableChoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleAssociableChoiceBean implements PairChoiceBean {

	@XmlAttribute
	private String identifier;

	@XmlAttribute
	private int matchMax;

	@XmlValue
	@XmlJavaTypeAdapter(value=XMLContentTypeAdapter.class)
	private XMLContent xmlContent ;

	@XmlAttribute
	private boolean fixed;

	@Override
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public int getMatchMax() {
		return matchMax;
	}

	public void setMatchMax(int matchMax) {
		this.matchMax = matchMax;
	}

	@Override
	public XMLContent getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(XMLContent content) {
		this.xmlContent = content;
	}

	@Override
	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
}
