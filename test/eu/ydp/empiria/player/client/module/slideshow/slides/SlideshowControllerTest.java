package eu.ydp.empiria.player.client.module.slideshow.slides;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.presenter.*;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

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
	@Mock
	private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
	@Captor
	private ArgumentCaptor<Command> commandCaptor;

	@Test
	public void shouldSetSlides_andReset() {
		// given
		List<SlideBean> slides = Lists.newArrayList();

		// when
		testObj.init(slides, inlineBodyGeneratorSocket);

		// then
		verify(slidesSwitcher).init(slides, inlineBodyGeneratorSocket);
		verify(slidesSwitcher).reset();
		verifyEnableButtons();
	}

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
	public void shouldStopSlideshow() {
		// given

		// when
		testObj.stopSlideshow();

		// then
		verify(slidesSwitcher).reset();
		verifyEnableButtons();
	}

	@Test
	public void shouldResetSlideAndPlay_whenIsLastSlide() {
		// given
		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(false);

		// when
		testObj.playSlideshow();

		// then
		verify(slidesSwitcher).reset();
		verifyEnableButtons();
	}

	@Test
	public void shouldPlayWithoutReset_whenIsNotLastSlide() {
		// given
		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(true);

		// when
		testObj.playSlideshow();

		// then
		verify(slidesSwitcher, never()).reset();
	}

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
