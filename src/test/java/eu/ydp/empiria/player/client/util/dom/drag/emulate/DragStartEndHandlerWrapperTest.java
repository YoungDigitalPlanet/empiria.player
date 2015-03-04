package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import com.google.gwt.dom.client.DataTransfer;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParserMock;
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
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class DragStartEndHandlerWrapperTest {

	DraggableWidget<?> draggableWidget;
	private DragStartEndHandlerWrapper instance;

	@Before
	public void before() {
		AbstractHTML5DragDropWrapper.parser = new OverlayTypesParserMock();
		draggableWidget = mock(DraggableWidget.class);
		instance = spy(new DragStartEndHandlerWrapper(draggableWidget));
		when(instance.getDataTransfer()).then(new Answer<DataTransfer>() {
			@Override
			public DataTransfer answer(InvocationOnMock invocation) throws Throwable {
				DataTransfer dataTransfer = mock(DataTransfer.class);
				Mockito.doAnswer(new Answer<Void>() {
					@Override
					public Void answer(InvocationOnMock invocation) throws Throwable {
						instance.setData((String) invocation.getArguments()[0], (String) invocation.getArguments()[1]);
						return null;
					}
				}).when(dataTransfer).setData(Matchers.anyString(), Matchers.anyString());
				Mockito.doAnswer(new Answer<Void>() {
					@Override
					public Void answer(InvocationOnMock invocation) throws Throwable {
						instance.getData((String) invocation.getArguments()[0]);
						return null;
					}
				}).when(dataTransfer).getData(Matchers.anyString());
				return dataTransfer;
			}
		});
	}

	@Test
	public void wrapDragStartHandlerTest() {
		DragStartHandler startHandler = mock(DragStartHandler.class);
		instance.wrap(startHandler);
		verify(draggableWidget).addDragStartHandler(Matchers.any(DragStartEventHandler.class));
	}

	@Test
	public void wrapDragEndHandlerTest() {
		DragEndHandler endHandler = mock(DragEndHandler.class);
		instance.wrap(endHandler);
		verify(draggableWidget).addDragStopHandler(Matchers.any(DragStopEventHandler.class));
	}

	DragStartEventHandler startHandler;

	@Test
	public void dragStartHandlerTest() {
		DragStartHandler dragStartHandler = mock(DragStartHandler.class);
		ArgumentCaptor<DragStartEventWrapper> captor = ArgumentCaptor.forClass(DragStartEventWrapper.class);
		when(draggableWidget.addDragStartHandler(Matchers.any(DragStartEventHandler.class))).then(new Answer<HandlerRegistration>() {
			@Override
			public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
				startHandler = (DragStartEventHandler) invocation.getArguments()[0];
				return null;
			}
		});
		doNothing().when(instance).setData(Matchers.anyString(), Matchers.anyString());
		doReturn(null).when(instance).getData(Matchers.anyString());
		instance.wrap(dragStartHandler);
		startHandler.onDragStart(Mockito.mock(DragStartEvent.class));
		verify(dragStartHandler).onDragStart(captor.capture());
		DragStartEventWrapper event = captor.getValue();
		event.setData("text", "text");
		event.getData("text");
		verify(instance).setData(Matchers.eq("text"), Matchers.eq("text"));
		verify(instance).getData(Matchers.eq("text"));
	}

	DragStopEventHandler stopHandler;

	@Test
	public void dragEndHandlerTest() {
		DragEndHandler endHandler = mock(DragEndHandler.class);
		ArgumentCaptor<DragEndEventWrapper> captor = ArgumentCaptor.forClass(DragEndEventWrapper.class);
		when(draggableWidget.addDragStopHandler(Matchers.any(DragStopEventHandler.class))).then(new Answer<HandlerRegistration>() {
			@Override
			public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
				stopHandler = (DragStopEventHandler) invocation.getArguments()[0];
				return null;
			}
		});
		doNothing().when(instance).setData(Matchers.anyString(), Matchers.anyString());
		doReturn(null).when(instance).getData(Matchers.anyString());
		instance.wrap(endHandler);
		stopHandler.onDragStop(mock(DragStopEvent.class));
		verify(endHandler).onDragEnd(captor.capture());
		DragEndEventWrapper event = captor.getValue();
		event.setData("text", "text");
		event.getData("text");
		verify(instance).setData(Matchers.eq("text"), Matchers.eq("text"));
		verify(instance).getData(Matchers.eq("text"));

	}

}
