package eu.ydp.empiria.player.client.module.media;

import java.util.Map;

/**
 * Contains data specific to the media instance (sources, type etc.).
 */
public class BaseMediaConfiguration {
	public enum MediaType {
		AUDIO, VIDEO
	}

	private final Map<String, String> sources;
	private final MediaType mediaType;
	private final int width;
	private final int height;
	private final String poster;
	private final boolean hasTemplate;
	private final String narrationText;
	private final boolean fullScreenTemplate;

	public BaseMediaConfiguration(Map<String, String> sources, MediaType mediaType, String poster, int height, int width, boolean hasTemplate, boolean fullScreenTemplate, String narrationText) {
		super();
		this.sources = sources;
		this.mediaType = mediaType;
		this.poster = poster;
		this.height = height;
		this.width = width;
		this.hasTemplate = hasTemplate;
		this.narrationText = narrationText;
		this.fullScreenTemplate = fullScreenTemplate;
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

	public boolean isFullScreenTemplate() {
		return fullScreenTemplate;
	}

	public String getNarrationText() {
		return narrationText;
	}
}
