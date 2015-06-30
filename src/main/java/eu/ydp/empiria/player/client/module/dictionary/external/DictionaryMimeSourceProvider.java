package eu.ydp.empiria.player.client.module.dictionary.external;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.empiria.player.client.util.MimeUtil;

import java.util.Map;

public class DictionaryMimeSourceProvider {
    private static final String DICTIONARY_MEDIA_PATH = "dictionary/media/";

    @Inject
    private EmpiriaPaths empiriaPaths;

    @Inject
    private MimeUtil mimeUtil;

    public Map<String, String> getSourcesWithTypes(String fileName) {
        String dictionaryMediaFilePath = DICTIONARY_MEDIA_PATH + fileName;
        String path = empiriaPaths.getCommonsFilePath(dictionaryMediaFilePath);

        Map<String, String> sourcesWithTypes = Maps.newHashMap();
        sourcesWithTypes.put(path, mimeUtil.getMimeTypeFromExtension(path));

        return sourcesWithTypes;
    }
}
