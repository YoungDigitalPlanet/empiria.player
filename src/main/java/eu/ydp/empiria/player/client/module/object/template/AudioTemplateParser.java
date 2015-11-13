package eu.ydp.empiria.player.client.module.object.template;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.util.AbstractTemplateParser;

public class AudioTemplateParser extends AbstractTemplateParser {

    private MediaWrapper<?> mediaWrapper;

    @Inject
    protected MediaControllerFactory factory;

    @Inject
    private Controllers controllers;

    @Override
    public void beforeParse(Node mainNode, Widget parent) {

    }

    @Override
    protected boolean isModuleSupported(String moduleName) {
        return controllers.contains(moduleName);
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
