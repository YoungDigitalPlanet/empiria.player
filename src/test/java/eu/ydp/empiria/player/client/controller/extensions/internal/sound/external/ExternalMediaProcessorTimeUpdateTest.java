package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaStatus;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.gwtutil.client.date.DateService;
import eu.ydp.gwtutil.client.timer.TimerAccessibleMock;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class ExternalMediaProcessorTimeUpdateTest extends ExternalMediaProcessorTestBase {

    private static long timeMillisCounter;

    @Override
    public void setUp() {
        super.setUpWithAccessibleTimer();
        DateService ds = injector.getInstance(DateService.class);
        doAnswer(new Answer<Long>() {

            @Override
            public Long answer(InvocationOnMock invocation) throws Throwable {
                return timeMillisCounter++;
            }
        }).when(ds).getTimeMillis();
    }

    @SuppressWarnings("unused")
    private Object[] media_params() {
        return $((Object[]) TestMediaAudio.values());
    }

    @Test
    @Parameters(method = "media_params")
    public void timeUpdate_onPlay(TestMediaAudio testMedias) throws InterruptedException {
        // given
        int REPEAT_TIMES = 3;

        MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);
        MediaEventHandler handler = mock(MediaEventHandler.class);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

        // when
        listener.onPlay(wrapper.getMediaUniqId());

        for (int i = 0; i < REPEAT_TIMES; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        verifyTimeUpdateAscending(REPEAT_TIMES, handler);
        assertThat(TimerAccessibleMock.hasBeenCancelled(), equalTo(false));
    }

    @Test
    @Parameters(method = "media_params")
    public void timeUpdate_onPause(TestMediaAudio testMedias) {
        // given
        int REPEAT_TIMES_EXPECTED = 2;
        MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);
        MediaEventHandler handler = mock(MediaEventHandler.class);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

        // when
        listener.onPlay(wrapper.getMediaUniqId());

        for (int i = 0; i < REPEAT_TIMES_EXPECTED; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onPause(wrapper.getMediaUniqId());

        // then
        verifyTimeUpdateAscending(REPEAT_TIMES_EXPECTED, handler);
        assertThat(TimerAccessibleMock.hasBeenCancelled(), equalTo(true));
    }

    @Test
    @Parameters(method = "media_params")
    public void timeUpdate_onEnd(TestMediaAudio testMedias) {
        // given
        int REPEAT_TIMES_EXPECTED = 2;
        MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);
        String mediaUniqId = wrapper.getMediaUniqId();
        MediaEventHandler handler = mock(MediaEventHandler.class);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

        // when
        listener.onPlay(mediaUniqId);

        for (int i = 0; i < REPEAT_TIMES_EXPECTED; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onEnd(mediaUniqId);

        // then
        verifyTimeUpdateAscending(REPEAT_TIMES_EXPECTED, handler);
        assertThat(TimerAccessibleMock.hasBeenCancelled(), equalTo(true));
    }

    @Test
    @Parameters(method = "media_params")
    public void timeUpdate_onPlay_onSeek(TestMediaAudio testMedias) {
        // given
        final int REPEAT_TIMES_BEFORE_SEEK = 2;
        final int REPEAT_TIMES_AFTER_SEEK = 2;
        final int REPEAT_TIMES_EXPECTED = REPEAT_TIMES_BEFORE_SEEK + REPEAT_TIMES_AFTER_SEEK + 1; // + 1 for external time update
        final double SEEK_TIME_SECONDS = 50;
        final int SEEK_TIME_MILLIS = (int) (SEEK_TIME_SECONDS * ConnectorTimeResolutionToSecondsConverter.MILLIS_DIVISOR);
        MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);
        MediaEventHandler handler = mock(MediaEventHandler.class);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

        MediaStatus seekMediaStatus = new MediaStatus() {

            @Override
            public int getCurrentTimeMillis() {
                return SEEK_TIME_MILLIS;
            }
        };

        // when
        listener.onPlay(wrapper.getMediaUniqId());

        for (int i = 0; i < REPEAT_TIMES_BEFORE_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onTimeUpdate(wrapper.getMediaUniqId(), seekMediaStatus);

        for (int i = 0; i < REPEAT_TIMES_AFTER_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        // then
        int indexAfterLeap = REPEAT_TIMES_BEFORE_SEEK;
        verifyTimeUpdateAscending(REPEAT_TIMES_EXPECTED, handler);
        verifyTimeUpdateWithTimeLeap(REPEAT_TIMES_EXPECTED, handler, indexAfterLeap, SEEK_TIME_SECONDS);
        assertThat(TimerAccessibleMock.hasBeenCancelled(), equalTo(false));
    }

    @Test
    @Parameters(method = "media_params")
    public void timeUpdate_onPlay_onSeek_onSeekBack(TestMediaAudio testMedias) {
        // given
        final int REPEAT_TIMES_BEFORE_SEEK = 2;
        final int REPEAT_TIMES_MID_SEEK = 2;
        final int REPEAT_TIMES_AFTER_SEEK = 2;
        final int REPEAT_TIMES_EXPECTED = REPEAT_TIMES_BEFORE_SEEK + REPEAT_TIMES_MID_SEEK + REPEAT_TIMES_AFTER_SEEK + 2; // + 2 for two external time updates
        final double SEEK_TIME_SECONDS_0 = 50;
        final int SEEK_TIME_MILLIS_0 = (int) (SEEK_TIME_SECONDS_0 * ConnectorTimeResolutionToSecondsConverter.MILLIS_DIVISOR);
        final double SEEK_TIME_SECONDS_1 = 20;
        final int SEEK_TIME_MILLIS_1 = (int) (SEEK_TIME_SECONDS_1 * ConnectorTimeResolutionToSecondsConverter.MILLIS_DIVISOR);
        MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);
        String mediaUniqId = wrapper.getMediaUniqId();
        MediaEventHandler handler = mock(MediaEventHandler.class);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

        MediaStatus firstSeekMediaStatus = new MediaStatus() {

            @Override
            public int getCurrentTimeMillis() {
                return SEEK_TIME_MILLIS_0;
            }
        };

        MediaStatus secondSeekMediaStatus = new MediaStatus() {

            @Override
            public int getCurrentTimeMillis() {
                return SEEK_TIME_MILLIS_1;
            }
        };

        // when
        listener.onPlay(mediaUniqId);

        for (int i = 0; i < REPEAT_TIMES_BEFORE_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onTimeUpdate(mediaUniqId, firstSeekMediaStatus);

        for (int i = 0; i < REPEAT_TIMES_MID_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onTimeUpdate(mediaUniqId, secondSeekMediaStatus);

        for (int i = 0; i < REPEAT_TIMES_AFTER_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        // then
        int indexAfterFirstSeek = REPEAT_TIMES_BEFORE_SEEK;
        verifyTimeUpdateWithTimeLeap(REPEAT_TIMES_EXPECTED, handler, indexAfterFirstSeek, SEEK_TIME_SECONDS_0);
        int indexAfterSecondSeek = REPEAT_TIMES_BEFORE_SEEK + REPEAT_TIMES_MID_SEEK;
        verifyTimeUpdateWithTimeLeap(REPEAT_TIMES_EXPECTED, handler, indexAfterSecondSeek, SEEK_TIME_SECONDS_1);
        assertThat(TimerAccessibleMock.hasBeenCancelled(), equalTo(false));
    }

    @Test
    @Parameters(method = "media_params")
    public void timeUpdate_onPlay_onSeek_onPause(TestMediaAudio testMedias) {
        // given
        final int REPEAT_TIMES_BEFORE_SEEK = 2;
        final int REPEAT_TIMES_AFTER_SEEK = 2;
        final int REPEAT_TIMES_EXPECTED = REPEAT_TIMES_BEFORE_SEEK + REPEAT_TIMES_AFTER_SEEK + 1; // + 1 for external time update
        final double SEEK_TIME_SECONDS = 50;
        final int SEEK_TIME_MILLIS = (int) (SEEK_TIME_SECONDS * ConnectorTimeResolutionToSecondsConverter.MILLIS_DIVISOR);
        MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);
        String mediaUniqId = wrapper.getMediaUniqId();
        MediaEventHandler handler = mock(MediaEventHandler.class);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

        MediaStatus seekMediaStatus = new MediaStatus() {

            @Override
            public int getCurrentTimeMillis() {
                return SEEK_TIME_MILLIS;
            }
        };

        // when
        listener.onPlay(mediaUniqId);

        for (int i = 0; i < REPEAT_TIMES_BEFORE_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onTimeUpdate(mediaUniqId, seekMediaStatus);

        for (int i = 0; i < REPEAT_TIMES_AFTER_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onPause(mediaUniqId);

        // then
        int indexAfterFirstSeek = REPEAT_TIMES_BEFORE_SEEK;
        verifyTimeUpdateAscending(REPEAT_TIMES_EXPECTED, handler);
        verifyTimeUpdateWithTimeLeap(REPEAT_TIMES_EXPECTED, handler, indexAfterFirstSeek, SEEK_TIME_SECONDS);
        assertThat(TimerAccessibleMock.hasBeenCancelled(), equalTo(true));
    }

    @Test
    @Parameters(method = "media_params")
    public void timeUpdate_onPlay_onSeek_onEnd(TestMediaAudio testMedias) {
        // given
        final int REPEAT_TIMES_BEFORE_SEEK = 2;
        final int REPEAT_TIMES_AFTER_SEEK = 2;
        final int REPEAT_TIMES_EXPECTED = REPEAT_TIMES_BEFORE_SEEK + REPEAT_TIMES_AFTER_SEEK + 1; // + 1 for external time update
        final double SEEK_TIME_SECONDS = 50;
        final int SEEK_TIME_MILLIS = (int) (SEEK_TIME_SECONDS * ConnectorTimeResolutionToSecondsConverter.MILLIS_DIVISOR);
        MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);
        String mediaUniqId = wrapper.getMediaUniqId();
        MediaEventHandler handler = mock(MediaEventHandler.class);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

        MediaStatus seekMediaStatus = new MediaStatus() {

            @Override
            public int getCurrentTimeMillis() {
                return SEEK_TIME_MILLIS;
            }
        };

        // when
        listener.onPlay(mediaUniqId);

        for (int i = 0; i < REPEAT_TIMES_BEFORE_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onTimeUpdate(mediaUniqId, seekMediaStatus);

        for (int i = 0; i < REPEAT_TIMES_AFTER_SEEK; i++) {
            TimerAccessibleMock.getInstance().dispatch();
        }

        listener.onEnd(mediaUniqId);

        // then
        int indexAfterFirstSeek = REPEAT_TIMES_BEFORE_SEEK;
        verifyTimeUpdateAscending(REPEAT_TIMES_EXPECTED, handler);
        verifyTimeUpdateWithTimeLeap(REPEAT_TIMES_EXPECTED, handler, indexAfterFirstSeek, SEEK_TIME_SECONDS);
        assertThat(TimerAccessibleMock.hasBeenCancelled(), equalTo(true));
    }

    private void verifyTimeUpdateWithTimeLeap(int repeatTimes, MediaEventHandler handler, int indexAfterLeap, double timeAfterLeapSeconds) {
        ArgumentCaptor<MediaEvent> ac = getArgumentCaptor(repeatTimes, handler);
        verifyTimeUpdateCount(repeatTimes, ac);
        assertThat(ac.getAllValues().get(indexAfterLeap).getCurrentTime(), greaterThanOrEqualTo(timeAfterLeapSeconds));
    }

    private void verifyTimeUpdateAscending(int repeatTimes, MediaEventHandler handler) {
        ArgumentCaptor<MediaEvent> ac = getArgumentCaptor(repeatTimes, handler);
        verifyTimeUpdateCount(repeatTimes, ac);
        verifyTimeUpdateAscendingTimeValues(repeatTimes, ac);
    }

    private ArgumentCaptor<MediaEvent> getArgumentCaptor(int repeatTimes, MediaEventHandler handler) {
        ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
        verify(handler, times(repeatTimes)).onMediaEvent(ac.capture());
        return ac;
    }

    private void verifyTimeUpdateCount(int repeatTimes, ArgumentCaptor<MediaEvent> ac) {
        assertThat(ac.getAllValues().size(), equalTo(repeatTimes));
        for (int i = 0; i < repeatTimes; i++) {
            assertThat(ac.getAllValues().get(i).getType(), equalTo(MediaEventTypes.ON_TIME_UPDATE));
        }
    }

    private void verifyTimeUpdateAscendingTimeValues(int repeatTimes, ArgumentCaptor<MediaEvent> ac) {
        for (int i = 0; i < repeatTimes - 1; i++) {
            double time0 = ac.getAllValues().get(i).getCurrentTime();
            double time1 = ac.getAllValues().get(i + 1).getCurrentTime();
            assertThat(time1, greaterThanOrEqualTo(time0));
        }
    }

}
