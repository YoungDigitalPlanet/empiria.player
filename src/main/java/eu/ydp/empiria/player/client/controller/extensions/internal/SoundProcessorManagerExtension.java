package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.extensions.types.MediaProcessorExtension;

@Singleton
public class SoundProcessorManagerExtension extends InternalExtension {

    private MediaProcessorExtension processor;

    public void setSoundProcessorExtension(MediaProcessorExtension processor) {
        this.processor = processor;
    }

    @Override
    public void init() {
        if (processor != null) {
            processor.initMediaProcessor();
        }
    }
}
