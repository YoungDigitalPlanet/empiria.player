package eu.ydp.empiria.player.client.module.button;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.CurrentPageProperties;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.feedback.FeedbackEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class FeedbackAudioMuteButtonModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private EventsBus eventsBus;
	private CurrentPageProperties currentPageProperties;

	private FeedbackAudioMuteButtonModule testObj;
	protected ClickHandler handler;
	private FlowRequestInvoker requestInvoker;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void before() {
		setUp(new Class<?>[] { CustomPushButton.class }, new Class<?>[] {}, new Class<?>[] { EventsBus.class }, new CustomGuiceModule());

		testObj = spy(injector.getInstance(FeedbackAudioMuteButtonModule.class));
		eventsBus = injector.getInstance(EventsBus.class);
		currentPageProperties = injector.getInstance(CurrentPageProperties.class);
		requestInvoker = mock(FlowRequestInvoker.class);
		CustomPushButton button = injector.getInstance(CustomPushButton.class);
		doAnswer(new Answer<ClickHandler>() {

			@Override
			public ClickHandler answer(InvocationOnMock invocation) throws Throwable {
				handler = (ClickHandler) invocation.getArguments()[0];
				return null;
			}
		}).when(button).addClickHandler(any(ClickHandler.class));
	}

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			CustomPushButton customPushButton = mock(CustomPushButton.class);
			binder.bind(CustomPushButton.class).toInstance(customPushButton);
			CurrentPageProperties currentPageProperties = mock(CurrentPageProperties.class);
			binder.bind(CurrentPageProperties.class).toInstance(currentPageProperties);
		}
	}

	@Test
	public void shouldFireEventOnInvoke() {
		// when
		testObj.invokeRequest();

		// then
		verify(eventsBus).fireEvent(any(FeedbackEvent.class));
	}

	@Test
	public void testChangeStyleOnTestPageLoadedExpectsVisibleWhenPageContainsInteractiveModules() {
		when(currentPageProperties.hasInteractiveModules()).thenReturn(true);
		testObj.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

		String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF;
		assertEquals(testObj.getStyleName(), expected);
	}

	@Test
	public void testChangeStyleOnTestPageLoadedExpectsHiddenWhenPageContainsInteractiveModules() {
		when(currentPageProperties.hasInteractiveModules()).thenReturn(false);

		testObj.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

		String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF + " " + FeedbackAudioMuteButtonModule.STYLE_NAME__DISABLED;
		assertEquals(testObj.getStyleName(), expected);
	}

	@Test
	public void shouldNotInvokeActionInPreviewMode() {
		// given
		testObj.initModule(mock(Element.class));
		doReturn(null).when(testObj).getCurrentGroupIdentifier();
		testObj.setFlowRequestsInvoker(requestInvoker);
		testObj.enablePreviewMode();

		// when
		handler.onClick(null);

		// then
		verifyZeroInteractions(requestInvoker);
	}
}
