package eu.ydp.empiria.player.client.module.connection.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;

@XmlRootElement(name="simpleAssociableChoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleAssociableChoiceBean implements PairChoiceBean {  

	@XmlAttribute
	private String identifier;
	
	@XmlAttribute
	private int matchMax;
	
	@XmlValue
	private String content;
	
	@XmlAttribute
	private boolean fixed;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getMatchMax() {
		return matchMax;
	}

	public void setMatchMax(int matchMax) {
		this.matchMax = matchMax;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
}
