package eu.ydp.empiria.player.client.module.video.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "video")
@XmlAccessorType(XmlAccessType.NONE)
public class VideoBean {

	@XmlAttribute
	private String poster;
	@XmlAttribute(required = true)
	private int width;
	@XmlAttribute(required = true)
	private int height;
	@XmlElement(name = "source", required = true)
	private List<SourceBean> sources;

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<SourceBean> getSources() {
		return sources;
	}

	public void setSources(List<SourceBean> sources) {
		this.sources = sources;
	}
}
