package eu.ydp.empiria.player.client.module.drawing.model;

import eu.ydp.empiria.player.client.structure.ModuleBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "drawing")
@XmlAccessorType(XmlAccessType.NONE)
public class DrawingBean extends ModuleBean {
    @XmlElement(name = "palette")
    private PaletteBean palette;
    @XmlElement(name = "image")
    private ImageBean image;

    public PaletteBean getPalette() {
        return palette;
    }

    public void setPalette(PaletteBean palette) {
        this.palette = palette;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }

}
