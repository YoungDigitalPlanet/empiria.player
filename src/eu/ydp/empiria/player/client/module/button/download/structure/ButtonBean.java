package eu.ydp.empiria.player.client.module.button.download.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.structure.ModuleBean;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "button")
public class ButtonBean extends ModuleBean {
	@XmlAttribute private String alt = "";
	@XmlAttribute private String href = "";

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}


}