package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "slideTitle")
public class SlideTitleBean {

    @XmlValue
    private XMLContent titleValue;

    public XMLContent getTitleValue() {
        return titleValue;
    }

    public void setTitleValue(XMLContent titleValue) {
        this.titleValue = titleValue;
    }
}
