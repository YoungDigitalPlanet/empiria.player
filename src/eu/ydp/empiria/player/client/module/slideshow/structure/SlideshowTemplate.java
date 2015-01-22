package eu.ydp.empiria.player.client.module.slideshow.structure;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "template")
public class SlideshowTemplate {

	@XmlElement(name = "slideshowPager")
	private String slideshowPager;

	public String getSlideshowPager() {
		return slideshowPager;
	}

	public void setSlideshowPager(String slideshowPager) {
		this.slideshowPager = slideshowPager;
	}
}
