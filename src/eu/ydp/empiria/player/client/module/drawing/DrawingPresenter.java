package eu.ydp.empiria.player.client.module.drawing;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DrawingPresenter {
	@Inject @ModuleScoped private DrawingBean bean;
	@Inject private DrawingView drawingView;
	@Inject private ToolboxPresenter toolboxPresenter;
	@Inject private CanvasPresenter canvasPresenter;

	public void init(){}
	public void reset(){}

	public IsWidget getView() {
		return drawingView.asWidget();
	}

}
