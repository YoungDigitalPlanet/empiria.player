package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;

public class ColorfillViewImpl implements ColorfillInteractionView {

	private static ColorfillViewWidgetUiBinder uiBinder = GWT.create(ColorfillViewWidgetUiBinder.class);

	@UiTemplate("ColorfillView.ui.xml")
	interface ColorfillViewWidgetUiBinder extends UiBinder<Widget, ColorfillViewImpl> {}

//	@UiField   commented out because of broken build
	ColorfillCanvas canvas;
	
	public ColorfillViewImpl() {
		uiBinder.createAndBindUi(this);
	}
	
	@Override
	public Widget asWidget() {
		throw new UnsupportedOperationException();
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
	public void setColor(Area area, ColorModel color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ColorModel getColor(Area area) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColors(Map<Area, ColorModel> colors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAreaClickListener(ColorfillAreaClickListener listener) {
		canvas.setAreaClickListener(listener);
	}

	@Override
	public void setImage(Image image) {
		// TODO Auto-generated method stub
		
	}

}
