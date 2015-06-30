package eu.ydp.empiria.player.client.module.media.html5;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;

/**
 * The delegator was applied because of the potential possibility of the change of the value while a program is run. Unfortunately it is necessary to protect
 * against it.
 */
public class HTML5MediaExecutorDelegator {

    private AbstractHTML5MediaExecutor executor;

    public AbstractHTML5MediaExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(AbstractHTML5MediaExecutor executor) {
        this.executor = executor;
    }
}
