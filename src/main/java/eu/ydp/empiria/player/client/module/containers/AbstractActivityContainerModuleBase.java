package eu.ydp.empiria.player.client.module.containers;

import eu.ydp.empiria.player.client.module.core.base.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.IActivity;

public abstract class AbstractActivityContainerModuleBase extends ContainerModuleBase implements IActivity {

    private final ModulesActivitiesController modulesActivitiesController;

    private boolean markingAnswers = false;
    private boolean showingAnswers = false;

    public AbstractActivityContainerModuleBase() {
        modulesActivitiesController = new ModulesActivitiesController();
    }

    @Override
    public void markAnswers(boolean mark) {
        if (!mark && markingAnswers || mark && !markingAnswers) {
            showCorrectAnswers(false);
            doMarkAnswers(mark);
        }
    }

    @Override
    public void showCorrectAnswers(boolean show) {
        if (!show && showingAnswers || show && !showingAnswers) {
            markAnswers(false);
            doShowCorrectAnswers(show);
        }
    }

    @Override
    public void lock(boolean state) {
        modulesActivitiesController.lock(getChildren(), state);
    }

    @Override
    public void reset() {
        showCorrectAnswers(false);
        markAnswers(false);
        modulesActivitiesController.reset(getChildren());
    }

    void doMarkAnswers(boolean mark) {
        modulesActivitiesController.markAnswers(getChildren(), mark);
        markingAnswers = mark;
    }

    void doShowCorrectAnswers(boolean show) {
        modulesActivitiesController.showCorrectAnswers(getChildren(), show);
        showingAnswers = show;
    }

}
