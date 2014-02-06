package eu.ydp.empiria.player.client.util.style;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.dom.client.Style;

import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest(Style.class)
public class CssHelperJUnitTest {

	private Map<String, String> elementStyles = null;

	private final CssHelper instance = new CssHelper();

	@Before
	public void before() {
		elementStyles = new ImmutableMap.Builder<String, String>().put("background", "#79C9FF;").put("font-size", "left").put("color", "white;")
				.put("margin", "margin").put("padding", "2px 8px 4px").put("display", "table-cell").build();
	}

	@Test
	public void shouldNotFindSimilarStyles() {
		ImmutableMap<String, String> notEquals = getNotCorrectStyles();

		for (Map.Entry<String, String> entry : notEquals.entrySet()) {
			assertFalse(instance.checkIfEquals(elementStyles, entry.getKey(), entry.getValue()));
		}
	}

	@Test
	public void shouldFindSimilarStyles() {
		ImmutableMap<String, String> equalStyles = getCorrectStyles();

		for (Map.Entry<String, String> entry : equalStyles.entrySet()) {
			assertTrue(instance.checkIfEquals(elementStyles, entry.getKey(), entry.getValue()));
		}
	}

	@Test
	public void shouldNotFindSimilarStylesNativeStyleObject() {
		ImmutableMap<String, String> equalStyles = getNotCorrectStyles();
		Style style = mock(Style.class);
		for (Map.Entry<String, String> entry : equalStyles.entrySet()) {
			doReturn(entry.getValue()).when(style).getProperty(Matchers.eq(entry.getValue()));
		}

		for (Map.Entry<String, String> entry : equalStyles.entrySet()) {
			assertFalse(instance.checkIfEquals(elementStyles, entry.getKey(), entry.getValue()));
		}
	}

	@Test
	public void shouldFindSimilarStylesNativeStyleObject() {
		ImmutableMap<String, String> equalStyles = getCorrectStyles();
		Style style = mock(Style.class);
		for (Map.Entry<String, String> entry : equalStyles.entrySet()) {
			doReturn(entry.getValue()).when(style).getProperty(Matchers.eq(entry.getValue()));
		}
		for (Map.Entry<String, String> entry : equalStyles.entrySet()) {
			assertTrue(instance.checkIfEquals(elementStyles, entry.getKey(), entry.getValue()));
		}
	}

	private ImmutableMap<String, String> getNotCorrectStyles() {
		ImmutableMap<String, String> notEquals = new ImmutableMap.Builder<String, String>().put("background", "79C9FF;").put("font-size", "right")
				.put("color", "black;").put("margin", "4px").put("padding", "3px 8px 4px").put("display", "table").build();
		return notEquals;
	}

	private ImmutableMap<String, String> getCorrectStyles() {
		ImmutableMap<String, String> equalStyles = new ImmutableMap.Builder<String, String>().put("background", "#79C9FF").put("font-size", " left ")
				.put("color", "white ").put("margin", "margin ").put("padding", " 2px  8px  4px ").put("display", " table-cell;").build();
		return equalStyles;
	}

}
