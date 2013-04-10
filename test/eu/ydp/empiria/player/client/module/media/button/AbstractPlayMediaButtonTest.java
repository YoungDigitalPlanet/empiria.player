package eu.ydp.empiria.player.client.module.media.button;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class AbstractPlayMediaButtonTest extends AbstractTestBase {

	private MockButton button;
	private EventsBus eventsBus;
	
	private static String testest;
	
	@SuppressWarnings("rawtypes")
	private MediaWrapper mediaWrapper;

	@Before
	public void setUpTest() {		
		mockButton();
	}

	@Test
	public void onClickTest() {
		// given
		eventsBus = mock(EventsBus.class);
		button.eventsBus = eventsBus;
		MediaEvent madiaEvent = mock(MediaEvent.class);
		when(button.createMediaEvent()).thenReturn(madiaEvent);
		
		// when
		button.onClick();
		
		// then
		Mockito.verify(eventsBus).fireEventFromSource(madiaEvent, mediaWrapper);
	}
	
	@Test
	public void isButtonActivatedWhenOnPlayEventHandled() {
		// given
		button.setActive(false);
		button.initButtonStyleChangeHandlers();
		MediaEvent mediaEvent = new MediaEvent(MediaEventTypes.ON_PLAY, mediaWrapper);
		
		// when
		eventsBus.fireEventFromSource(mediaEvent, mediaWrapper);
		
		// then
		assertThat(button.isActive(), equalTo(true));		
	}

	@Test
	public void isButtonDeactivatedWhenOnStopEventHandled() {
		// given
		button.setActive(true);
		button.initButtonStyleChangeHandlers();
		MediaEvent mediaEvent = new MediaEvent(MediaEventTypes.ON_STOP, mediaWrapper);
		
		// when
		eventsBus.fireEventFromSource(mediaEvent, mediaWrapper);
		
		// then
		assertThat(button.isActive(), equalTo(false));		
	}	

	@SuppressWarnings("unchecked")
	private void mockButton() {
		button = mock(MockButton.class, Mockito.CALLS_REAL_METHODS);
		eventsBus = injector.getInstance(EventsBus.class);
		button.eventsBus = eventsBus;
		
		mediaWrapper = mock(MediaWrapper.class);
		when(button.getMediaWrapper()).thenReturn(mediaWrapper);
		Mockito.doNothing().when(button).changeStyleForClick();
	}
	
	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void restore() {
		GWTMockUtilities.restore();
	}		

	private static class MockButton extends AbstractPlayMediaButton<MockButton> {

		public MockButton(String baseStyleName) {			
			super(baseStyleName);			
		}

		@Override
		protected MediaEvent createMediaEvent() {
			return mock(MediaEvent.class);
		}

		@Override
		protected boolean initButtonStyleChangeHandlersCondition() {
			return true;
		}
		
		@Override
		protected CurrentPageScope createCurrentPageScope() {		
			return null;
		}
		
	}
}
