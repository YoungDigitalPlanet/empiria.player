package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ColorfillPalette extends Composite {

	private static ColorfillPaletteUiBinder uiBinder = GWT.create(ColorfillPaletteUiBinder.class);

	interface ColorfillPaletteUiBinder extends UiBinder<Widget, ColorfillPalette> {
	}

	public ColorfillPalette() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
