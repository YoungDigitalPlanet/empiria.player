package eu.ydp.empiria.player.client.module.accordion.controller;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;

public class AccordionController<T extends AccordionHideController> {

    private Optional<AccordionSectionPresenter> currentSection;
    private T hideController;

    @Inject
    public AccordionController(T hideController) {
        this.hideController = hideController;
        currentSection = Optional.absent();
    }

    private void show(AccordionSectionPresenter section) {
        if (currentSection.isPresent()) {
            hideController.hide(currentSection.get());
        }

        section.showHorizontally();
        section.showVertically();
        currentSection = Optional.of(section);
    }

    public void onClick(AccordionSectionPresenter section) {
        if (currentSection.isPresent() && currentSection.get() == section) {
            hideController.hide(currentSection.get());
            currentSection = Optional.absent();
        } else {
            show(section);
        }
    }
}
