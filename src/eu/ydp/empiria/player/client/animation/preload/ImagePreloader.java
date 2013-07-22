package eu.ydp.empiria.player.client.animation.preload;

import static com.google.gwt.dom.client.Style.BorderStyle.NONE;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.util.geom.Size;

public class ImagePreloader {

	private static final int WIDGET_COORD_PX = -1000;

	public HandlerRegistration preload(final String src, final ImagePreloadHandler preloadHandler,final ImagePreloadErrorHandler errorHandler){
		Image img = new Image(src);
		updateStyles(img);
		HandlerRegistration handlerRegistration = addHandler(preloadHandler,errorHandler, img);
		RootPanel.get().add(img);
		return handlerRegistration;
	}

	private void updateStyles(final Image img) {
		Style style = img.getElement().getStyle();
		style.setLeft(WIDGET_COORD_PX, Unit.PX);
		style.setTop (WIDGET_COORD_PX, Unit.PX);
		style.setPosition(Position.ABSOLUTE);
		style.setPadding(0, Unit.PX);
		style.setMargin(0, Unit.PX);
		style.setBorderStyle(NONE);
		style.setProperty("maxWidth", "none");
	}

	private HandlerRegistration addHandler(final ImagePreloadHandler preloadHandler, ImagePreloadErrorHandler errorHandler, final Image img) {
		addErrorHandler(errorHandler, img);
		final HandlerRegistration handlerRegistration = addLoadHandler(preloadHandler, img);
		return handlerRegistration;
	}

	private HandlerRegistration addLoadHandler(final ImagePreloadHandler preloadHandler, final Image img) {
		HandlerRegistration handlerRegistration = img.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(final LoadEvent event) {
				preloadHandler.onLoad(new Size(img.getOffsetWidth(), img.getOffsetHeight()));
				img.removeFromParent();
			}
		});
		return handlerRegistration;
	}

	private void addErrorHandler(final ImagePreloadErrorHandler errorHandler, final Image img) {
		img.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(final ErrorEvent event) {
				errorHandler.onError();
				img.removeFromParent();
			}
		});
	}
}
