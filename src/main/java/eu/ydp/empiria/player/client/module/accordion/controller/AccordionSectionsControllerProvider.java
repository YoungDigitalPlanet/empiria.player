package eu.ydp.empiria.player.client.module.accordion.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.accordion.Transition;

@Singleton
public class AccordionSectionsControllerProvider {

    @Inject
    private Provider<AccordionController<AccordionBothDirectionsController>> bothDirectionsController;
    @Inject
    private Provider<AccordionController<AccordionHorizontalController>> horizontalController;
    @Inject
    private Provider<AccordionController<AccordionVerticalController>> verticalController;

    public AccordionController getController(Transition transition) {
        switch (transition) {
            case ALL:
                return bothDirectionsController.get();
            case WIDTH:
                return horizontalController.get();
            case HEIGHT:
                return verticalController.get();
            default:
                return bothDirectionsController.get();
        }
    }
}
