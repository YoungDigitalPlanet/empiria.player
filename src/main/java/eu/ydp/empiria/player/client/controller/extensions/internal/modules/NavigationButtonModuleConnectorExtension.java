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

package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.core.creator.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.module.button.NavigationButtonModule;

import java.util.Map;

import static eu.ydp.empiria.player.client.module.ModuleTagName.NEXT_ITEM_NAVIGATION;
import static eu.ydp.empiria.player.client.module.ModuleTagName.PREV_ITEM_NAVIGATION;

public abstract class NavigationButtonModuleConnectorExtension extends ControlModuleConnectorExtension {

    private static final Map<String, NavigationButtonDirection> NODE2DIRECTION = ImmutableMap
            .<String, NavigationButtonDirection>builder()
            .put(NEXT_ITEM_NAVIGATION.tagName(), NavigationButtonDirection.NEXT)
            .put(PREV_ITEM_NAVIGATION.tagName(), NavigationButtonDirection.PREVIOUS)
            .build();

    @Inject
    private ModuleFactory moduleFactory;

    @Override
    public ModuleCreator getModuleCreator() {
        return new AbstractModuleCreator() {
            @Override
            public IModule createModule() {
                NavigationButtonDirection direction = getDirection(getModuleNodeName());
                NavigationButtonModule button = moduleFactory.createNavigationButtonModule(direction);
                initializeModule(button);
                return button;
            }
        };
    }

    private NavigationButtonDirection getDirection(String name) {
        return NODE2DIRECTION.get(name);
    }

}
