package eu.ydp.empiria.player.client.module.sourcelist.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.gin.factory.SourceListFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParserMock;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventHandler;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;

@SuppressWarnings("PMD")
public class SourceListViewImplTest extends AbstractTestBaseWithoutAutoInjectorInit {

	final List<SourceListViewItem> itemMocks = new ArrayList<SourceListViewItem>();

	public class SourceListModule implements Module {
		@Override
		public void configure(Binder binder) {
			SourceListFactory factory = mock(SourceListFactory.class);
			when(factory.getSourceListViewItem(Mockito.any(DragDataObject.class), Mockito.any(IModule.class))).thenAnswer(new Answer<SourceListViewItem>() {
				@Override
				public SourceListViewItem answer(InvocationOnMock invocation) throws Throwable {
					SourceListViewItem mock = mock(SourceListViewItem.class);

					itemMocks.add(mock);
					return mock;
				}
			});
			binder.bind(SourceListFactory.class).toInstance(factory);
		}
	}

	protected SourceListViewImpl instance;
	protected IModule module;
	private EventsBus eventbus;

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
		setUp(new Class[0], new Class[0], new Class[] { EventsBus.class }, new SourceListModule());
		instance = spy(injector.getInstance(SourceListViewImpl.class));
		instance.items = mock(FlowPanel.class);
		eventbus = injector.getInstance(EventsBus.class);
		module = mock(IModule.class);
		instance.setIModule(module);
		Mockito.doNothing().when(instance).initWidget();
		doReturn(new DragDataObjectImpl()).when(instance).createDragDataObject();
		SourceListJAXBParserMock parser = new SourceListJAXBParserMock();
		instance.setBean(parser.create().parse(SourceListJAXBParserMock.XML_WITHOUT_MOVE_ELEMENTS));
	}

	@Test
	public void createAndBindUiTest() {
		instance.createAndBindUi();
		verify(eventbus).addHandlerToSource(Mockito.eq(DragDropEvent.getType(DragDropEventTypes.DRAG_END)), Mockito.eq(module),
				Mockito.any(DragDropEventHandler.class), Mockito.any(EventScope.class));
	}

	@Test
	public void testOnItemDragStarted() {
		final DragDataObject dragDataObject = mock(DragDataObject.class);
		final DragStartEvent startEvent = mock(DragStartEvent.class);
		final SourceListViewItem item = mock(SourceListViewItem.class);
		eventbus.addHandler(DragDropEvent.getType(DragDropEventTypes.DRAG_START), new DragDropEventHandler() {

			@Override
			public void onDragEvent(DragDropEvent event) {
				assertEquals(dragDataObject, event.getDragDataObject());
				assertEquals(module, event.getIModule());
			}
		});
		instance.onItemDragStarted(dragDataObject, startEvent, item);
	}


	@Test
	public void findValueTest() {
		instance.createAndBindUi();
		assertTrue(instance.containsValue("psa"));
		assertFalse(instance.containsValue("sss"));
	}

	@Test
	public void hideItemTest() {
		instance.createAndBindUi();
		DragDropEvent event = new DragDropEvent(DragDropEventTypes.DRAG_END, null);
		DragDataObject data = new DragDataObjectImpl();
		data.setValue("psa");
		event.setDragDataObject(data);
		instance.onDragEvent(event);
		verify(itemMocks.get(0)).hide();
	}

	@Test
	public void restoreItemTest() {
		instance.createAndBindUi();
		DragDropEvent event = new DragDropEvent(DragDropEventTypes.DRAG_END, null);
		DragDataObject data = new DragDataObjectImpl();
		data.setValue("psa");
		event.setDragDataObject(data);
		instance.onDragEvent(event);
		verify(itemMocks.get(0)).hide();
		data.setPreviousValue("psa");
		data.setValue("kota");
		instance.onDragEvent(event);
		verify(itemMocks.get(0)).show();
	}

}
