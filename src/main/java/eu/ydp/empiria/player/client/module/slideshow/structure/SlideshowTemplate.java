package eu.ydp.empiria.player.client.module.slideshow.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "template")
public class SlideshowTemplate {

    @XmlElement(name = "slideshowPager")
    private SlideshowPagerBean slideshowPager;

    public SlideshowPagerBean getSlideshowPager() {
        return slideshowPager;
    }

    public void setSlideshowPager(SlideshowPagerBean slideshowPager) {
        this.slideshowPager = slideshowPager;
    }

    public boolean hasSlideshowPager() {
        return slideshowPager != null;
    }
}
