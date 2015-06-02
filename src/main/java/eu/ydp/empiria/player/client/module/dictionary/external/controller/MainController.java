package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Panel;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.MainView;

public class MainController implements WordsLoadingListener, ExplanationListener {

    @Inject
    private WordsController wordsController;
    @Inject
    private MainView mainView;
    @Inject
    private ExplanationController explanationController;

    private Panel wrappingPanel;
    private boolean shouldBeInitialized = true;

    public void init(Panel panel) {
        if (shouldBeInitialized) {
            this.wrappingPanel = panel;
            wordsController.load();
            explanationController.init();
            shouldBeInitialized = false;
        }
    }

    @Override
    public void onWordsLoaded() {
        mainView.init();
        wrappingPanel.add(mainView);
        mainView.addViewToContainerView(explanationController.getView());

        if (Options.getViewType() == ViewType.HALF) {
            explanationController.show();
        }
    }

    @Override
    public void onEntryLoaded(final Entry entry) {
        if (Options.getViewType() == ViewType.HALF) {
            mainView.hideMenu();
            explanationController.show();
        }

        if (entry != null) {
            processEntry(entry);
        }
    }

    private void processEntry(Entry entry) {
        explanationController.processEntry(entry);
    }

    @Override
    public void onBackClick() {
        if (Options.getViewType() == ViewType.HALF) {
            mainView.showMenu();
            explanationController.hide();
        }
    }
}