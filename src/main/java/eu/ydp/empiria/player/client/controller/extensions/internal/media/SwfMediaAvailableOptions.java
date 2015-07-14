package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;

/**
 * dostepne opcje dla odtwarzacza swf
 */
public class SwfMediaAvailableOptions implements MediaAvailableOptions {

    @Override
    public boolean isPlaySupported() {
        return true;
    }

    @Override
    public boolean isPauseSupported() {
        return true;
    }

    @Override
    public boolean isMuteSupported() {
        return true;
    }

    @Override
    public boolean isVolumeChangeSupported() {
        return true;
    }

    @Override
    public boolean isStopSupported() {
        return true;
    }

    @Override
    public boolean isSeekSupported() {
        return true;
    }

    @Override
    public boolean isFullScreenSupported() {
        return false;
    }

    @Override
    public boolean isMediaMetaAvailable() {
        return true;
    }

    @Override
    public boolean isTemplateSupported() {
        return true;
    }

}
