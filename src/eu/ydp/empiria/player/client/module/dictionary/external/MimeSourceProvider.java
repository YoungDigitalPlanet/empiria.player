package eu.ydp.empiria.player.client.module.dictionary.external;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.empiria.player.client.util.MimeUtil;

public class MimeSourceProvider {
	private static final String dictionaryMediaPath = "dictionary/media/";

	@Inject
	private EmpiriaPaths empiriaPaths;

	@Inject
	private MimeUtil mimeUtil;

	public Map<String, String> getSourcesWithTypes(String fileName) {
		String dictionaryMediaFilePath = dictionaryMediaPath + fileName;
		String path = empiriaPaths.getCommonsFilePath(dictionaryMediaFilePath);

		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		sourcesWithTypes.put(path, mimeUtil.getMimeTypeFromExtension(path));

		return sourcesWithTypes;
	}
}
