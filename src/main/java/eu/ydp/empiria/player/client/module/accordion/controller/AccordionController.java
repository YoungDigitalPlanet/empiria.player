package eu.ydp.empiria.player.client.module.accordion.controller;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.WINDOW_RESIZED;

public class AccordionController<T extends AccordionHideController> implements PlayerEventHandler {

    private Optional<AccordionSectionPresenter> currentSection;
    private T hideController;

    @Inject
    public AccordionController(T hideController, EventsBus eventsBus) {
        this.hideController = hideController;
        currentSection = Optional.absent();
        eventsBus.addHandler(PlayerEvent.getType(WINDOW_RESIZED), this);
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

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (currentSection.isPresent()) {
            currentSection.get().showHorizontally();
            currentSection.get().showVertically();
        }
    }
}
