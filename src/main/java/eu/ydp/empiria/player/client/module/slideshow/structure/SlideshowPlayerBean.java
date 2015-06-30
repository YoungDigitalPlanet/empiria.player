package eu.ydp.empiria.player.client.module.slideshow.structure;

import eu.ydp.empiria.player.client.structure.ModuleBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "slideshowPlayer")
public class SlideshowPlayerBean extends ModuleBean {

    @XmlElement(name = "slideshow")
    private SlideshowBean slideshowBean;

    @XmlElement(name = "template")
    private SlideshowTemplate template;

    public SlideshowBean getSlideshowBean() {
        return slideshowBean;
    }

    public void setSlideshowBean(SlideshowBean slideshowBean) {
        this.slideshowBean = slideshowBean;
    }

    public SlideshowTemplate getTemplate() {
        return template;
    }

    public void setTemplate(SlideshowTemplate template) {
        this.template = template;
    }

    public boolean hasTemplate() {
        return template != null;
    }
}
