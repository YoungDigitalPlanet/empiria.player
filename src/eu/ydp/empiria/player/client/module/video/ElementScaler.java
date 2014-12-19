package eu.ydp.empiria.player.client.module.video;

import com.google.gwt.dom.client.*;

public class ElementScaler {

	private final String MAX_WIDTH_PROPERTY = "maxWidth";

	public void scale(Element element) {
		Style style = element.getStyle();
		int width = element.getClientWidth();
		int height = element.getClientHeight();

		style.clearWidth();
		style.clearHeight();

		double ratio = 100.0 * height / width;
		style.setPaddingTop(ratio, Style.Unit.PCT);
		style.setProperty(MAX_WIDTH_PROPERTY, width, Style.Unit.PX);
	}
}
