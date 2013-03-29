package eu.ydp.empiria.player.client.module.button;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

@SuppressWarnings("PMD")
public class ResetButtonModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

	ResetButtonModule instance;
	FlowRequestInvoker requestInvoker;

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

}
