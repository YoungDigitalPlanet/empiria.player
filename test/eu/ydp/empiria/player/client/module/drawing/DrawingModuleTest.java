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
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.ImageBean;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasView;
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
	
	@Test
	public void shouldResetViewOnCanvas() throws Exception {
		//given
		CanvasView canvasView = Mockito.mock(CanvasView.class);
		when(canvasPresenter.getView())
			.thenReturn(canvasView);
		
		//when
		drawingModule.reset();
		
		//then
		verify(canvasView).clear();
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
