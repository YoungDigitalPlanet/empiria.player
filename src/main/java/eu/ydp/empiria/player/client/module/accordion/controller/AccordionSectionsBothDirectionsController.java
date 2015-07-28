package eu.ydp.empiria.player.client.module.accordion.controller;


import com.google.common.base.Optional;

public class AccordionSectionsBothDirectionsController extends AccordionSectionsController {

    @Override
    public void hide() {
        if (currentView.isPresent()) {
            currentView.get().hideHorizontally();
            currentView.get().hideVertically();
            currentView = Optional.absent();
        }
    }
}
