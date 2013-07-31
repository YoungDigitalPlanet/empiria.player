package eu.ydp.empiria.player.client.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

@RunWith(MockitoJUnitRunner.class)
public class EmpiriaPathsTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private DataSourceManager dataSourceManager;

	@InjectMocks
	private EmpiriaPaths empiriaPaths;

	private XmlData xmlData;

	@Before
	public void setUp() {
		xmlData = mock(XmlData.class);
		when(dataSourceManager.getAssessmentData().getData()).thenReturn(xmlData);
		when(xmlData.getBaseURL()).thenReturn("http://url/test");
	}

	@Test
	public void shouldGetBasePath() {
		// when
		String basePath = empiriaPaths.getBasePath();

		// then
		verify(dataSourceManager.getAssessmentData()).getData();
		verify(dataSourceManager.getAssessmentData(), never()).getSkinData(); // skin specific data
		assertThat(basePath, is("http://url/test"));
	}

	@Test
	public void testGetCommonsPath() throws Exception {
		// when
		String commonsPath = empiriaPaths.getCommonsPath();

		// then
		assertThat(commonsPath, is("http://url/test/common"));
	}

	@Test
	public void shouldGetFilePath() {
		// given
		String filename = "file.png";

		// when
		String filepath = empiriaPaths.getCommonsFilePath(filename);

		// then
		assertThat(filepath, is("http://url/test/common/file.png"));
	}
}
