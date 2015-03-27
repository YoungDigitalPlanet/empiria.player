package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.common.base.Optional;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class CanvasImageView implements IsWidget {

	private final FlowPanel mainPanel = new FlowPanel();

	private int height;

	private int width;

	private Canvas canvas;

	private Image imageView;

	private Optional<LoadHandler> loadHandler = Optional.absent();

	public void setImageUrl(String src, int width, int height) {
		this.width = width;
		this.height = height;
		imageView = new Image();
		imageView.setUrl(src);
		RootPanel.get().add(imageView);
		imageView.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(final LoadEvent event) {
				imageView.removeFromParent();
				createCanvasAndAddToView(imageView);
				if (loadHandler.isPresent()) {
					loadHandler.get().onLoad(event);
				}
			}
		});
	}

	private void createCanvasAndAddToView(Image image) {
		canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(height);
		canvas.setCoordinateSpaceWidth(width);
		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.getContext2d().drawImage((ImageElement) image.getElement().cast(), 0, 0);
		mainPanel.add(canvas);
	}

	public void reload() {
		canvas.getContext2d().clearRect(0, 0, width, height);
		canvas.getContext2d().drawImage((ImageElement) imageView.getElement().cast(), 0, 0);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}

	public void setImageLoadHandler(LoadHandler loadHandler) {
		this.loadHandler = Optional.fromNullable(loadHandler);
	}

	public void setPanelStyle(String panelStyle) {
		mainPanel.setStyleName(panelStyle);
	}
}
