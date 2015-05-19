package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "title")
public class ImgTitleBean {

	@XmlValue
	private String titleName;

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
