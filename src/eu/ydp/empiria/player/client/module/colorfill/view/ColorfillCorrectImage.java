package eu.ydp.empiria.player.client.module.colorfill.view;

import javax.annotation.PostConstruct;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class ColorfillCorrectImage implements IsWidget{

	private final CanvasImageView canvasImageView;
	private final StyleNameConstants styleNameConstants;

	@Inject
	public ColorfillCorrectImage(CanvasImageView canvasImageView, StyleNameConstants styleNameConstants) {
		this.canvasImageView = canvasImageView;
		this.styleNameConstants = styleNameConstants;
	}
	
	@PostConstruct
	public void initializeView(){
		hide();
		canvasImageView.setPanelStyle(styleNameConstants.QP_COLORFILL_CORRECT_IMG());
	}

	public void setImageUrl(Image correctImage) {
		canvasImageView.setImageUrl(correctImage.getSrc(), correctImage.getWidth(), correctImage.getHeight());
	}

	@Override
	public Widget asWidget() {
		return canvasImageView.asWidget();
	}
	
	public void hide(){
		canvasImageView.asWidget().setVisible(false);
	}
	
	public void show(){
		canvasImageView.asWidget().setVisible(true);
	}
	
}
