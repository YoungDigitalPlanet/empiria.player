package eu.ydp.empiria.player.client.module.drawing.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "palette")
public class PaletteBean {
	@XmlElement(name = "color")
	private List<ColorBean> colors;

	public List<ColorBean> getColors() {
		return colors;
	}

	public void setColors(List<ColorBean> colors) {
		this.colors = colors;
	}
}
