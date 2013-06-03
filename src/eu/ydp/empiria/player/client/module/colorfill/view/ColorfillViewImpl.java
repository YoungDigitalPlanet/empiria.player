package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;

public class ColorfillViewImpl implements ColorfillInteractionView {

	private static ColorfillViewWidgetUiBinder uiBinder = GWT.create(ColorfillViewWidgetUiBinder.class);

	@UiTemplate("ColorfillView.ui.xml")
	interface ColorfillViewWidgetUiBinder extends UiBinder<Widget, ColorfillViewImpl> {}

	@UiField
	FlowPanel container;
	
	@UiField(provided = true)
	ColorfillCanvas canvas;
	
	@UiField(provided = true)
	ColorfillPalette palette;
	
	@Inject
	public ColorfillViewImpl(ColorfillCanvas canvas, ColorfillPalette palette) {
		this.canvas = canvas;
		this.palette = palette;
		uiBinder.createAndBindUi(this);
	}
	
	@Override
	public Widget asWidget() {
		return container;
	}

	@Override
	public void setImage(Image image) {
		canvas.setImage(image);
	}

	@Override
	public void createButton(ColorModel color) {
		palette.createButton(color);
	}

	@Override
	public void selectButton(ColorModel color) {
		palette.selectButton(color);
	}

	@Override
	public void deselectButton(ColorModel color) {
		palette.deselectButton(color);
	}

	@Override
	public void setButtonClickListener(ColorfillButtonClickListener listener) {
		palette.setButtonClickListener(listener);
	}

	@Override
	public void setColor(Area area, ColorModel color) {
		canvas.setColor(area, color);
	}

	@Override
	public ColorModel getColor(Area area) {
		return canvas.getColor(area);
	}

	@Override
	public void setColors(Map<Area, ColorModel> colors) {
		canvas.setColors(colors);
	}

	@Override
	public void setAreaClickListener(ColorfillAreaClickListener listener) {
		canvas.setAreaClickListener(listener);
	}

	@Override
	public void reset() {
		canvas.reset();
	}

}
