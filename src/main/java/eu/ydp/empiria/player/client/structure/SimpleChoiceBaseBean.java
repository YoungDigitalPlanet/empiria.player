package eu.ydp.empiria.player.client.structure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import eu.ydp.gwtutil.client.StringUtils;

@XmlTransient
public class SimpleChoiceBaseBean {

	@XmlAttribute
	protected String identifier = StringUtils.EMPTY_STRING;;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
