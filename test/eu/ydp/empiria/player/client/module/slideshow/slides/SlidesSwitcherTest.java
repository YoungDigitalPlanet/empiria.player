package eu.ydp.empiria.player.client.module.slideshow.slides;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlidesSwitcherTest {

	private SlidesSwitcher testObj;
	@Mock
	private SlidePresenter presenter;
	@Mock
	private SlideBean slide;

	private final List<SlideBean> slides = Lists.newArrayList();

	@Before
	public void init() {
		testObj = new SlidesSwitcher(presenter);
		slides.add(slide);
		testObj.setSlides(slides);
	}

	@Test
	public void shouldNotDecrementCurrentSlideAfterInit() {
		// given
		int startSlide = 0;
		assertThat(testObj.getCurrentSlide()).isEqualTo(startSlide);
		
		// when
		boolean result = testObj.canSwitchToPreviousSlide();
		testObj.showPreviousSlide();

		// then
		assertThat(result).isFalse();
		assertThat(testObj.getCurrentSlide()).isEqualTo(startSlide);
	}

	@Test
	public void shouldDecrementCurrentSlide() {
		// given
		slides.add(slide);
		testObj.showNextSlide();
		assertThat(testObj.getCurrentSlide()).isEqualTo(1);

		// when
		boolean result = testObj.canSwitchToPreviousSlide();
		testObj.showPreviousSlide();

		// then
		assertThat(result).isTrue();
		assertThat(testObj.getCurrentSlide()).isEqualTo(0);
		verify(presenter, times(2)).replaceView(slide);
	}

	@Test
	public void isAbleToSwitchToNextSlide() {
		// given
		assertThat(testObj.getCurrentSlide()).isEqualTo(0);
		slides.add(slide);

		// when
		boolean result = testObj.canSwitchToNextSlide();
		testObj.showNextSlide();

		// then
		assertThat(result).isTrue();
		assertThat(testObj.getCurrentSlide()).isEqualTo(1);
		verify(presenter).replaceView(slide);
	}

	@Test
	public void isNotAbleToSwitchToNextSlide() {
		// given

		// when
		boolean result = testObj.canSwitchToNextSlide();
		testObj.showNextSlide();

		// then
		assertThat(result).isFalse();
		assertThat(testObj.getCurrentSlide()).isEqualTo(0);
	}

	@Test
	public void shouldResetSlidesPosition() {
		// given
		slides.add(slide);
		testObj.showNextSlide();

		// when
		testObj.reset();

		// then
		assertThat(testObj.getCurrentSlide()).isEqualTo(0);
		verify(presenter, times(2)).replaceView(slide);
	}

	@Test
	public void shouldNotReplaceView() {
		// given
		slides.clear();

		// when
		testObj.reset();

		// then
		verifyZeroInteractions(presenter);
	}

	@Test
	public void shouldGetStartTimeOfCurrentSlide() {
		// given
		when(slide.getStartTime()).thenReturn(10);

		// when
		int result = testObj.getCurrentSlideStartTime();

		// then
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void shouldReturnZeroTime_whenEmptyList() {
		// given
		slides.clear();
		when(slide.getStartTime()).thenReturn(10);

		// when
		int result = testObj.getCurrentSlideStartTime();

		// then
		assertThat(result).isEqualTo(0);
	}

	@Test
	public void shouldGetStartTimeOfNextSlide() {
		// given
		slides.add(slide);
		when(slide.getStartTime()).thenReturn(10);

		// when
		int result = testObj.getNextSlideStartTime();

		// then
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void shouldReturnZeroTime_whenIsLastSlide() {
		// given
		when(slide.getStartTime()).thenReturn(10);

		// when
		int result = testObj.getNextSlideStartTime();

		// then
		assertThat(result).isEqualTo(0);
	}
}
