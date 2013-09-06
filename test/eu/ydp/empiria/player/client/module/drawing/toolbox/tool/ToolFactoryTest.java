package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModel;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;

@RunWith(MockitoJUnitRunner.class)
public class ToolFactoryTest {

	@InjectMocks
	private ToolFactory factory;

	@Mock
	private DrawCanvas drawCanvas;
	
	@Test
	public void shouldCreatePencilTool() throws Exception {
		//given
		ToolboxModel toolModel = new ToolboxModel();
		toolModel.setToolType(ToolType.PENCIL);
		
		//when
		Tool tool = factory.createTool(toolModel);
		
		//then
		assertTrue(tool instanceof PencilTool);
	}
	
	@Test
	public void shouldCreateEraserTool() throws Exception {
		//given
		ToolboxModel toolModel = new ToolboxModel();
		toolModel.setToolType(ToolType.ERASER);
		
		//when
		Tool tool = factory.createTool(toolModel);
		
		//then
		assertTrue(tool instanceof EraserTool);
	}
}
