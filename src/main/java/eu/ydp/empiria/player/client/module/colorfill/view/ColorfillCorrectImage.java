package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

import javax.annotation.PostConstruct;

public class ColorfillCorrectImage implements IsWidget {

    private final FlowPanel image;
    private final StyleNameConstants styleNameConstants;

    @Inject
    public ColorfillCorrectImage(StyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
        image = new FlowPanel();
    }

    @PostConstruct
    public void initializeView() {
        hide();
        image.setStyleName(styleNameConstants.QP_COLORFILL_CORRECT_IMG());
    }

    public void setImageUrl(Image correctImage) {
        Style style = image.getElement().getStyle();
        style.setBackgroundImage("url(" + correctImage.getSrc() + ")");

        String px = Unit.PX.toString().toLowerCase();
        String width = correctImage.getWidth() + px;
        String height = correctImage.getHeight() + px;
        image.setSize(width, height);
    }

    @Override
    public Widget asWidget() {
        return image;
    }

    public void hide() {
        image.setVisible(false);
    }

    public void show() {
        image.setVisible(true);
    }

}
