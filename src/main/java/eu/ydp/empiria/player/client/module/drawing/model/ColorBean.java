package eu.ydp.empiria.player.client.module.drawing.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "color")
public class ColorBean {
	@XmlAttribute
	private String rgb;
	@XmlValue
	private String description;

	public String getDescription() {
		return description;
	}

	public String getRgb() {
		return rgb;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
}
