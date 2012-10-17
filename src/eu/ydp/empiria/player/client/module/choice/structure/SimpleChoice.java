package eu.ydp.empiria.player.client.module.choice.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import eu.ydp.gwtutil.client.StringUtils;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="simpleChoice")
public class SimpleChoice {

	@XmlAttribute
	private String identifier;
	@XmlAttribute
	private boolean fixed;
	@XmlValue
	private String content;
	private boolean multi;
	
	private SimpleChoice(){
		identifier = StringUtils.EMPTY_STRING;
		content = StringUtils.EMPTY_STRING;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}
	
	
	
}
