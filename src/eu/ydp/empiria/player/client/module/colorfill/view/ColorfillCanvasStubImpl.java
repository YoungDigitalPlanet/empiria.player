package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;

public class ColorfillCanvasStubImpl implements ColorfillCanvas {

	@Override
	public Widget asWidget() {
		return new FlowPanel();
	}

	@Override
	public void setImage(Image image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(Area area, ColorModel color) {
		// TODO Auto-generated method stub

	}

	@Override
	public ColorModel getColor(Area area) {
		// TODO Auto-generated method stub
		return ColorModel.createEraser();
	}

	@Override
	public void setColors(Map<Area, ColorModel> colors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAreaClickListener(ColorfillAreaClickListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
