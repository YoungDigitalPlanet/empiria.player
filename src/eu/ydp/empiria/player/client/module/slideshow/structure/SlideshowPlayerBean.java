package eu.ydp.empiria.player.client.module.slideshow.structure;

import eu.ydp.empiria.player.client.structure.ModuleBean;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "slideshowPlayer")
public class SlideshowPlayerBean extends ModuleBean {

	@XmlElement(name = "slideshow")
	private SlideshowBean slideshowBean;

	public SlideshowBean getSlideshowBean() {
		return slideshowBean;
	}

	public void setSlideshowBean(SlideshowBean slideshowBean) {
		this.slideshowBean = slideshowBean;
	}
}
