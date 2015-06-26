package eu.ydp.empiria.player.client.controller.extensions.internal.media.external;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.external.FullscreenVideoMediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;

@RunWith(JUnitParamsRunner.class)
public class FullscreenVideoExecutorTest extends AbstractTestWithMocksBase {

	private FullscreenVideoExecutor executor;
	private ExternalFullscreenVideoConnector connector;
	private EventsBus eventsBus;

	@Override
	public void setUp() {
		super.setUp(FullscreenVideoExecutor.class);
		executor = injector.getInstance(FullscreenVideoExecutor.class);
		connector = injector.getInstance(ExternalFullscreenVideoConnector.class);
		eventsBus = injector.getInstance(EventsBus.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void play_timeChanged() {
		// given
		final String ID = "ID";
		final Double TIME = 70d;
		FullscreenVideoMediaWrapper mw = createMediaWrapperMock(ID, TIME);
		executor.setMediaWrapper(mw);
		BaseMediaConfiguration bmc = createBaseMediaConfiguration();
		executor.setBaseMediaConfiguration(bmc);

		// when
		executor.play();

		// then
		verify(connector).openFullscreen(eq(ID), anyCollection(), eq(TIME));
	}

	@Test
	@Parameters({ "0", "25", "50", "75", "100" })
	public void setCurrentTime(double timeNew) {
		// given
		final String ID = "ID";
		final Double TIME_OLD = 50d;
		FullscreenVideoMediaWrapper mw = createMediaWrapperMock(ID, TIME_OLD);
		executor.setMediaWrapper(mw);
		BaseMediaConfiguration bmc = createBaseMediaConfiguration();
		executor.setBaseMediaConfiguration(bmc);

		// when
		executor.setCurrentTime(timeNew);

		// then
		verify(mw).setCurrentTime(eq(timeNew));
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(eventsBus).fireEventFromSource(ac.capture(), eq(mw));
		assertThat(ac.getValue().getCurrentTime(), equalTo(timeNew));

	}

	@Test
	@Parameters({ "0", "25", "50", "75", "100" })
	public void onFullscreenClosed_validId(double timeNew) {
		// given
		final String ID = "ID";
		final Double TIME_OLD = 50d;
		FullscreenVideoMediaWrapper mw = createMediaWrapperMock(ID, TIME_OLD);
		executor.setMediaWrapper(mw);
		BaseMediaConfiguration bmc = createBaseMediaConfiguration();
		executor.setBaseMediaConfiguration(bmc);

		// when
		executor.onFullscreenClosed(ID, timeNew);

		// then
		verify(mw).setCurrentTime(eq(timeNew));
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(eventsBus).fireEventFromSource(ac.capture(), eq(mw));
		assertThat(ac.getValue().getCurrentTime(), equalTo(timeNew));
	}

	@Test
	public void onFullscreenClosed_invalidId() {
		// given
		final String ID = "ID";
		final String OTHER_ID = "OTHER_ID";
		final Double TIME_OLD = 50d;
		final double TIME_NEW = 60d;
		FullscreenVideoMediaWrapper mw = createMediaWrapperMock(ID, TIME_OLD);
		executor.setMediaWrapper(mw);
		BaseMediaConfiguration bmc = createBaseMediaConfiguration();
		executor.setBaseMediaConfiguration(bmc);

		// when
		executor.onFullscreenClosed(OTHER_ID, TIME_NEW);

		// then
		verify(mw, never()).setCurrentTime(anyDouble());
		verify(eventsBus, never()).fireEventFromSource(any(MediaEvent.class), anyObject());
	}

	private FullscreenVideoMediaWrapper createMediaWrapperMock(final String id, Double time) {
		FullscreenVideoMediaWrapper mw = mock(FullscreenVideoMediaWrapper.class);
		stub(mw.getMediaUniqId()).toReturn(id);
		stub(mw.getCurrentTime()).toReturn(time);
		return mw;
	}

	private BaseMediaConfiguration createBaseMediaConfiguration() {
		return new BaseMediaConfiguration(Maps.<String, String> newHashMap(), MediaType.VIDEO, "poster.jpg", 480, 640, true, false, "");
	}

}
