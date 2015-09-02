package eu.ydp.empiria.player.client.module.external.interaction;

import eu.ydp.empiria.player.client.module.external.common.ExternalFolderNameProvider;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalPathsTest {

    @InjectMocks
    private ExternalPaths testObj;
    @Mock
    private ExternalFolderNameProvider externalFolderNameProvider;

    @Before
    public void init() {
        when(externalFolderNameProvider.getExternalFolderName()).thenReturn("src");
        testObj.setExternalFolderNameProvider(externalFolderNameProvider);
    }

    @Test
    public void shouldReturnPathToFileInExternalModuleFolder() {
        // given
        String fileName = "file";
        String expectedPath = "media/src/file";
        when(externalFolderNameProvider.getExternalRelativePath("src/file")).thenReturn(expectedPath);

        // when
        String pathToFile = testObj.getExternalFilePath(fileName);

        // then
        assertThat(pathToFile).isEqualTo(expectedPath);
    }

    @Test
    public void shouldReturnPathToEntryPoint() {
        // given
        String expectedPath = "media/src/index.html";
        when(externalFolderNameProvider.getExternalRelativePath("src/index.html")).thenReturn(expectedPath);

        // when
        String pathToEntryPoint = testObj.getExternalEntryPointPath();

        // then
        assertThat(pathToEntryPoint).isEqualTo(expectedPath);
    }
}
