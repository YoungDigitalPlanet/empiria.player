package eu.ydp.empiria.player.client.module.video;

import com.google.gwt.dom.client.*;

public class ElementScaler {

	private final String MAX_WIDTH_PROPERTY = "maxWidth";

	private final Element element;
	private final double initialRatio;

	public ElementScaler(Element element) {
		this.element = element;
		initialRatio = calculateRatio();
	}

	private double calculateRatio() {
		int width = element.getClientWidth();
		int height = element.getClientHeight();
		return 100.0 * height / width;
	}

	public void setRatio() {
		Style style = element.getStyle();

		style.clearWidth();
		style.clearHeight();

		style.setPaddingTop(initialRatio, Style.Unit.PCT);
	}

	public void clearRatio() {
		Style style = element.getStyle();
		style.clearPaddingTop();
	}

	public void setMaxWidth() {
		int width = element.getClientWidth();
		Style style = element.getStyle();
		style.setProperty(MAX_WIDTH_PROPERTY, width, Style.Unit.PX);

	}

	public void clearMaxWidth() {
		Style style = element.getStyle();
		style.clearProperty(MAX_WIDTH_PROPERTY);
	}
}
