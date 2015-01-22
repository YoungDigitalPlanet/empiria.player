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
		boolean firstResult = testObj.canSwitchToPreviousSlide();
		assertThat(firstResult).isFalse();
		
		// when
		boolean result = testObj.canSwitchToPreviousSlide();
		testObj.showPreviousSlide();

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void shouldDecrementCurrentSlide() {
		// given
		slides.add(slide);
		testObj.showNextSlide();

		// when
		boolean result = testObj.canSwitchToPreviousSlide();
		testObj.showPreviousSlide();

		// then
		assertThat(result).isTrue();
		verify(presenter, times(2)).replaceViewData(slide);
	}

	@Test
	public void isAbleToSwitchToNextSlide() {
		// given
		slides.add(slide);
		boolean canSwitchToPreviousSlide = testObj.canSwitchToPreviousSlide();
		assertThat(canSwitchToPreviousSlide).isFalse();

		// when
		testObj.showNextSlide();
		boolean result = testObj.canSwitchToPreviousSlide();

		// then
		assertThat(result).isTrue();
		verify(presenter).replaceViewData(slide);
	}

	@Test
	public void isNotAbleToSwitchToNextSlide() {
		// given
		slides.add(slide);
		boolean canSwitchToNextSlide = testObj.canSwitchToNextSlide();
		assertThat(canSwitchToNextSlide).isTrue();

		// when
		testObj.showNextSlide();
		boolean result = testObj.canSwitchToNextSlide();

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void shouldResetSlidesPosition() {
		// given
		slides.add(slide);
		testObj.showNextSlide();

		// when
		testObj.reset();
		boolean result = testObj.canSwitchToPreviousSlide();

		// then
		assertThat(result).isFalse();
		verify(presenter, times(2)).replaceViewData(slide);
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
	public void shouldShowSlide() {
		// given
		slides.add(slide);
		int slideToShow = 1;

		// when
		testObj.showSlide(slideToShow);
		int result = testObj.getCurrentSlideIndex();

		// then
		assertThat(result).isEqualTo(slideToShow);
		verify(presenter).replaceViewData(slide);
	}

	@Test
	public void shouldNotShowSlide() {
		// given
		slides.add(slide);
		int slideToShow = 3;

		// when
		testObj.showSlide(slideToShow);

		// then
		verify(presenter, never()).replaceViewData(slide);
	}
}
