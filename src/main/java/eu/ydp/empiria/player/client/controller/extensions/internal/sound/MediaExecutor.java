package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaExecutor<T extends Widget> {
    MediaWrapper<T> getMediaWrapper();

    void setMediaWrapper(MediaWrapper<T> descriptor);

    void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration);

    BaseMediaConfiguration getBaseMediaConfiguration();

    void init();

    @Deprecated
    void play(String src);

    void play();

    void playLooped();

    void stop();

    void pause();

    void resume();

    void setMuted(boolean mute);

    void setVolume(double volume);

    void setCurrentTime(double time);

    void setSoundFinishedListener(SoundExecutorListener listener);
}
