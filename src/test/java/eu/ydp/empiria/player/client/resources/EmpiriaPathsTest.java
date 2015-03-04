package eu.ydp.empiria.player.client.resources;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.api.Assertions.assertThat;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class EmpiriaPathsTest {

	@InjectMocks
	private EmpiriaPaths empiriaPaths;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private DataSourceManager dataSourceManager;
	@Mock
	private XmlData xmlData;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(dataSourceManager.getAssessmentData().getData()).thenReturn(xmlData);
	}

	@Test
	@Parameters(method = "baseURLParams")
	public void shouldGetBasePath(String path) {
		// when
		when(xmlData.getBaseURL()).thenReturn(path);
		final String basePath = empiriaPaths.getBasePath();

		// then
		verify(dataSourceManager.getAssessmentData()).getData();
		verify(dataSourceManager.getAssessmentData(), never()).getSkinData(); // skin
																				// specific
																				// data
		assertThat(basePath).isEqualTo("http://url/test/");
	}

	@Test
	@Parameters(method = "baseURLParams")
	public void testGetCommonsPath(String path) throws Exception {
		// when
		when(xmlData.getBaseURL()).thenReturn(path);
		final String commonsPath = empiriaPaths.getCommonsPath();

		// then
		assertThat(commonsPath).isEqualTo("http://url/test/common/");
	}

	@Test
	@Parameters(method = "baseURLParams")
	public void shouldGetFilePath(String path) {
		// given
		when(xmlData.getBaseURL()).thenReturn(path);
		final String filename = "file.png";

		// when
		String filepath = empiriaPaths.getCommonsFilePath(filename);

		// then
		assertThat(filepath).isEqualTo("http://url/test/common/file.png");
	}

	@Test
	@Parameters(method = "baseURLParams")
	public void shouldReturnPathRelativeToCommons(String path) {
		// given
		final String relativePath = "some/dir";

		when(xmlData.getBaseURL()).thenReturn(path);
		final String expected = "http://url/test/common/some/dir/";

		// when
		String actual = empiriaPaths.getPathRelativeToCommons(relativePath);

		// then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Parameters(method = "baseURLParams")
	public void shouldReturnPathRelativeToCommons_trailingSlash(String path) {
		// given
		final String relativePath = "some/dir/";

		when(xmlData.getBaseURL()).thenReturn(path);
		final String expected = "http://url/test/common/some/dir/";

		// when
		String actual = empiriaPaths.getPathRelativeToCommons(relativePath);

		// then
		assertThat(actual).isEqualTo(expected);
	}

	Object[] baseURLParams() {
		return $("http://url/test/", "http://url/test");
	}

}
