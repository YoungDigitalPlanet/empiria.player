package eu.ydp.empiria.player.client.module.object.template;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.ModuleTagName;

import java.util.Set;

@Singleton
public class TemplateControllers {

    private Set<String> controllers = Sets.newHashSet();

    @Inject
    public TemplateControllers() {
        if (controllers.isEmpty()) {
            controllers.add(ModuleTagName.MEDIA_PLAY_PAUSE_BUTTON.tagName());
            controllers.add(ModuleTagName.MEDIA_PLAY_STOP_BUTTON.tagName());
            controllers.add(ModuleTagName.MEDIA_STOP_BUTTON.tagName());
            controllers.add(ModuleTagName.MEDIA_MUTE_BUTTON.tagName());
            controllers.add(ModuleTagName.MEDIA_PROGRESS_BAR.tagName());
            controllers.add(ModuleTagName.MEDIA_POSITION_IN_STREAM.tagName());
            controllers.add(ModuleTagName.MEDIA_VOLUME_BAR.tagName());
            controllers.add(ModuleTagName.MEDIA_CURRENT_TIME.tagName());
            controllers.add(ModuleTagName.MEDIA_TOTAL_TIME.tagName());
            controllers.add(ModuleTagName.MEDIA_TEXT_TRACK.tagName());
            controllers.add(ModuleTagName.MEDIA_SCREEN.tagName());
        }
    }

    public boolean isControllerSupported(Object object){
        return controllers.contains(object);
    }
}
