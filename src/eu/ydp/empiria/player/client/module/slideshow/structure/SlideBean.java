package eu.ydp.empiria.player.client.module.slideshow.structure;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "slide")
public class SlideBean {

	@XmlAttribute
	private int startTime;
	@XmlElement
	private SourceBean source;
	@XmlElement(defaultValue = "")
	private String narration;
	@XmlElement(name = "slideTitle")
	private String title;

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

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
