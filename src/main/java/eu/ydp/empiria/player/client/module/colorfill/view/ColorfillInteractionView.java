package eu.ydp.empiria.player.client.module.colorfill.view;

import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;

import java.util.List;

public interface ColorfillInteractionView extends ColorfillCanvas, ColorfillPalette {

    void setCorrectImage(Image correctlyColoredImage);

    void showUserAnswers();

    void showCorrectAnswers();

    void markCorrectAnswers(List<Area> pointsToMark);

    void unmarkCorrectAnswers();

    void markWrongAnswers(List<Area> pointsToMark);

    void unmarkWrongAnswers();

}
