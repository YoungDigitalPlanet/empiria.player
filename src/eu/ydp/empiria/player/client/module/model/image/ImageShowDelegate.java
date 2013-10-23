package eu.ydp.empiria.player.client.module.model.image;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.gwtutil.client.util.geom.Size;
import eu.ydp.gwtutil.client.util.geom.WidgetSize;

public class ImageShowDelegate {

	private ShowImageDTO dto;

	public ImageShowDelegate(ShowImageDTO dto) {
		this.dto = dto;
	}

	public void showOnWidget(Widget content) {
		String srcWithUrlInside = createPath(dto.path);
		setImage(content, srcWithUrlInside);
		setSize(dto.size, content);
	}

	private String createPath(String path) {
		return "url(" + path + ")";
	}

	private void setImage(Widget content, String srcWithUrlInside) {
		Style style = content.getElement().getStyle();
		style.setBackgroundImage(srcWithUrlInside);
	}

	private void setSize(Size size, Widget content) {
		WidgetSize widgetSize = new WidgetSize(size);
		widgetSize.setOnWidget(content);
	}
}