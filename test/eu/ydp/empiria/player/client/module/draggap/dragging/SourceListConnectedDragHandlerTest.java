package eu.ydp.empiria.player.client.module.draggap.dragging;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.junit.GWTMockUtilities;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.gwtutil.client.Wrapper;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({JavaScriptObject.class, JSONObject.class})
public class SourceListConnectedDragHandlerTest {

	private SourceListConnectedDragHandler dragHandler;
	private final String moduleIdentifier = "moduleId";
	private final Wrapper<String> itemIdWrapper = Wrapper.of("itemId");
	private SourceListManagerAdapter sourcelistManagerAdapter;
	private OverlayTypesParser overlayTypesParser;
	
	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}
	
	@Before
	public void setUp() throws Exception {
		sourcelistManagerAdapter = Mockito.mock(SourceListManagerAdapter.class);
		overlayTypesParser = Mockito.mock(OverlayTypesParser.class);
		dragHandler = new SourceListConnectedDragHandler(sourcelistManagerAdapter, overlayTypesParser);
		dragHandler.initialize(moduleIdentifier, itemIdWrapper);
	}

	@Test
	public void shouldInformSourcelistManagerOnDragEnd() throws Exception {
		DragEndEvent event = Mockito.mock(DragEndEvent.class);
		
		dragHandler.onDragEnd(event);
		
		verify(sourcelistManagerAdapter).dragFinished();
	}
	
	@Test
	public void shouldInformSourcelistManagerAndExtendEventDataOnDragStart() throws Exception {
		DragStartEvent event = Mockito.mock(DragStartEvent.class);

		NativeDragDataObject dragDataObject = Mockito.mock(NativeDragDataObject.class);
		when(overlayTypesParser.get())
			.thenReturn(dragDataObject);
		
		String jsonString = "jsonString";
		when(dragDataObject.toJSON())
			.thenReturn(jsonString);
		
		dragHandler.onDragStart(event);
		
		verify(sourcelistManagerAdapter).dragStart();
		
		InOrder inOrder = Mockito.inOrder(overlayTypesParser, dragDataObject);
		inOrder.verify(overlayTypesParser).get();
		inOrder.verify(dragDataObject).setItemId(itemIdWrapper.getInstance());
		inOrder.verify(dragDataObject).setSourceId(moduleIdentifier);
	}
}
