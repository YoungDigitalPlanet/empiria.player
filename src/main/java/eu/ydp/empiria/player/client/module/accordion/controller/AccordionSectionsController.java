package eu.ydp.empiria.player.client.module.accordion.controller;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.module.accordion.view.section.AccordionSectionView;

public abstract class AccordionSectionsController {

    protected Optional<AccordionSectionView> currentView;

    public AccordionSectionsController() {
        currentView = Optional.absent();
    }

    protected void show(AccordionSectionView sectionView){
        hide();
        sectionView.showHorizontally();
        sectionView.showVertically();
        currentView = Optional.of(sectionView);
    }

    protected abstract void hide();

    public void onClick(AccordionSectionView sectionView) {
        if (currentView.isPresent() && currentView.get() == sectionView) {
            hide();
        } else {
            show(sectionView);
        }
    }
}
