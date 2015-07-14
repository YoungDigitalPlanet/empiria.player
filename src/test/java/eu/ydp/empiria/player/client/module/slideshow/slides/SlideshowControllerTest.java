package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.module.slideshow.SlideEndHandler;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowButtonsPresenter;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowPagerPresenter;
import eu.ydp.empiria.player.client.module.slideshow.sound.SlideshowSoundController;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.empiria.player.client.module.slideshow.structure.SoundBean;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

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
    @Mock
    private SlideshowSoundController slideshowSoundController;
    @Mock
    private EventsBus eventsBus;
    @Captor
    private ArgumentCaptor<SlideEndHandler> slideEndCaptor;
    @Captor
    private ArgumentCaptor<PlayerEvent> playerEventCaptor;

    @Test
    public void shouldSetSlides_andReset() {
        // given
        List<SlideBean> slides = Lists.newArrayList();

        // when
        testObj.init(slides, inlineBodyGeneratorSocket);

        // then
        verify(slidesSwitcher).init(slides, inlineBodyGeneratorSocket);
        verify(slidesSwitcher).reset();
        verify(slidesSwitcher).setSlideEnd(any(SlideEndHandler.class));
        verifyEnableButtons();
    }

    @Test
    public void shouldInitSounds() {
        // given
        SlideBean slide = mock(SlideBean.class);
        SoundBean soundBean = mock(SoundBean.class);
        List<SlideBean> slides = Lists.newArrayList();
        slides.add(slide);

        String audiopath = "test.mp3";
        when(slide.getSound()).thenReturn(soundBean);
        when(slide.hasSound()).thenReturn(true);
        when(soundBean.getSrc()).thenReturn(audiopath);

        // when
        testObj.init(slides, inlineBodyGeneratorSocket);

        // then
        verify(slideshowSoundController).initSound(audiopath);
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
    public void shouldShowAndPlazSlide_andUpdateButtonsStyle() {
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
        verifyEnableButtons();
        verify(buttonsPresenter).setPlayButtonDown(false);
    }

    @Test
    public void shouldFireEventAfterSlideshowPlay() {
        // given
        PlayerEventTypes expectedEventType = PlayerEventTypes.SLIDESHOW_STARTED;
        SlideshowController expextedSource = testObj;

        // when
        testObj.playSlideshow();

        // then
        verify(eventsBus).fireEvent(playerEventCaptor.capture());
        PlayerEvent playerEvent = playerEventCaptor.getValue();
        assertThat(playerEvent.getType()).isEqualTo(expectedEventType);
        assertThat(playerEvent.getSource()).isEqualTo(expextedSource);
    }

    @Test
    public void shouldPauseSlideshow() {
        // given

        // when
        testObj.pauseSlideshow();

        // then
        verify(slidesSwitcher).pauseSlide();
        verify(buttonsPresenter).setPlayButtonDown(false);
    }

    @Test
    public void shouldPauseSlideshow_whenAnotherSlideshowStarted() {
        // given
        SlideshowController otherSlideshow = mock(SlideshowController.class);
        PlayerEvent slideshowStartedEvent = new PlayerEvent(PlayerEventTypes.SLIDESHOW_STARTED, otherSlideshow, otherSlideshow);

        // when
        testObj.onPlayerEvent(slideshowStartedEvent);

        // then
        verify(slidesSwitcher).pauseSlide();
    }

    @Test
    public void shouldPauseSlideshow_whenPageChanged() {
        // given
        MainFlowProcessor eventSource = mock(MainFlowProcessor.class);
        PlayerEvent pageChangedEvent = new PlayerEvent(PlayerEventTypes.PAGE_CHANGING, eventSource, eventSource);

        // when
        testObj.onPlayerEvent(pageChangedEvent);

        // then
        verify(slidesSwitcher).pauseSlide();
    }

    @Test
    public void shouldNotPauseSlideshow_whenThisSlideshowStarted() {
        // given
        PlayerEvent slideshowStartedEvent = new PlayerEvent(PlayerEventTypes.SLIDESHOW_STARTED, testObj, testObj);

        // when
        testObj.onPlayerEvent(slideshowStartedEvent);

        // then
        verify(slidesSwitcher, never()).pauseSlide();
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
    public void shouldResetSlide_whenCantSwitchToNextSlide() {
        // given
        List<SlideBean> slides = Lists.newArrayList();
        testObj.init(slides, inlineBodyGeneratorSocket);
        verify(slidesSwitcher).setSlideEnd(slideEndCaptor.capture());
        SlideEndHandler value = slideEndCaptor.getValue();

        when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(false);

        // when
        value.onEnd();

        // then
        verify(slidesSwitcher, times(2)).reset();
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
    public void shouldContinuePlayingSlideshow_whenCanSwitchToNextSlide() {
        // given
        List<SlideBean> slides = Lists.newArrayList();
        testObj.init(slides, inlineBodyGeneratorSocket);
        verify(slidesSwitcher).setSlideEnd(slideEndCaptor.capture());
        SlideEndHandler value = slideEndCaptor.getValue();

        when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(true);

        // when
        value.onEnd();

        // then
        verify(slidesSwitcher).showNextSlide();
        verify(slidesSwitcher).stopAndPlaySlide();
    }

    @Test
    public void shouldStopSlideshow_whenCanNotSwitchToNextSlide() {
        // given
        List<SlideBean> slides = Lists.newArrayList();
        testObj.init(slides, inlineBodyGeneratorSocket);
        verify(slidesSwitcher).setSlideEnd(slideEndCaptor.capture());
        SlideEndHandler value = slideEndCaptor.getValue();

        when(slidesSwitcher.canSwitchToNextSlide()).thenReturn(false);

        // when
        value.onEnd();

        // then
        verify(slidesSwitcher, times(2)).reset();
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
