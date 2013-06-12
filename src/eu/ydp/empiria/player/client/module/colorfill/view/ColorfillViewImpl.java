package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.module.colorfill.view.mark.CorrectAnswersMarkingPanel;
import eu.ydp.empiria.player.client.module.colorfill.view.mark.WrongAnswersMarkingPanel;

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
	
	@UiField
	AbsolutePanel imageContainer;
	
	@UiField(provided = true)
	ColorfillCorrectImage correctImageCanvas;
	
	@UiField(provided = true)
	CorrectAnswersMarkingPanel correctAnswersMarkingPanel;
	
	@UiField(provided = true)
	WrongAnswersMarkingPanel wrongAnswersMarkingPanel;

	@Inject
	public ColorfillViewImpl(ColorfillCanvas canvas, ColorfillPalette palette, ColorfillCorrectImage correctImageCanvas, 
			CorrectAnswersMarkingPanel correctAnswersMarkingPanel, WrongAnswersMarkingPanel wrongAnswersMarkingPanel) {
		this.canvas = canvas;
		this.palette = palette;
		this.correctImageCanvas = correctImageCanvas;
		this.correctAnswersMarkingPanel = correctAnswersMarkingPanel;
		this.wrongAnswersMarkingPanel = wrongAnswersMarkingPanel;
		
		
		uiBinder.createAndBindUi(this);
	}
	
	@Override
	public Widget asWidget() {
		return container;
	}

	@Override
	public void setImage(Image image) {
		String px = Unit.PX.toString().toLowerCase();
		String width = image.getWidth() + px;
		String height = image.getHeight() + px;
		imageContainer.setSize(width, height);
		
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

	@Override
	public void setCorrectImage(Image correctImage) {
		correctImageCanvas.setImageUrl(correctImage);
	}

	@Override
	public void showUserAnswers(){
		canvas.asWidget().setVisible(true);
		correctImageCanvas.hide();
	}
	
	@Override
	public void showCorrectAnswers(){
		canvas.asWidget().setVisible(false);
		correctImageCanvas.show();
	}

	@Override
	public void markCorrectAnswers(List<Area> pointsToMark) {
		correctAnswersMarkingPanel.markAndShow(pointsToMark);
	}

	@Override
	public void unmarkCorrectAnswers() {
		correctAnswersMarkingPanel.clearAndHide();
	}

	@Override
	public void markWrongAnswers(List<Area> pointsToMark) {
		wrongAnswersMarkingPanel.markAndShow(pointsToMark);
	}

	@Override
	public void unmarkWrongAnswers() {
		wrongAnswersMarkingPanel.clearAndHide();
	}
}
