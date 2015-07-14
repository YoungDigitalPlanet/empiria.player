package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.common.collect.Maps;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.event.factory.Command;

import java.util.Map;

public class ColorfillPaletteImpl implements ColorfillPalette {

    private static ColorfillPaletteUiBinder uiBinder = GWT.create(ColorfillPaletteUiBinder.class);

    @UiTemplate("ColorfillPalette.ui.xml")
    interface ColorfillPaletteUiBinder extends UiBinder<Widget, ColorfillPaletteImpl> {
    }

    @UiField
    FlowPanel container;

    @Inject
    private PaletteButtonCreator buttonCreator;

    private ColorfillButtonClickListener listener;

    private final Map<ColorModel, PaletteButton> buttons = Maps.newHashMap();

    public ColorfillPaletteImpl() {
        uiBinder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return container;
    }

    @Override
    public void createButton(final ColorModel color, String description) {
        Command command = createButtonClickCommand(color);
        PaletteButton button = buttonCreator.createButton(color, command, description);
        integrateButton(color, button);
    }

    private void integrateButton(final ColorModel color, PaletteButton button) {
        buttons.put(color, button);
        container.add(button);
    }

    private Command createButtonClickCommand(final ColorModel color) {
        return new Command() {

            @Override
            public void execute(NativeEvent event) {
                if (listener != null) {
                    listener.onButtonClick(color);
                }
            }
        };
    }

    @Override
    public void selectButton(ColorModel color) {
        if (buttons.containsKey(color)) {
            buttons.get(color).select();
        }
    }

    @Override
    public void deselectButton(ColorModel color) {
        if (buttons.containsKey(color)) {
            buttons.get(color).deselect();
        }
    }

    @Override
    public void setButtonClickListener(ColorfillButtonClickListener listener) {
        this.listener = listener;
    }

}
