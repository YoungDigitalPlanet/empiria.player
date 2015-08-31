package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.common.base.Strings;
import eu.ydp.empiria.player.client.structure.ModuleBean;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "img")
public class PicturePlayerBean extends ModuleBean {

    @XmlElement(name = "title")
    private PicturePlayerTitleBean titleBean;
    @XmlAttribute
    private String alt;
    @XmlAttribute
    private String src;
    @XmlAttribute
    private String srcFullScreen;
    @XmlAttribute
    private String fullscreenMode;

    public PicturePlayerTitleBean getTitleBean() {
        return titleBean;
    }

    public void setTitleBean(PicturePlayerTitleBean titleBean) {
        this.titleBean = titleBean;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
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

    public boolean hasTitle() {
        return titleBean != null;
    }

    public boolean hasAlt() {
        return alt != null;
    }

    public boolean hasFullscreen() {
        return !Strings.isNullOrEmpty(getSrcFullScreen());
    }
}
