package eu.ydp.empiria.player.client.util;

import java.util.Map;

public class SourceUtil {
	public static String getMpegSource(Map<String, String> sources) {
		for (Map.Entry<String, String> src : sources.entrySet()) {
			if (src.getValue().matches(".*mpeg|.*mp4|.*mp3")) {
				return src.getKey();
			}
		}
		return null;
	}
}
