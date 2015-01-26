package eu.ydp.empiria.player.client.module.slideshow.slides;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.slideshow.presenter.*;

@RunWith(GwtMockitoTestRunner.class)
public class SlideshowControllerTest {

	@InjectMocks
	private SlideshowController testObj;
	@Mock
	private SlidesSwitcher slidesSwitcher;
	@Mock
	private SlideshowButtonsPresenter buttonsPresenter;
	@Mock
	private SlideshowPagerPresenter pagerPresenter;
	@Captor
	private ArgumentCaptor<Command> commandCaptor;

	@Test
	public void shouldInitPager() {
		// given
		Widget widget = mock(Widget.class);
		when(pagerPresenter.createPager(anyInt())).thenReturn(widget);

		int slidesSize = 2;

		// when
		Widget result = testObj.initPager(slidesSize);

		// then
		verify(pagerPresenter).setSlideshowController(testObj);
		verify(pagerPresenter).createPager(slidesSize);
		assertThat(result).isEqualTo(widget);
	}

	@Test
	public void shouldDelegateShowSlide_andUpdateButtonsStyle() {
		// given
		int slideIndexToShow = 2;

		// when
		testObj.showSlide(slideIndexToShow);

		// then
		verify(slidesSwitcher).showSlide(slideIndexToShow);
		verifyEnableButtons();
	}

	@Test
	public void shouldShowPreviousSlide_andSetButtons() {
		// given

		// when
		testObj.showPreviousSlide();

		// then
		verify(slidesSwitcher).showPreviousSlide();
		verifyEnableButtons();
	}

	@Test
	public void shouldShowNextSlide_andSetButtons() {
		// given

		// when
		testObj.showNextSlide();

		// then
		verify(slidesSwitcher).showNextSlide();
		verifyEnableButtons();
	}

	private void verifyEnableButtons() {
		verifyEnableButtons(1);
	}

	private void verifyEnableButtons(int times) {
		boolean canSwitchToNextSlide = slidesSwitcher.canSwitchToNextSlide();
		verify(buttonsPresenter, times(times)).setEnabledNextButton(canSwitchToNextSlide);

		boolean canSwitchToPreviousSlide = slidesSwitcher.canSwitchToPreviousSlide();
		verify(buttonsPresenter, times(times)).setEnabledPreviousButton(canSwitchToPreviousSlide);

		verify(pagerPresenter, times(times)).updateButtons(anyInt());
	}
}
