package eu.ydp.empiria.player.client.module.tutor;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import javax.inject.Inject;

public class SingleRunTutorEndHandler implements TutorEndHandler {
    private Optional<EndHandler> endHandler = Optional.absent();

    @Inject
    @ModuleScoped
    private ActionExecutorService executorService;

    @Override
    public void onEnd() {
        fireTheEndHandlerIfPresent();
    }

    @Override
    public void onEndWithDefaultAction() {
        executorService.execute(ActionType.DEFAULT, this);
        fireTheEndHandlerIfPresent();
    }

    private void fireTheEndHandlerIfPresent() {
        if (endHandler.isPresent()) {
            EndHandler endHandlerInstance = endHandler.get();
            endHandler = Optional.absent();
            endHandlerInstance.onEnd();
        }
    }

    @Override
    public void setEndHandler(EndHandler endHandler) {
        this.endHandler = Optional.fromNullable(endHandler);
    }
}
