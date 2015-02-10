package eu.ydp.empiria.player.client.module.slideshow.slides;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.SlideEnd;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.sound.SlideSoundController;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlidesSwitcherTest {

	@InjectMocks
	private SlidesSwitcher testObj;
	@Mock
	private SlidePresenter presenter;
	@Mock
	private SlideBean slide;
	@Mock
	private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
	@Mock
	private SlideSoundController slideSoundController;
	@Mock
	private AudioBean audioBean;

	@Captor
	ArgumentCaptor<List<AudioBean>> captor;

	private final List<SlideBean> slides = Lists.newArrayList();

	@Before
	public void init() {
		slides.add(slide);
		testObj.init(slides, inlineBodyGeneratorSocket);
	}

	@Test
	public void shouldInitSounds() {
		// given
		String audiopath = "test.mp3";
		when(slide.getAudio()).thenReturn(audioBean);
		when(slide.hasAudio()).thenReturn(true);
		when(audioBean.getSrc()).thenReturn(audiopath);

		// when
		testObj.init(slides, inlineBodyGeneratorSocket);

		// than
		verify(slideSoundController).initSound(audiopath);
	}

	@Test
	public void shouldNotDecrementCurrentSlideAfterInit() {
		// given
		boolean firstResult = testObj.canSwitchToPreviousSlide();
		assertThat(firstResult).isFalse();

		// when
		boolean result = testObj.canSwitchToPreviousSlide();
		testObj.showPreviousSlide();

		// than
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
		verify(slideSoundController).stopAllSounds();
	}

	@Test
	public void shouldPauseSlide_whenThereIsAudio() {
		// given
		String audiopath = "test.mp3";
		when(slide.getAudio()).thenReturn(audioBean);
		when(audioBean.getSrc()).thenReturn(audiopath);
		when(slide.hasAudio()).thenReturn(true);

		// when
		testObj.pauseSlide();

		// then
		verify(slideSoundController).pauseSound(audiopath);
	}

	@Test
	public void shouldNotPauseSlide_whenThereIsNoAudio() {
		// given
		when(slide.hasAudio()).thenReturn(false);

		// when
		testObj.pauseSlide();

		// then
		verifyZeroInteractions(slideSoundController);
	}

	@Test
	public void shouldNotPlaySound_whenThereIsNoAudio() {
		// given
		when(slide.hasAudio()).thenReturn(false);
		
		// when
		testObj.playSlide();
		
		// then
		verifyZeroInteractions(slideSoundController);
	}

	@Test
	public void shouldPlaySlide() {
		// given
		String audiopath = "test.mp3";
		when(slide.getAudio()).thenReturn(audioBean);
		when(audioBean.getSrc()).thenReturn(audiopath);
		when(slide.hasAudio()).thenReturn(true);

		// when
		testObj.playSlide();

		// then
		verify(slideSoundController).playSound(eq(audiopath), any(SlideEnd.class));
	}

	@Test
	public void shouldStopAndPlaySlide() {
		// given
		String audiopath = "test.mp3";
		when(slide.getAudio()).thenReturn(audioBean);
		when(audioBean.getSrc()).thenReturn(audiopath);
		when(slide.hasAudio()).thenReturn(true);

		// when
		testObj.stopAndPlaySlide();

		// then
		verify(slideSoundController).playSound(eq(audiopath), any(SlideEnd.class));
		verify(slideSoundController).stopAllSounds();
	}

	@Test
	public void shouldNotReplaceView() {
		// given
		slides.clear();

		// when
		testObj.reset();

		// then
		verify(presenter, only()).setInlineBodyGenerator(inlineBodyGeneratorSocket);
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
	public void shouldNotShowSlide_whenIndexOutOfBounds() {
		// given
		slides.add(slide);
		int slideToShow = 3;

		// when
		testObj.showSlide(slideToShow);

		// then
		verify(presenter, never()).replaceViewData(slide);
	}
}
