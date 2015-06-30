package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.executor.ExternalMediaExecutor;

public class ExternalMediaProxy implements MediaProxy {

    private ExternalMediaWrapper externalMediaWrapper;
    private ExternalMediaExecutor externalMediaExecutor;

    @Inject
    public ExternalMediaProxy(ExternalMediaWrapper externalMediaWrapper, ExternalMediaExecutor externalMediaExecutor) {
        this.externalMediaWrapper = externalMediaWrapper;
        this.externalMediaExecutor = externalMediaExecutor;
        this.externalMediaExecutor.setMediaWrapper(this.externalMediaWrapper);
    }

    @Override
    public ExternalMediaWrapper getMediaWrapper() {
        return externalMediaWrapper;
    }

    @Override
    public ExternalMediaExecutor getMediaExecutor() {
        return externalMediaExecutor;
    }

    public void setDuration(double durationSeconds) {
        externalMediaWrapper.setDuration(durationSeconds);
    }

    public void setCurrentTime(double currentTimeSeconds) {
        externalMediaWrapper.setCurrentTime(currentTimeSeconds);
    }

}
