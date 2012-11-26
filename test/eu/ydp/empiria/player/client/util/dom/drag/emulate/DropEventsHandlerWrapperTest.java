package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParserMock;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.web.bindery.event.shared.HandlerRegistration;

@SuppressWarnings("PMD")
public class DropEventsHandlerWrapperTest {
	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	DropEventsHandlerWrapper instance;
	DroppableWidget<?> droppableWidget;

	@Before
	public void before() {
		AbstractHTML5DragDropWrapper.parser = new OverlayTypesParserMock();
		droppableWidget = mock(DroppableWidget.class);
		instance = new DropEventsHandlerWrapper(droppableWidget);
	}

	@Test
	public void addDragEnterHandlerTest() {
		DragEnterHandler handler = mock(DragEnterHandler.class);
		instance.wrap(handler);
		verify(droppableWidget).addOverDroppableHandler(Mockito.any(OverDroppableEventHandler.class));
	}

	OverDroppableEventHandler droppableEventHandler;

	@Test
	public void fireDragEnterHandlerTest() {
		when(droppableWidget.addOverDroppableHandler(Mockito.any(OverDroppableEventHandler.class))).then(new Answer<HandlerRegistration>() {
			@Override
			public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
				droppableEventHandler = (OverDroppableEventHandler) invocation.getArguments()[0];
				return null;
			}
		});
		DragEnterHandler handler = mock(DragEnterHandler.class);
		instance.wrap(handler);
		droppableEventHandler.onOverDroppable(null);
		verify(handler).onDragEnter(Mockito.any(DragEnterEvent.class));
	}

	@Test
	public void addDragLeaveHandlerTest() {
		DragLeaveHandler handler = mock(DragLeaveHandler.class);
		instance.wrap(handler);
		verify(droppableWidget).addOutDroppableHandler(Mockito.any(OutDroppableEventHandler.class));
	}

	OutDroppableEventHandler outDroppableEventHandler;

	@Test
	public void fireDragLeaveHandlerTest() {
		when(droppableWidget.addOutDroppableHandler(Mockito.any(OutDroppableEventHandler.class))).then(new Answer<HandlerRegistration>() {
			@Override
			public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
				outDroppableEventHandler = (OutDroppableEventHandler) invocation.getArguments()[0];
				return null;
			}
		});
		DragLeaveHandler handler = mock(DragLeaveHandler.class);
		instance.wrap(handler);
		outDroppableEventHandler.onOutDroppable(null);
		verify(handler).onDragLeave(Mockito.any(DragLeaveEvent.class));
	}

	@Test
	public void addDragOverHandlerTest() {
		DragOverHandler handler = mock(DragOverHandler.class);
		instance.wrap(handler);
		verify(droppableWidget).addOverDroppableHandler(Mockito.any(OverDroppableEventHandler.class));
	}

	@Test
	public void fireDragOverHandlerTest() {
		when(droppableWidget.addOverDroppableHandler(Mockito.any(OverDroppableEventHandler.class))).then(new Answer<HandlerRegistration>() {
			@Override
			public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
				droppableEventHandler = (OverDroppableEventHandler) invocation.getArguments()[0];
				return null;
			}
		});
		DragOverHandler handler = mock(DragOverHandler.class);
		instance.wrap(handler);
		droppableEventHandler.onOverDroppable(null);
		verify(handler).onDragOver(Mockito.any(DragOverEvent.class));
	}

	@Test
	public void addDropHandlerTest() {
		DropHandler handler = mock(DropHandler.class);
		instance.wrap(handler);
		verify(droppableWidget).addDropHandler(Mockito.any(DropEventHandler.class));
	}

	DropEventHandler dropEventHandler;

	@Test
	public void fireDropHandlerTest() {
		when(droppableWidget.addDropHandler(Mockito.any(DropEventHandler.class))).then(new Answer<HandlerRegistration>() {
			@Override
			public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
				dropEventHandler = (DropEventHandler) invocation.getArguments()[0];
				return null;
			}
		});
		DropHandler handler = mock(DropHandler.class);
		instance.wrap(handler);
		dropEventHandler.onDrop(null);
		verify(handler).onDrop(Mockito.any(DropEvent.class));
	}
}
