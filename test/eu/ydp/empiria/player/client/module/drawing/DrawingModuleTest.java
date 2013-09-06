package eu.ydp.empiria.player.client.module.drawing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;







import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommand;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandFactory;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandType;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.ImageBean;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class DrawingModuleTest {

	@InjectMocks
	private DrawingModule drawingModule;
	
	@Mock
	private DrawingBean bean;
	@Mock
	private DrawingView drawingView;
	@Mock
	private ToolboxPresenter toolboxPresenter;
	@Mock
	private CanvasPresenter canvasPresenter;
	@Mock
	private DrawCommandFactory factory;
	
	
	@Test
	public void shouldResetViewOnCanvas() throws Exception {
		//given
		DrawCommand clearAllCommand = Mockito.mock(DrawCommand.class);
		when(factory.createCommand(DrawCommandType.CLEAR_ALL))
			.thenReturn(clearAllCommand);
		
		//when
		drawingModule.reset();
		
		//then
		verify(clearAllCommand).execute();
	}
	
	@Test
	public void shouldInitializeModule() throws Exception {
		//given
		ImageBean image = getSampleImageBean();
		when(bean.getImage())
			.thenReturn(image);
		
		//when
		drawingModule.initModule(null);
		
		//then
		ArgumentCaptor<Size> sizeCaptor = ArgumentCaptor.forClass(Size.class);
		verify(canvasPresenter).setImage(eq(image.getSrc()), sizeCaptor.capture());
		
		Size imageSize = sizeCaptor.getValue();
		assertEquals(image.getWidth(), imageSize.getWidth());
		assertEquals(image.getHeight(), imageSize.getHeight());
		
		verify(toolboxPresenter).init();
	}

	private ImageBean getSampleImageBean() {
		ImageBean image = new ImageBean();
		image.setHeight(10);
		image.setWidth(123);
		image.setSrc("src");
		return image;
	}
}
