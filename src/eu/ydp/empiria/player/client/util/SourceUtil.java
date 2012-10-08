package eu.ydp.empiria.player.client.util;

import java.util.Map;

public final class SourceUtil {
	private SourceUtil() {
	}

	public static String getMpegSource(Map<String, String> sources) {
		for (Map.Entry<String, String> src : sources.entrySet()) {
			if (src.getValue().matches(".*mpeg|.*mp4|.*mp3")) {
				return src.getKey(); // NOPMD
			}
		}
		return null;
	}

	public static boolean containsOgg(Map<String, String> sources) {
		for (Map.Entry<String, String> src : sources.entrySet()) {
			if (src.getValue().matches(".*ogv|.*ogg")) {
				return true;
			}
		}
		return false;
	}
}
