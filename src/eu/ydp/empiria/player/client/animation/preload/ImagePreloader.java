package eu.ydp.empiria.player.client.animation.preload;

import static com.google.gwt.dom.client.Style.BorderStyle.NONE;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.util.geom.Size;

public class ImagePreloader {

	private static final int WIDGET_COORD_PX = -1000;

	public void preload(final String src, final ImagePreloadHandler preloadHandler){
		Image img = new Image(src);
		updateStyles(img);
		addHandler(preloadHandler, img);
		RootPanel.get().add(img);
	}
	
	private void updateStyles(final Image img) {
		Style style = img.getElement().getStyle();
		style.setLeft(WIDGET_COORD_PX, Unit.PX);
		style.setTop (WIDGET_COORD_PX, Unit.PX);
		style.setPosition(Position.ABSOLUTE);
		style.setPadding(0, Unit.PX);
		style.setMargin(0, Unit.PX);
		style.setBorderStyle(NONE);
	}

	private void addHandler(final ImagePreloadHandler preloadHandler, final Image img) {
		img.addLoadHandler(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {
				preloadHandler.onLoad(new Size(img.getOffsetWidth(), img.getOffsetHeight()));
				img.removeFromParent();
			}
		});
	}
}
