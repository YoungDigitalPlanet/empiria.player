package eu.ydp.empiria.player.client.module.slideshow.slides;

import static org.mockito.Mockito.*;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.client.Command;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlidesController.Presenter;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlidesControllerImplTest {

	@InjectMocks
	private SlidesControllerImpl testObj;
	@Mock
	private SlidesSwitcher slidesSwitcher;
	@Mock
	private Presenter presenter;
	@Mock
	private SlideshowTimer timer;
	@Mock
	private SlidesSorter slidesSorter;
	@Captor
	private ArgumentCaptor<Command> commandCaptor;

	@Before
	public void init() {
		testObj.setPresenter(presenter);
	}

	@Test
	public void shouldSetSlides_andReset() {
		// given
		List<SlideBean> slides = Lists.newArrayList();

		// when
		testObj.setSlides(slides);

		// then
		InOrder inOrder = inOrder(slidesSorter, slidesSwitcher, presenter);
		inOrder.verify(slidesSorter).sortByTime(slides);
		inOrder.verify(slidesSwitcher).setSlides(slides);
		inOrder.verify(slidesSwitcher).reset();
		inOrder.verify(presenter).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		inOrder.verify(presenter).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
	}

	@Test
	public void shouldStopSlideshow() {
		// given
		
		// when
		testObj.stopSlideshow();
		
		// then
		verify(timer).cancel();
		verify(slidesSwitcher).reset();
		verify(presenter).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		verify(presenter).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
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
		verify(presenter).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		verify(presenter).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
		verify(timer).schedule(delay);
	}

	@Test
	public void shouldOnlyPlay_whenIsNotLastSlide() {
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
		verify(presenter, never()).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		verify(presenter, never()).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
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
		verify(presenter).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		verify(presenter).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
	}

	@Test
	public void shouldShowNextSlide_andSetButtons() {
		// given

		// when
		testObj.showNextSlide();

		// then
		verify(slidesSwitcher).showNextSlide();
		verify(presenter).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		verify(presenter).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
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

		verify(timer).setCommand(commandCaptor.capture());
		
		// when
		Command command = commandCaptor.getValue();
		command.execute();

		// then
		verify(slidesSwitcher).showNextSlide();
		verify(presenter).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		verify(presenter).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
		verify(timer).schedule(delay);
	}

	@Test
	public void shouldShowNextSlide_andPause_whenIsLastSlide() {
		// given

		when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(false);
		verify(timer).setCommand(commandCaptor.capture());

		// when
		Command command = commandCaptor.getValue();
		command.execute();

		// then
		verify(slidesSwitcher).showNextSlide();
		verify(presenter).setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		verify(presenter).setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
		verify(timer).cancel();
		verify(presenter).setPlayButtonDown(false);
	}
}
