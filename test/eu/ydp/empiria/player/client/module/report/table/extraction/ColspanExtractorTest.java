package eu.ydp.empiria.player.client.module.report.table.extraction;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.xml.client.Element;

@RunWith(MockitoJUnitRunner.class)
public class ColspanExtractorTest {

	@InjectMocks
	private ColspanExtractor testObj;

	@Mock
	private Element element;

	@Test
	public void testExtractWhenExists() {
		// given
		int EXPECTED_COLSPAN = 2;
		when(element.getAttribute("colspan")).thenReturn("2");

		// when
		int colspan = testObj.extract(element);

		// then
		assertEquals(colspan, EXPECTED_COLSPAN);
	}

	@Test
	public void testExtractWhenNotExists() {
		// given
		int EXPECTED_COLSPAN = 1;
		when(element.getAttribute("colspan")).thenReturn(null);

		// when
		int colspan = testObj.extract(element);

		// then
		assertEquals(colspan, EXPECTED_COLSPAN);
	}
}
