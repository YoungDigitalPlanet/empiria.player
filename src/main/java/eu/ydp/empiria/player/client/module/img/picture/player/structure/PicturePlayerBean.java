package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import eu.ydp.empiria.player.client.structure.ModuleBean;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "img")
public class PicturePlayerBean extends ModuleBean {

	@XmlElement
	private ImgTitleBean imgTitleBean;
	@XmlAttribute
	private String src;
	@XmlAttribute
	private String srcFullScreen;
	@XmlAttribute
	private String fullscreenMode;

	public ImgTitleBean getImgTitleBean() {
		return imgTitleBean;
	}

	public void setImgTitleBean(ImgTitleBean imgTitleBean) {
		this.imgTitleBean = imgTitleBean;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSrcFullScreen() {
		return srcFullScreen;
	}

	public void setSrcFullScreen(String srcFullScreen) {
		this.srcFullScreen = srcFullScreen;
	}

	public String getFullscreenMode() {
		return fullscreenMode;
	}

	public void setFullscreenMode(String fullscreenMode) {
		this.fullscreenMode = fullscreenMode;
	}
}
