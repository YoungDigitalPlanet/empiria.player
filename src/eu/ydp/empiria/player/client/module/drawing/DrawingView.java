package eu.ydp.empiria.player.client.module.drawing;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxView;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DrawingView extends Composite {

	private static DrawingViewUiBinder uiBinder = GWT.create(DrawingViewUiBinder.class);

	interface DrawingViewUiBinder extends UiBinder<Widget, DrawingView> {
	}

	@UiField(provided = true)
	ToolboxView toolboxView;
	@UiField(provided = true)
	CanvasView canvasView;

	@Inject
	public DrawingView(@ModuleScoped ToolboxView toolboxView, @ModuleScoped CanvasView canvasView) {
		this.toolboxView = toolboxView;
		this.canvasView = canvasView;
		initWidget(uiBinder.createAndBindUi(this));
	}
}
