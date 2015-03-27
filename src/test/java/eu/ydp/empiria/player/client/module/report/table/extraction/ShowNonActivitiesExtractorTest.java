package eu.ydp.empiria.player.client.module.report.table.extraction;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_SHOW_NON_ACTIVITES;

import static org.fest.assertions.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.style.StyleSocket;

@RunWith(MockitoJUnitRunner.class)
public class ShowNonActivitiesExtractorTest {

	@InjectMocks
	private ShowNonActivitiesExtractor testobj;

	@Mock
	private StyleSocket styleSocket;
	@Mock
	private Element element;

	@Test
	public void shouldExtractTrue() {
		// given
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EMPIRIA_REPORT_SHOW_NON_ACTIVITES, "true");

		when(styleSocket.getStyles(element)).thenReturn(styles);

		// when
		boolean isShowNonActivities = testobj.extract(element);

		// then
		assertThat(isShowNonActivities).isTrue();
	}

	@Test
	public void shouldExtractFalse() {
		// given
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EMPIRIA_REPORT_SHOW_NON_ACTIVITES, "false");

		when(styleSocket.getStyles(element)).thenReturn(styles);

		// when
		boolean isShowNonActivities = testobj.extract(element);

		// then
		assertThat(isShowNonActivities).isFalse();
	}
}
