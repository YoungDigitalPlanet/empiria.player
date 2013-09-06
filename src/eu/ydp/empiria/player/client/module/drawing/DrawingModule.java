package eu.ydp.empiria.player.client.module.drawing;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.IResetable;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.ImageBean;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.geom.Size;

public class DrawingModule extends SimpleModuleBase implements IResetable {

	@Inject
	@ModuleScoped
	private DrawingBean bean;
	@Inject
	private DrawingView drawingView;
	@Inject
	private ToolboxPresenter toolboxPresenter;
	@Inject
	private CanvasPresenter canvasPresenter;
	
	@Override
	public void reset() {
		canvasPresenter.getView().clear();
	}
	
	@Override
	public Widget getView() {
		return drawingView.asWidget();
	}
	
	@Override
	protected void initModule(Element element) {
		initializeCanvas();
		toolboxPresenter.init();
	}

	private void initializeCanvas() {
		ImageBean image = bean.getImage();
		String src = image.getSrc();
		Size size = new Size(image.getWidth(), image.getHeight());
		canvasPresenter.setImage(src, size);
	}

}
