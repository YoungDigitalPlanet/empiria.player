package eu.ydp.empiria.player.client.media.texttrack;

import com.google.gwt.junit.GWTMockUtilities;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.factory.VideoTextTrackElementFactory;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import org.junit.*;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class VideoTextTrackElementTest extends AbstractTestBase {

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    protected VideoTextTrackElementFactory trackElementFactory;
    protected EventsBus eventsBus;
    protected MediaStyleNameConstants styleNameConstants;
    protected VideoTextTrackElementPresenter presenter;
    protected TextTrackFactory textTrackFactory;
    protected PageScopeFactory pageScopeFactory;
    protected MediaWrapper<?> mediaWrapper;
    protected TextTrackCue textTrackCue;
    protected MediaEvent event;

    @Before
    public void before() {
        setUp(new Class<?>[0], new Class<?>[0], new Class<?>[]{EventsBus.class});
        trackElementFactory = injector.getInstance(VideoTextTrackElementFactory.class);
        eventsBus = injector.getInstance(EventsBus.class);
        styleNameConstants = injector.getInstance(MediaStyleNameConstants.class);
        presenter = injector.getInstance(VideoTextTrackElementPresenter.class);
        textTrackFactory = injector.getInstance(TextTrackFactory.class);
        pageScopeFactory = injector.getInstance(PageScopeFactory.class);

    }

    @Test
    public void initTest() {
        VideoTextTrackElement instance = trackElementFactory.getVideoTextTrackElement(TextTrackKind.CAPTIONS);
        verify(presenter).addStyleName(styleNameConstants.QP_MEDIA_TEXT_TRACK() + "-" + TextTrackKind.CAPTIONS.name().toLowerCase());
        verify(presenter).setStyleName(styleNameConstants.QP_MEDIA_TEXT_TRACK());
        instance.init();
        verify(eventsBus).addAsyncHandlerToSource(eq(MediaEvent.getType(MediaEventTypes.TEXT_TRACK_UPDATE)), Matchers.any(MediaWrapper.class), eq(instance),
                Matchers.any(CurrentPageScope.class));
        verify(eventsBus).addAsyncHandlerToSource(eq(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE)), Matchers.any(MediaWrapper.class), eq(instance),
                Matchers.any(CurrentPageScope.class));
    }

    @Test
    public void fullScreenModeTest() {
        VideoTextTrackElement instance = trackElementFactory.getVideoTextTrackElement(TextTrackKind.CAPTIONS);
        instance.setFullScreen(true);
        instance.init();
        verify(eventsBus).addAsyncHandlerToSource(eq(MediaEvent.getType(MediaEventTypes.TEXT_TRACK_UPDATE)), Matchers.any(MediaWrapper.class), eq(instance),
                Matchers.any(CurrentPageScope.class));
        verify(eventsBus).addAsyncHandlerToSource(eq(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE)), Matchers.any(MediaWrapper.class), eq(instance),
                Matchers.any(CurrentPageScope.class));
        Assert.assertTrue(instance.isInFullScreen());
        verify(presenter)
                .addStyleName(styleNameConstants.QP_MEDIA_TEXT_TRACK() + instance.getSuffixToAdd() + "-" + TextTrackKind.CAPTIONS.name().toLowerCase());
        verify(presenter).setStyleName(styleNameConstants.QP_MEDIA_TEXT_TRACK() + instance.getSuffixToAdd());
    }

    @Test
    public void presenterTest() {
        VideoTextTrackElement instance = trackElementFactory.getVideoTextTrackElement(TextTrackKind.CAPTIONS);
        instance.init();
        Assert.assertTrue(instance.presenter.equals(presenter));
    }

    private void prepareTextEventMediaWrapper() {
        mediaWrapper = Mockito.mock(MediaWrapper.class);
        event = new MediaEvent(MediaEventTypes.TEXT_TRACK_UPDATE);
        textTrackCue = new TextTrackCue("id", 0, 10, "test");
        TextTrack textTrack = textTrackFactory.getTextTrack(TextTrackKind.CAPTIONS, mediaWrapper);
        textTrackCue.setTextTrack(textTrack);
        event.setTextTrackCue(textTrackCue);

    }

    @Test
    public void mediaEventTextTrackUpdateTestWithTextTrack() {
        // prepare
        prepareTextEventMediaWrapper();
        VideoTextTrackElement instance = Mockito.spy(trackElementFactory.getVideoTextTrackElement(TextTrackKind.CAPTIONS));
        instance.setMediaDescriptor(mediaWrapper);
        instance.init();
        // test
        eventsBus.fireEventFromSource(event, mediaWrapper, pageScopeFactory.getCurrentPageScope());
        verify(instance).onMediaEvent(eq(event));
        verify(instance).showHideText(eq(textTrackCue));
    }

    @Test
    public void mediaEventTextTrackUpdateTestWithoutTextTrack() {
        // prepare
        mediaWrapper = Mockito.mock(MediaWrapper.class);
        VideoTextTrackElement instance = Mockito.spy(trackElementFactory.getVideoTextTrackElement(TextTrackKind.CAPTIONS));
        instance.setMediaDescriptor(mediaWrapper);
        instance.init();
        MediaEvent event = new MediaEvent(MediaEventTypes.TEXT_TRACK_UPDATE);
        // test
        eventsBus.fireEventFromSource(event, mediaWrapper, pageScopeFactory.getCurrentPageScope());
        verify(instance).onMediaEvent(eq(event));
        verify(instance, Mockito.times(0)).showHideText(Matchers.any(TextTrackCue.class));
    }

    @Test
    public void mediaEventOntimeUpdateTestWithTextTrack() {
        // prepare
        prepareTextEventMediaWrapper();
        Mockito.when(mediaWrapper.getCurrentTime()).thenReturn(5d);
        VideoTextTrackElement instance = Mockito.spy(trackElementFactory.getVideoTextTrackElement(TextTrackKind.CAPTIONS));
        instance.setMediaDescriptor(mediaWrapper);
        instance.init();
        // test
        eventsBus.fireEventFromSource(event, mediaWrapper, pageScopeFactory.getCurrentPageScope());
        event = new MediaEvent(MediaEventTypes.ON_TIME_UPDATE);
        eventsBus.fireEventFromSource(event, mediaWrapper, pageScopeFactory.getCurrentPageScope());

        verify(instance).onMediaEvent(eq(event));
        verify(instance, Mockito.times(2)).showHideText(eq(textTrackCue));
        verify(presenter, Mockito.times(2)).setInnerText(eq("test"));

    }

    @Test
    public void mediaEventOntimeUpdateHideTextTestWithTextTrack() {
        // prepare
        prepareTextEventMediaWrapper();
        Mockito.when(mediaWrapper.getCurrentTime()).thenReturn(11d);
        VideoTextTrackElement instance = Mockito.spy(trackElementFactory.getVideoTextTrackElement(TextTrackKind.CAPTIONS));
        instance.setMediaDescriptor(mediaWrapper);
        instance.init();
        // test
        eventsBus.fireEventFromSource(event, mediaWrapper, pageScopeFactory.getCurrentPageScope());
        event = new MediaEvent(MediaEventTypes.ON_TIME_UPDATE);
        eventsBus.fireEventFromSource(event, mediaWrapper, pageScopeFactory.getCurrentPageScope());

        verify(instance, Mockito.times(2)).showHideText(eq(textTrackCue));
        verify(presenter, Mockito.times(2)).setInnerText(eq(""));
    }

    @Test
    public void mediaEventOntimeUpdateUpdateTestWithoutTextTrack() {
        // prepare
        mediaWrapper = Mockito.mock(MediaWrapper.class);
        VideoTextTrackElement instance = Mockito.spy(trackElementFactory.getVideoTextTrackElement(TextTrackKind.CAPTIONS));
        instance.setMediaDescriptor(mediaWrapper);
        instance.init();
        MediaEvent event = new MediaEvent(MediaEventTypes.TEXT_TRACK_UPDATE);
        // test
        eventsBus.fireEventFromSource(event, mediaWrapper, pageScopeFactory.getCurrentPageScope());
        event = new MediaEvent(MediaEventTypes.ON_TIME_UPDATE);
        eventsBus.fireEventFromSource(event, mediaWrapper, pageScopeFactory.getCurrentPageScope());
        verify(instance, Mockito.times(0)).showHideText(Matchers.any(TextTrackCue.class));
    }

}
