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
            controllers.add(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName());
            controllers.add(ModuleTagName.MEDIA_POSITION_IN_STREAM.tagName());
            controllers.add(ModuleTagName.MEDIA_VOLUME_BAR.tagName());
            controllers.add(ModuleTagName.MEDIA_CURRENT_TIME.tagName());
            controllers.add(ModuleTagName.MEDIA_TOTAL_TIME.tagName());
            controllers.add(ModuleTagName.MEDIA_TEXT_TRACK.tagName());
            controllers.add(ModuleTagName.MEDIA_SCREEN.tagName());
        }
    }

    public boolean isControllerSupported(String templateName) {
        return controllers.contains(templateName);
    }
}
