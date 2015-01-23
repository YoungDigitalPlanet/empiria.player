package eu.ydp.empiria.player.client.module.slideshow.structure;

import javax.xml.bind.annotation.*;

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
