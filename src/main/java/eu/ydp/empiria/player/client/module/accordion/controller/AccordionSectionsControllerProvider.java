package eu.ydp.empiria.player.client.module.accordion.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.accordion.Transition;

public class AccordionSectionsControllerProvider {

    @Inject
    private Provider<AccordionSectionsBothDirectionsController> bothDirectionsController;
    @Inject
    private Provider<AccordionSectionsHorizontalController> horizontalController;
    @Inject
    private Provider<AccordionSectionsVerticalController> verticalController;

    public AccordionSectionsController getController(Transition transition) {
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
