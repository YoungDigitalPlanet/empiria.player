package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.gwtutil.client.geom.Rectangle;

public class WidgetSizeHelper {

	public Rectangle toRectange(IsWidget isWidget) {
		Widget widget = isWidget.asWidget();
		return new Rectangle(widget.getAbsoluteLeft(), widget.getAbsoluteTop(), widget.getOffsetWidth(), widget.getOffsetHeight());
	}
}
