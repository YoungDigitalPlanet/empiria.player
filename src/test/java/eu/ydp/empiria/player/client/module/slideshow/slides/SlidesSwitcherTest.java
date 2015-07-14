package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.SlideEndHandler;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.sound.SlideshowSoundController;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.empiria.player.client.module.slideshow.structure.SoundBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
    private SlideshowSoundController slideshowSoundController;
    @Mock
    private SoundBean soundBean;

    @Captor
    ArgumentCaptor<List<SoundBean>> captor;

    private final List<SlideBean> slides = Lists.newArrayList();

    @Before
    public void init() {
        slides.add(slide);
        testObj.init(slides, inlineBodyGeneratorSocket);
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
        verify(slideshowSoundController).stopAllSounds();
    }

    @Test
    public void shouldPauseSlide_whenThereIsAudio() {
        // given
        String audiopath = "test.mp3";
        when(slide.getSound()).thenReturn(soundBean);
        when(soundBean.getSrc()).thenReturn(audiopath);
        when(slide.hasSound()).thenReturn(true);

        // when
        testObj.pauseSlide();

        // then
        verify(slideshowSoundController).pauseSound(audiopath);
    }

    @Test
    public void shouldNotPauseSlide_whenThereIsNoAudio() {
        // given
        when(slide.hasSound()).thenReturn(false);

        // when
        testObj.pauseSlide();

        // then
        verifyZeroInteractions(slideshowSoundController);
    }

    @Test
    public void shouldNotPlaySound_whenThereIsNoAudio() {
        // given
        when(slide.hasSound()).thenReturn(false);

        // when
        testObj.playSlide();

        // then
        verifyZeroInteractions(slideshowSoundController);
    }

    @Test
    public void shouldPlaySlide() {
        // given
        String audiopath = "test.mp3";
        when(slide.getSound()).thenReturn(soundBean);
        when(soundBean.getSrc()).thenReturn(audiopath);
        when(slide.hasSound()).thenReturn(true);

        // when
        testObj.playSlide();

        // then
        verify(slideshowSoundController).playSound(eq(audiopath), any(SlideEndHandler.class));
    }

    @Test
    public void shouldStopAndPlaySlide() {
        // given
        String audiopath = "test.mp3";
        when(slide.getSound()).thenReturn(soundBean);
        when(soundBean.getSrc()).thenReturn(audiopath);
        when(slide.hasSound()).thenReturn(true);

        // when
        testObj.stopAndPlaySlide();

        // then
        verify(slideshowSoundController).playSound(eq(audiopath), any(SlideEndHandler.class));
        verify(slideshowSoundController).stopAllSounds();
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
