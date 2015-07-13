package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.feedback.player.SingleFeedbackSoundPlayer;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface SingleFeedbackSoundPlayerFactory {
    public SingleFeedbackSoundPlayer getSingleFeedbackSoundPlayer(MediaWrapper<?> mediaWrapper);
}
