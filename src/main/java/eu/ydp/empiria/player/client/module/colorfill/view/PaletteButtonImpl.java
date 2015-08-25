package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillStyleNameConstants;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.ui.InputToButtonReplacer;

public class PaletteButtonImpl extends Composite implements PaletteButton {

    private static PaletteButtonUiBinder uiBinder = GWT.create(PaletteButtonUiBinder.class);

    @UiTemplate("PaletteButton.ui.xml")
    interface PaletteButtonUiBinder extends UiBinder<Widget, PaletteButtonImpl> {
    }

    @UiField
    FlowPanel container;

    @UiField
    PushButton button;

    @UiField
    FlowPanel description;

    private ColorfillStyleNameConstants styleNameConstants;

    @Inject
    public PaletteButtonImpl(ColorfillStyleNameConstants styleNameConstants, InputToButtonReplacer inputReplacer) {
        initWidget(uiBinder.createAndBindUi(this));
        this.styleNameConstants = styleNameConstants;
        inputReplacer.changeInputTypeChildToButton(button);
    }

    @Override
    public void setColor(ColorModel color) {
        container.addStyleDependentName(color.toStringRgba());
    }

    @Override
    public void select() {
        container.addStyleName(styleNameConstants.QP_COLORFILL_PALETTE_BUTTON_CONTAINER_SELECTED());
    }

    @Override
    public void deselect() {
        container.removeStyleName(styleNameConstants.QP_COLORFILL_PALETTE_BUTTON_CONTAINER_SELECTED());
    }

    @Override
    public void setDescription(String description) {
        this.description.getElement().setInnerHTML(description);
    }
}
