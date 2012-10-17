package eu.ydp.empiria.player.client.module.components.multiplepair.structure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public abstract class AMultiplePairChoiceBean {

	@XmlAttribute
	private String identifier;
	
	@XmlAttribute
	private int matchMax;
	
	@XmlValue
	private String content;

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
	
}
