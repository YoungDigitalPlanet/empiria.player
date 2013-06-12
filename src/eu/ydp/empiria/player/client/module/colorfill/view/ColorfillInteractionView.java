package eu.ydp.empiria.player.client.module.colorfill.view;

import eu.ydp.empiria.player.client.module.colorfill.structure.Image;

public interface ColorfillInteractionView extends ColorfillCanvas, ColorfillPalette {

	void setCorrectImage(Image correctlyColoredImage);

	void showUserAnswers();

	void showCorrectAnswers();

}
