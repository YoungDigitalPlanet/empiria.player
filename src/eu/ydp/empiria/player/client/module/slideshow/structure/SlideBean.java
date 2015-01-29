package eu.ydp.empiria.player.client.module.slideshow.structure;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "slide")
public class SlideBean {

	@XmlAttribute
	private int startTime;
	@XmlElement
	private SourceBean source;
	@XmlElement
	private SlideNarrationBean narration;
	@XmlElement
	private SlideTitleBean slideTitle;

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public SourceBean getSource() {
		return source;
	}

	public void setSource(SourceBean src) {
		this.source = src;
	}

	public SlideNarrationBean getNarration() {
		return narration;
	}

	public void setNarration(SlideNarrationBean narration) {
		this.narration = narration;
	}

	public boolean hasNarration() {
		return narration != null;
	}

	public SlideTitleBean getSlideTitle() {
		return slideTitle;
	}

	public void setSlideTitle(SlideTitleBean title) {
		this.slideTitle = title;
	}

	public boolean hasSlideTitle() {
		return slideTitle != null;
	}
}
