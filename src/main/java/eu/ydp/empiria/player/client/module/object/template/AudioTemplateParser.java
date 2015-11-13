package eu.ydp.empiria.player.client.module.object.template;

import com.google.common.collect.Sets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.util.AbstractTemplateParser;

import java.util.Set;

public class AudioTemplateParser extends AbstractTemplateParser {

    private static final Set<String> CONTROLLERS = Sets.newHashSet();
    private MediaWrapper<?> mediaWrapper;

    @Inject
    protected MediaControllerFactory factory;

    public AudioTemplateParser() {
        if (CONTROLLERS.isEmpty()) {
            CONTROLLERS.add(ModuleTagName.MEDIA_PLAY_PAUSE_BUTTON.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_PLAY_STOP_BUTTON.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_STOP_BUTTON.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_MUTE_BUTTON.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_PROGRESS_BAR.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_POSITION_IN_STREAM.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_VOLUME_BAR.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_CURRENT_TIME.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_TOTAL_TIME.tagName());
        }
    }

    @Override
    public void beforeParse(Node mainNode, Widget parent) {

    }

    @Override
    public void parse(Node mainNode, Widget parent) {
        super.parse(mainNode, parent);
    }

    @Override
    protected boolean isModuleSupported(String moduleName) {
        return CONTROLLERS.contains(moduleName);
    }

    public void setMediaWrapper(MediaWrapper<?> mediaDescriptor) {
        this.mediaWrapper = mediaDescriptor;
    }

    @Override
    protected MediaController getMediaControllerNewInstance(String moduleName, Node node) {
        MediaController controller = factory.get(ModuleTagName.getTag(moduleName));
        if (controller != null) {
            controller.setMediaDescriptor(mediaWrapper);
        }
        return controller;
    }
}
