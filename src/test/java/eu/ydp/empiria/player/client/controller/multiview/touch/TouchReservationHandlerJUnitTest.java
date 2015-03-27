package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TouchReservationHandlerJUnitTest {

	private EventsBus eventsBus;
	private IsWidget isWidget;
	private final ArgumentCaptor<Type> typeCaptor = ArgumentCaptor.forClass(Type.class);
	private final HandlerRegistration handlerRegistration = mock(HandlerRegistration.class);

	@Before
	public void before() {
		isWidget = mock(IsWidget.class);
		Widget widget = mock(Widget.class);
		doReturn(handlerRegistration).when(widget)
									 .addDomHandler(Matchers.any(TouchStartHandler.class), Matchers.any(Type.class));
		doReturn(widget).when(isWidget)
						.asWidget();

		eventsBus = mock(EventsBus.class);

	}

	@Test
	public void TouchReservationHandler() throws Exception {
		TouchReservationHandler instance = new TouchReservationHandler(isWidget, eventsBus);
		verify(isWidget.asWidget()).addDomHandler(Matchers.any(TouchStartHandler.class), typeCaptor.capture());
		assertTrue(typeCaptor.getValue()
							 .equals(TouchStartEvent.getType()));

	}

	@Test
	public void removeHandler() throws Exception {
		TouchReservationHandler instance = new TouchReservationHandler(isWidget, eventsBus);
		instance.removeHandler();
		verify(handlerRegistration).removeHandler();
	}

}
