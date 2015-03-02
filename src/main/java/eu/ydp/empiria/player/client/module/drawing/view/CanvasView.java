package eu.ydp.empiria.player.client.module.drawing.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.gwtutil.client.util.geom.Size;

public interface CanvasView extends DrawCanvas, IsWidget {

	void setBackground(String url);

	void setSize(Size size);

	void initializeInteractionHandlers(CanvasPresenter canvasPresenter);
}
