package eu.ydp.empiria.player.client.module.draggap.dragging;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.draggap.DragGapModuleFactory;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.ui.drop.FlowPanelWithDropZone;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import eu.ydp.gwtutil.client.Wrapper;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({Widget.class})
public class DragDropControllerTest {

	private DragDropController dragDropController;
	private DragGapView dragGapView;
	private DragGapModuleFactory dragGapModuleFactory;
	private final String moduleIdentifier = "moduleId";
	private final Wrapper<String> itemIdWrapper = Wrapper.of("itemId");
	private DropZoneGuardian dropZoneGuardian;
	private SourceListConnectedDragHandler dragHandler;
	
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
		dragGapView = Mockito.mock(DragGapView.class);
		dragGapModuleFactory = Mockito.mock(DragGapModuleFactory.class);
		dragDropController = new DragDropController(dragGapView, dragGapModuleFactory);
	}

	@Test
	public void shouldSetUpDragHandlers() throws Exception {
		mockDragHandlerCreation();
		
		dragDropController.initializeDrag(moduleIdentifier, itemIdWrapper);
		
		verify(dragGapView).setDragStartHandler(dragHandler);
		verify(dragGapView).setDragEndHandler(dragHandler);
	}

	@Test
	public void shouldLockDropZone() throws Exception {
		mockDropEnabling();
		dragDropController.initializeDrop(moduleIdentifier);
		
		//when
		dragDropController.lockDropZone();
		
		//then
		verify(dropZoneGuardian).lockDropZone();
	}
	
	@Test
	public void shouldUnlockDropZone() throws Exception {
		mockDropEnabling();
		dragDropController.initializeDrop(moduleIdentifier);
		
		//when
		dragDropController.unlockDropZone();
		
		//then
		verify(dropZoneGuardian).unlockDropZone();
	}
	
	private void mockDragHandlerCreation() {
		dragHandler = Mockito.mock(SourceListConnectedDragHandler.class);
		when(dragGapModuleFactory.createSourceListConnectedDragHandler(moduleIdentifier, itemIdWrapper))
			.thenReturn(dragHandler);
	}

	private void mockDropEnabling() {
		SourceListConnectedDropHandler dropHandler = Mockito.mock(SourceListConnectedDropHandler.class);
		when(dragGapModuleFactory.createSourceListConnectedDropHandler(moduleIdentifier))
			.thenReturn(dropHandler);

		@SuppressWarnings("unchecked")
		DroppableObject<FlowPanelWithDropZone> droppable = Mockito.mock(DroppableObject.class);
		when(dragGapView.enableDropCapabilities())
			.thenReturn(droppable);
		
		Widget droppableWidget = Mockito.mock(Widget.class);
		when(droppable.getDroppableWidget())
			.thenReturn(droppableWidget);
		
		dropZoneGuardian = Mockito.mock(DropZoneGuardian.class);
		when(dragGapModuleFactory.createDropZoneGuardian(droppable, droppableWidget))
			.thenReturn(dropZoneGuardian);
	}
}
