package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.util.position.Point;

public class ColorfillInteractionViewImpl implements ColorfillInteractionView {

	@Inject
	private ColorfillInteractionViewWidget viewWidget;
	
	@Override
	public Widget asWidget() {
		return viewWidget.asWidget();
	}

	@Override
	public void createButton(ColorModel color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectButton(ColorModel color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectButton(ColorModel color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setButtonClickListener(ColorfillButtonClickListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColor(Point point, ColorModel color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ColorModel getColor(Point point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColors(Map<Point, ColorModel> colors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAreaClickListener(ColorfillAreaClickListener listener) {
		// TODO Auto-generated method stub
		
	}

}
