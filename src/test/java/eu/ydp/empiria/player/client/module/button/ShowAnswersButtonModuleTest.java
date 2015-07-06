package eu.ydp.empiria.player.client.module.button;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;
import org.junit.*;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class ShowAnswersButtonModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private static final String DISABLED_STYLE_NAME = "qp-showanswers-button-disabled";

	ShowAnswersButtonModule instance;
	EventsBus eventsBus;
	FlowRequestInvoker requestInvoker;
	private ClickHandler handler;
	private CustomPushButton button;
	private StyleNameConstants styleNameConstants;

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(CustomPushButton.class)
				  .toInstance(mock(CustomPushButton.class));
		}
	}

	@Before
	public void before() {
		setUp(new Class<?>[] { CustomPushButton.class }, new Class<?>[] { }, new Class<?>[] { EventsBus.class }, new CustomGuiceModule());
		instance = spy(injector.getInstance(ShowAnswersButtonModule.class));
		eventsBus = injector.getInstance(EventsBus.class);
		requestInvoker = mock(FlowRequestInvoker.class);
		button = injector.getInstance(CustomPushButton.class);
		styleNameConstants = injector.getInstance(StyleNameConstants.class);
		doAnswer(new Answer<ClickHandler>() {

			@Override
			public ClickHandler answer(InvocationOnMock invocation) throws Throwable {
				handler = (ClickHandler) invocation.getArguments()[0];
				return null;
			}
		}).when(button)
		  .addClickHandler(any(ClickHandler.class));
	}

	@After
	public void after() {
		Mockito.verifyNoMoreInteractions(requestInvoker);
	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Test
	public void testInitModuleElement() {
		instance.initModule(mock(Element.class));
		verify(eventsBus).addHandler(Matchers.eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGING)), Matchers.eq(instance));
	}

	@Test
	public void testOnDeliveryEvent() {
		DeliveryEvent event = mock(DeliveryEvent.class);
		Map<String, Object> params = new HashMap<String, Object>();
		when(event.getParams()).thenReturn(params);

		when(event.getType()).thenReturn(DeliveryEventType.SHOW_ANSWERS);
		instance.onDeliveryEvent(event);
		assertEquals(true, instance.isSelected);
		verify(instance).updateStyleName();

		when(event.getType()).thenReturn(DeliveryEventType.CONTINUE);
		instance.onDeliveryEvent(event);
		assertEquals(false, instance.isSelected);
		verify(instance, times(2)).updateStyleName();

		instance.isSelected = true;
		when(event.getType()).thenReturn(DeliveryEventType.CHECK);
		instance.onDeliveryEvent(event);
		assertEquals(false, instance.isSelected);
		verify(instance, times(3)).updateStyleName();

		instance.isSelected = true;
		when(event.getType()).thenReturn(DeliveryEventType.RESET);
		instance.onDeliveryEvent(event);
		assertEquals(false, instance.isSelected);
		verify(instance, times(4)).updateStyleName();
	}

	@Test
	public void testInvokeRequest() {
		instance.setFlowRequestsInvoker(requestInvoker);
		doReturn(null).when(instance)
					  .getCurrentGroupIdentifier();

		instance.isSelected = true;
		instance.invokeRequest();
		verify(requestInvoker).invokeRequest(Matchers.any(FlowRequest.Continue.class));

		Mockito.reset(requestInvoker);
		instance.isSelected = false;
		instance.invokeRequest();
		verify(requestInvoker).invokeRequest(Matchers.any(FlowRequest.ShowAnswers.class));
	}

	@Test
	public void testGetStyleName() {
		instance.isSelected = true;
		assertEquals("qp-hideanswers-button", instance.getStyleName());
		instance.isSelected = false;
		assertEquals("qp-showanswers-button", instance.getStyleName());
	}

	@Test
	public void testOnPlayerEvent() {
		PlayerEvent event = mock(PlayerEvent.class);
		when(event.getType()).thenReturn(PlayerEventTypes.PAGE_CHANGING);
		instance.setFlowRequestsInvoker(requestInvoker);
		doReturn(null).when(instance)
					  .getCurrentGroupIdentifier();

		instance.isSelected = false;
		instance.onPlayerEvent(event);
		verify(requestInvoker, times(0)).invokeRequest(Matchers.any(FlowRequest.Continue.class));

		instance.isSelected = true;
		instance.onPlayerEvent(event);
		verify(requestInvoker, times(1)).invokeRequest(Matchers.any(FlowRequest.Continue.class));
	}

	@Test
	public void shouldNotInvokeActionInPreviewMode() {
		// given
		instance.initModule(mock(Element.class));
		doReturn(null).when(instance)
					  .getCurrentGroupIdentifier();
		instance.setFlowRequestsInvoker(requestInvoker);
		instance.enablePreviewMode();

		// when
		handler.onClick(null);

		// then
		verifyZeroInteractions(requestInvoker);
	}

	@Test
	public void shouldNotOverwriteStyleInPreview() {
		// given
		final String inactiveStyleName = "STYLE_NAME";
		instance.initModule(mock(Element.class));
		doReturn(null).when(instance)
					  .getCurrentGroupIdentifier();
		when(styleNameConstants.QP_MODULE_MODE_PREVIEW()).thenReturn(inactiveStyleName);
		instance.enablePreviewMode();

		// when
		instance.updateStyleName();

		// then
		InOrder inOrder = inOrder(button);
		inOrder.verify(button)
			   .setStyleName(DISABLED_STYLE_NAME);
		inOrder.verify(button)
			   .addStyleName(inactiveStyleName);
		inOrder.verify(button)
			   .setStyleName(DISABLED_STYLE_NAME);
		inOrder.verify(button)
			   .addStyleName(inactiveStyleName);
	}
}
