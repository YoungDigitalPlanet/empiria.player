package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "title")
public class PicturePlayerTitleBean {

	@XmlValue
	private String titleName;

	public String getTitle() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}