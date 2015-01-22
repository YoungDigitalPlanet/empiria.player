package eu.ydp.empiria.player.client.module.media;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.MimeUtil;

public class MimeSourceProvider {

	@Inject
	private MimeUtil mimeUtil;

	public Map<String, String> getSourcesWithTypeByExtension(String src) {
		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		String mimeType = mimeUtil.getMimeTypeFromExtension(src);
		sourcesWithTypes.put(src, mimeType);

		return sourcesWithTypes;
	}
}
