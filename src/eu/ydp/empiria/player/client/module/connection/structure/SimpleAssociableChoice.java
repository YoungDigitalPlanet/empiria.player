package eu.ydp.empiria.player.client.module.connection.structure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="simpleAssociableChoice")
public class SimpleAssociableChoice {
	
	@XmlAttribute
	private boolean fixed;
	
	@XmlAttribute
	private String identifier;
	
	@XmlAttribute
	private int matchMax;
	
	@XmlValue
	private String content;

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

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
