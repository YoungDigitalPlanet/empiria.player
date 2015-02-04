package eu.ydp.empiria.player.client.module.slideshow.structure;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "slide")
public class SlideBean {

	@XmlElement
	private AudioBean audio;
	@XmlElement
	private SourceBean source;
	@XmlElement(defaultValue = "")
	private String narration;
	@XmlElement(name = "slideTitle")
	private String title;

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

	public AudioBean getAudio() {
		return audio;
	}

	public void setAudio(AudioBean audio) {
		this.audio = audio;
	}

	public boolean hasAudio() {
		return this.audio != null;
	}
}
