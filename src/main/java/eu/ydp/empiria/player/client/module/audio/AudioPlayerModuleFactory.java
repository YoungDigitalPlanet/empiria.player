package eu.ydp.empiria.player.client.module.audio;

import com.google.gwt.media.client.Audio;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.audioplayer.AudioPlayerModule;
import eu.ydp.empiria.player.client.module.audioplayer.DefaultAudioPlayerModule;
import eu.ydp.empiria.player.client.module.audioplayer.FlashAudioPlayerModule;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

import java.util.Map;

public class AudioPlayerModuleFactory {
    @Inject
    private Provider<DefaultAudioPlayerModule> defaultAudioPlayerModuleProvider;
    @Inject
    private Provider<FlashAudioPlayerModule> flashAudioPlayerModuleProvider;
    @Inject
    private MediaChecker mediaChecker;

    public AudioPlayerModule getAudioPlayerModule(Map<String, String> sources){
        AudioPlayerModule player;
        if (doesPlayerNeedsFlash(sources)) {
            player = flashAudioPlayerModuleProvider.get();
        } else {
            player = defaultAudioPlayerModuleProvider.get();
        }
        return player;
    }
    private boolean doesPlayerNeedsFlash(Map<String, String> sources) {
        return ((!mediaChecker.isHtml5Mp3Supported() && !SourceUtil.containsOgg(sources)) || !Audio.isSupported()) && UserAgentChecker.isLocal();
    }
}
