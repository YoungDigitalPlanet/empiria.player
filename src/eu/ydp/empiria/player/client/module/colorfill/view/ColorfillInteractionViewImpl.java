package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ColorfillInteractionViewImpl implements ColorfillInteractionView {

	@Inject
	private ColorfillInteractionViewWidget viewWidget;
	
	@Override
	public Widget asWidget() {
		return viewWidget.asWidget();
	}

}
