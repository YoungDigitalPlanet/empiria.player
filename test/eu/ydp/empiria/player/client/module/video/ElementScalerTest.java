package eu.ydp.empiria.player.client.module.video;

import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.google.gwt.dom.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ElementScalerTest {

	@InjectMocks
	private ElementScaler testObj;
	@Mock
	private Element element;
	@Mock
	private Style style;
	private final double DELTA = 0.01;

	@Test
	public void shouldClearOldSizesAndSetTopPadding() {
		// given
		int width = 16;
		int height = 9;
		double expectedRatio = 56.25;
		when(element.getStyle()).thenReturn(style);
		when(element.getClientWidth()).thenReturn(width);
		when(element.getClientHeight()).thenReturn(height);

		// when
		testObj.scale(element);

		// then
		verify(style).clearWidth();
		verify(style).clearHeight();

		verify(style).setPaddingTop(eq(expectedRatio, DELTA), eq(Unit.PCT));
		verify(style).setProperty("maxWidth", width, Unit.PX);
	}
}
