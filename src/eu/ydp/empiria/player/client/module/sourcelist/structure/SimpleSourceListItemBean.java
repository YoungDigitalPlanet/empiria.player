package eu.ydp.empiria.player.client.module.sourcelist.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "simpleSourceListItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleSourceListItemBean {
	@XmlAttribute
	private String alt;

	@XmlValue
	private String value;

	public String getAlt() {
		return alt;
	}

	public String getValue() {
		return value;
	}

}
