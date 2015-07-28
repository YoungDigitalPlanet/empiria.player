package eu.ydp.empiria.player.client.module.accordion.controller;


import com.google.common.base.Optional;

public class AccordionSectionsVerticalController extends AccordionSectionsController {

    @Override
    public void hide() {
        if (currentView.isPresent()) {
            currentView.get().hideVertically();
            currentView = Optional.absent();
        }
    }
}
