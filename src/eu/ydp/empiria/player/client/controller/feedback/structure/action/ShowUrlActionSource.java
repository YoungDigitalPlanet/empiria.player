package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "source")
public class ShowUrlActionSource {

	@XmlAttribute(name = "src")
	private String src;

	@XmlAttribute(name = "type")
	private String type;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ShowUrlActionSource [src=" + src + ", type=" + type + "]";
	}
}
