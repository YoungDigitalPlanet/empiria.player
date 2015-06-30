package eu.ydp.empiria.player.client.module.colorfill.structure;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "button")
public class ColorButton {

    @XmlAttribute
    private String rgb;

    @XmlValue
    private String description;

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
