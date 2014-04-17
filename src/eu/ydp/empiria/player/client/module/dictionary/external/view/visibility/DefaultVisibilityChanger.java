package eu.ydp.empiria.player.client.module.dictionary.external.view.visibility;

import com.google.gwt.dom.client.Style.Display;

public class DefaultVisibilityChanger implements VisibilityChanger {

	@Override
	public void show(VisibilityClient client) {
		client.getElementStyle().setDisplay(Display.BLOCK);
	}

	@Override
	public void hide(VisibilityClient client) {
		client.getElementStyle().setDisplay(Display.NONE);
	}

}