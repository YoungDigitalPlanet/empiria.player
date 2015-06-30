package eu.ydp.empiria.player.client.module.dictionary;

import eu.ydp.empiria.player.client.module.dictionary.external.DictionaryMimeSourceProvider;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.empiria.player.client.util.MimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryMimeSourceProviderTest {

    @InjectMocks
    private DictionaryMimeSourceProvider testObj;

    @Mock
    private EmpiriaPaths empiriaPaths;

    @Mock
    private MimeUtil mimeUtil;

    @Test
    public void shouldReturnSourceWithTypes() {
        // given
        String fileName = "test.mp3";
        String mimeType = "audio/mp4";
        String dictionaryFilePath = "dictionary/media/" + fileName;

        when(empiriaPaths.getCommonsFilePath(dictionaryFilePath)).thenReturn(dictionaryFilePath);
        when(mimeUtil.getMimeTypeFromExtension(dictionaryFilePath)).thenReturn(mimeType);

        // when
        Map<String, String> result = testObj.getSourcesWithTypes(fileName);

        // then
        assertSame(1, result.size());
        String resultMimeType = result.get(dictionaryFilePath);
        assertEquals(mimeType, resultMimeType);
    }
}
