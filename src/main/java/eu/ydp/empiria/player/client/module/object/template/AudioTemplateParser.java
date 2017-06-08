/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
    protected MediaControllerFactory mediaControllerFactory;

    @Inject
    private TemplateControllers templateControllers;

    @Override
    public void beforeParse(Node mainNode, Widget parent) {

    }

    @Override
    protected boolean isModuleSupported(String moduleName) {
        return templateControllers.isControllerSupported(moduleName);
    }

    public void setMediaWrapper(MediaWrapper<?> mediaDescriptor) {
        this.mediaWrapper = mediaDescriptor;
    }

    @Override
    protected MediaController getMediaControllerNewInstance(String moduleName, Node node) {
        MediaController controller = mediaControllerFactory.get(ModuleTagName.getTag(moduleName));
        if (controller != null) {
            controller.setMediaDescriptor(mediaWrapper);
        }
        return controller;
    }
}
