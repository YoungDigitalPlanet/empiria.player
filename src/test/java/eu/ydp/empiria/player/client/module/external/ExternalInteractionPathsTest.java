package eu.ydp.empiria.player.client.module.external;

import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleStructure;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionPathsTest {

	@InjectMocks
	private ExternalInteractionPaths testObj;
	@Mock
	private EmpiriaPaths empiriaPaths;
	@Mock
	private ExternalInteractionModuleStructure structure;
	@Mock
	private ExternalInteractionModuleBean bean;
	private String src = "src";
	private String media = "media";

	@Before
	public void init() {
		when(structure.getBean()).thenReturn(bean);
		when(bean.getSrc()).thenReturn(src);
		String externalBasePath = media + "/" + src;
		when(empiriaPaths.getMediaFilePath(src)).thenReturn(externalBasePath);
	}

	@Test
	public void shouldReturnPathToFileInExternalModuleFolder() {
		// given
		String fileName = "file";
		String expectedPath = "media/src/file";

		// when
		String pathToFile = testObj.getExternalFilePath(fileName);

		// then
		assertThat(pathToFile).isEqualTo(expectedPath);
	}

	@Test
	public void shouldReturnPathToEntryPoint() {
		// given
		String expectedPath = "media/src/index.html";

		// when
		String pathToEntryPoint = testObj.getExternalEntryPointPath();

		// then
		assertThat(pathToEntryPoint).isEqualTo(expectedPath);
	}
}