package eu.ydp.empiria.player.client.module.slideshow.slides;

import static org.mockito.Mockito.*;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.client.Command;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowButtonsPresenter;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlideshowControllerTest {

	@InjectMocks
	private SlideshowController testObj;
	@Mock
	private SlidesSwitcher slidesSwitcher;
	@Mock
	private SlideshowButtonsPresenter buttonsPresenter;
	@Mock
	private SlideshowTimer timer;
	@Mock
	private SlidesSorter slidesSorter;
	@Captor
	private ArgumentCaptor<Command> commandCaptor;

	@Test
	public void shouldSetSlides_andReset() {
		// given
		List<SlideBean> slides = Lists.newArrayList();

		// when
		testObj.init(slides);

		// then
		InOrder inOrder = inOrder(slidesSorter, slidesSwitcher, buttonsPresenter);
		inOrder.verify(slidesSorter).sortByTime(slides);
		inOrder.verify(slidesSwitcher).setSlides(slides);
		inOrder.verify(slidesSwitcher).reset();
		inOrder.verify(buttonsPresenter).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		inOrder.verify(buttonsPresenter).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
	}

	@Test
	public void shouldStopSlideshow() {
		// given
		
		// when
		testObj.stopSlideshow();
		
		// then
		verify(timer).cancel();
		verify(slidesSwitcher).reset();
		verifyEnableButtons();
	}

	@Test
	public void shouldResetSlideAndPlay_whenIsLastSlide() {
		// given
		int nextTime = 10;
		int currentTime = 5;
		int delay = nextTime - currentTime;
		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(false);
		when(slidesSwitcher.getNextSlideStartTime()).thenReturn(nextTime);
		when(slidesSwitcher.getCurrentSlideStartTime()).thenReturn(currentTime);


		// when
		testObj.playSlideshow();

		// then
		verify(slidesSwitcher).reset();
		verifyEnableButtons();
		verify(timer).schedule(delay);
	}

	@Test
	public void shouldPlayWithoutReset_whenIsNotLastSlide() {
		// given
		int nextTime = 10;
		int currentTime = 5;
		int delay = nextTime - currentTime;
		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(true);
		when(slidesSwitcher.getNextSlideStartTime()).thenReturn(nextTime);
		when(slidesSwitcher.getCurrentSlideStartTime()).thenReturn(currentTime);

		// when
		testObj.playSlideshow();

		// then
		verify(slidesSwitcher, never()).reset();
		verify(timer).schedule(delay);
	}

	@Test
	public void shouldStopTimer() {
		// given

		// when
		testObj.pauseSlideshow();

		// then
		verify(timer).cancel();
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

	@Test
	public void shouldShowNextSlide_andSheduleNext_whenIsNotLastSlide() {
		// given
		int nextTime = 10;
		int currentTime = 9;
		int delay = nextTime - currentTime;
		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(true);
		when(slidesSwitcher.getNextSlideStartTime()).thenReturn(nextTime);
		when(slidesSwitcher.getCurrentSlideStartTime()).thenReturn(currentTime);

		List<SlideBean> slides = Lists.newArrayList();
		testObj.init(slides);

		verify(timer).setCommand(commandCaptor.capture());
		
		// when
		Command command = commandCaptor.getValue();
		command.execute();

		// then
		verify(slidesSwitcher).showNextSlide();
		verifyEnableButtons(2);
		verify(timer).schedule(delay);
	}

	@Test
	public void shouldShowNextSlide_andPause_whenIsLastSlide() {
		// given
		List<SlideBean> slides = Lists.newArrayList();
		testObj.init(slides);

		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(false);
		verify(timer).setCommand(commandCaptor.capture());

		// when
		Command command = commandCaptor.getValue();
		command.execute();

		// then
		verify(slidesSwitcher).showNextSlide();
		verifyEnableButtons(2);
		verify(timer).cancel();
		verify(buttonsPresenter).setPlayButtonDown(false);
	}

	private void verifyEnableButtons() {
		verifyEnableButtons(1);
	}

	private void verifyEnableButtons(int times) {
		boolean canSwitchToNextSlide = slidesSwitcher.canSwitchToNextSlide();
		verify(buttonsPresenter, times(times)).setEnabledNextButton(canSwitchToNextSlide);

		boolean canSwitchToPreviousSlide = slidesSwitcher.canSwitchToPreviousSlide();
		verify(buttonsPresenter, times(times)).setEnabledPreviousButton(canSwitchToPreviousSlide);
	}
}
