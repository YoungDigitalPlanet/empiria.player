package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxView;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxViewImpl;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasView;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasViewImpl;


public class DrawingGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(CanvasView.class).to(CanvasViewImpl.class);
		bind(ToolboxView.class).to(ToolboxViewImpl.class);
	}

}
