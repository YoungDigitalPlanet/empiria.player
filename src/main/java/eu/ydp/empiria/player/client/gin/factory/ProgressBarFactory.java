package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.media.progress.MediaProgressBarImpl;
import eu.ydp.empiria.player.client.module.media.progress.ProgressBarEndEventHandler;
import eu.ydp.empiria.player.client.module.media.progress.ProgressBarUpdateEventHandler;

public interface ProgressBarFactory {

    ProgressBarEndEventHandler createProgressBarEndEventHandler(MediaProgressBarImpl progressBar);

    ProgressBarUpdateEventHandler createProgressBarUpdateEventHandler(MediaProgressBarImpl progressBar);
}
