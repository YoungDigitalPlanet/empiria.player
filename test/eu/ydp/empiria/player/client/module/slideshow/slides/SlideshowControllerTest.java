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
		InOrder inOrder = inOrder(slidesSwitcher);
		inOrder.verify(slidesSwitcher).init(slides, inlineBodyGeneratorSocket);;
		inOrder.verify(slidesSwitcher).initSounds();

		verify(slidesSwitcher).reset();
		verify(slidesSwitcher).setShowNextSlideCommand(any(Command.class));
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
		verify(slidesSwitcher).stopAndPlaySlide();
		verifyEnableButtons();
	}

	@Test
	public void shouldStopSlideshow() {
		// given

		// when
		testObj.stopSlideshow();

		// then
		verify(slidesSwitcher).reset();
		verify(slidesSwitcher).stopSlide();
		verifyEnableButtons();
		verify(buttonsPresenter).setPlayButtonDown(false);
	}

	@Test
	public void shouldPauseSlideshow() {
		// given

		// when
		testObj.pauseSlideshow();

		// then
		verify(slidesSwitcher).pauseSlide();
	}

	@Test
	public void shouldPlaySlideshow() {
		// given

		// when
		testObj.playSlideshow();

		// then
		verify(slidesSwitcher).playSlide();

	}

	@Test
	public void shouldResetSlideAndPlay_whenIsLastSlide() {
		// given
		List<SlideBean> slides = Lists.newArrayList();
		testObj.init(slides, inlineBodyGeneratorSocket);
		verify(slidesSwitcher).setShowNextSlideCommand(commandCaptor.capture());
		Command value = commandCaptor.getValue();

		when(buttonsPresenter.isPlayButtonDown()).thenReturn(true);
		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(false);

		// when
		value.execute();

		// then
		verify(slidesSwitcher, times(2)).reset();
		verify(slidesSwitcher).stopSlide();
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
		verify(slidesSwitcher).stopAndPlaySlide();
		verifyEnableButtons();
	}

	@Test
	public void shouldShowNextSlide_andSetButtons() {
		// given

		// when
		testObj.showNextSlide();

		// then
		verify(slidesSwitcher).showNextSlide();
		verify(slidesSwitcher).stopAndPlaySlide();
		verifyEnableButtons();
	}

	@Test
	public void shouldNotContinuePlayingSlideshow_whenIsNotPlaying() {
		// given
		List<SlideBean> slides = Lists.newArrayList();
		testObj.init(slides, inlineBodyGeneratorSocket);
		verify(slidesSwitcher).setShowNextSlideCommand(commandCaptor.capture());
		Command value = commandCaptor.getValue();

		when(buttonsPresenter.isPlayButtonDown()).thenReturn(false);

		// when
		value.execute();

		// than
		verify(slidesSwitcher, never()).showNextSlide();
	}

	@Test
	public void shouldContinuePlayingSlideshow_whenIsPlaying() {
		// given
		List<SlideBean> slides = Lists.newArrayList();
		testObj.init(slides, inlineBodyGeneratorSocket);
		verify(slidesSwitcher).setShowNextSlideCommand(commandCaptor.capture());
		Command value = commandCaptor.getValue();

		when(buttonsPresenter.isPlayButtonDown()).thenReturn(true);
		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(true);

		// when
		value.execute();

		// than
		verify(slidesSwitcher).showNextSlide();
		verify(slidesSwitcher).stopAndPlaySlide();
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
