package eu.ydp.empiria.player.client.module.media;

import java.util.HashMap;
import java.util.Map;

public class BaseMediaConfiguration {
	public enum MediaType {
		AUDIO, VIDEO
	}

	Map<String, String> sources = new HashMap<String, String>();
	MediaType mediaType = MediaType.AUDIO;
	private int width;
	private int height;
	private String poster;
	private boolean hasTemplate = false;

	public BaseMediaConfiguration(Map<String, String> sources, MediaType mediaType, String poster, int height, int width, boolean hasTemplate) {
		super();
		this.sources = sources;
		this.mediaType = mediaType;
		this.poster = poster;
		this.height = height;
		this.width = width;
		this.hasTemplate = hasTemplate;
	}

	public Map<String, String> getSources() {
		return sources;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getPoster() {
		return poster;
	}

	public boolean isTemplate() {
		return hasTemplate;
	}
}
