package eu.ydp.empiria.player.client.module.dictionary.external.view.visibility;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;

public class AndroidVisibilityChanger implements VisibilityChanger {

	private static final int TOP_HIDDEN = -10000;

	@Override
	public void show(VisibilityClient client) {
		Style style = client.getElementStyle();
		style.setPosition(Position.STATIC);
		style.clearTop();
	}

	@Override
	public void hide(VisibilityClient client) {
		final Style style = client.getElementStyle();
		style.setPosition(Position.FIXED);
		style.setTop(TOP_HIDDEN, Unit.PX);
	}

}
