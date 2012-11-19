package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import eu.ydp.empiria.player.client.util.dom.drag.emulate.DragStartEndHandlerWrapper.DragEndEventWrapper;
import eu.ydp.empiria.player.client.util.dom.drag.emulate.DragStartEndHandlerWrapper.DragStartEventWrapper;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.web.bindery.event.shared.HandlerRegistration;

@SuppressWarnings("PMD")
public class DragStartEndHandlerWrapperTest {

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	DraggableWidget<?> draggableWidget;
	private DragStartEndHandlerWrapper instance;

	@Before
	public void before() {
		draggableWidget = mock(DraggableWidget.class);
		instance = spy(new DragStartEndHandlerWrapper(draggableWidget));
	}

	@Test
	public void wrapDragStartHandlerTest() {
		DragStartHandler startHandler = mock(DragStartHandler.class);
		instance.wrap(startHandler);
		verify(draggableWidget).addDragStartHandler(Mockito.any(DragStartEventHandler.class));
	}

	@Test
	public void wrapDragEndHandlerTest() {
		DragEndHandler endHandler = mock(DragEndHandler.class);
		instance.wrap(endHandler);
		verify(draggableWidget).addDragStopHandler(Mockito.any(DragStopEventHandler.class));
	}

	DragStartEventHandler startHandler;

	@Test
	public void dragStartHandlerTest() {
		DragStartHandler dragStartHandler = mock(DragStartHandler.class);
		ArgumentCaptor<DragStartEventWrapper> captor = ArgumentCaptor.forClass(DragStartEventWrapper.class);
		when(draggableWidget.addDragStartHandler(Mockito.any(DragStartEventHandler.class))).then(new Answer<HandlerRegistration>() {
			@Override
			public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
				startHandler = (DragStartEventHandler) invocation.getArguments()[0];
				return null;
			}
		});
		instance.wrap(dragStartHandler);
		startHandler.onDragStart(Mockito.mock(DragStartEvent.class));
		verify(dragStartHandler).onDragStart(captor.capture());
		DragStartEventWrapper event = captor.getValue();
		event.setData("text", "text");
		event.getData("text");
		verify(instance).setData(Mockito.eq("text"), Mockito.eq("text"));
		verify(instance).getData(Mockito.eq("text"));
	}

	DragStopEventHandler stopHandler;

	@Test
	public void dragEndHandlerTest() {
		DragEndHandler endHandler = mock(DragEndHandler.class);
		ArgumentCaptor<DragEndEventWrapper> captor = ArgumentCaptor.forClass(DragEndEventWrapper.class);
		when(draggableWidget.addDragStopHandler(Mockito.any(DragStopEventHandler.class))).then(new Answer<HandlerRegistration>() {
			@Override
			public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
				stopHandler = (DragStopEventHandler) invocation.getArguments()[0];
				return null;
			}
		});
		instance.wrap(endHandler);
		stopHandler.onDragStop(mock(DragStopEvent.class));
		verify(endHandler).onDragEnd(captor.capture());
		DragEndEventWrapper event = captor.getValue();
		event.setData("text", "text");
		event.getData("text");
		verify(instance).setData(Mockito.eq("text"), Mockito.eq("text"));
		verify(instance).getData(Mockito.eq("text"));

	}

}
