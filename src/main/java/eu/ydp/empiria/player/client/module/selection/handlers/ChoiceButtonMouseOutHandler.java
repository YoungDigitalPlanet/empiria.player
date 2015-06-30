package eu.ydp.empiria.player.client.module.selection.handlers;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;

public class ChoiceButtonMouseOutHandler implements MouseOutHandler {

    private ChoiceButtonBase button;

    @Inject
    public ChoiceButtonMouseOutHandler(@Assisted ChoiceButtonBase button) {
        this.button = button;
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        button.setMouseOver(false);
    }

}
