package eu.ydp.empiria.player.client.module.button;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.button.ResetButtonModule;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

@SuppressWarnings("PMD")
public class ResetButtonModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

	ResetButtonModule instance;
	FlowRequestInvoker requestInvoker;
	protected ClickHandler handler;

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(CustomPushButton.class).toInstance(mock(CustomPushButton.class));
		}
	}

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
		instance = spy(injector.getInstance(ResetButtonModule.class));
		requestInvoker = mock(FlowRequestInvoker.class);
		instance.setFlowRequestsInvoker(requestInvoker);
		CustomPushButton button = injector.getInstance(CustomPushButton.class);
		doAnswer(new Answer<ClickHandler>() {


			@Override
			public ClickHandler answer(InvocationOnMock invocation) throws Throwable {
				handler = (ClickHandler) invocation.getArguments()[0];
				return null;
			}
		}).when(button).addClickHandler(any(ClickHandler.class));
	}

	@Test
	public void testOnDeliveryEvent() {
		Mockito.verifyZeroInteractions(requestInvoker);
	}

	@Test
	public void testInvokeRequest() {
		doReturn(null).when(instance).getCurrentGroupIdentifier();

		instance.invokeRequest();
		verify(requestInvoker).invokeRequest(Mockito.any(FlowRequest.Reset.class));
	}

	@Test
	public void testGetStyleName() {
		assertEquals("qp-reset-button", instance.getStyleName());
	}

	@Test
	public void shouldNotInvokeActionInPreviewMode() {
		// given
		instance.initModule(mock(Element.class));
		doReturn(null).when(instance).getCurrentGroupIdentifier();
		instance.setFlowRequestsInvoker(requestInvoker);
		instance.enablePreviewMode();

		// when
		handler.onClick(null);

		// then
		verifyZeroInteractions(requestInvoker);
	}
}
