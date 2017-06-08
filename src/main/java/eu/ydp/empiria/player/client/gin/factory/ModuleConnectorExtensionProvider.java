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

package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.NextPageButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.PageSwitchModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.PrevPageButtonModuleConnectorExtension;

public class ModuleConnectorExtensionProvider {

    @Inject
    private PrevPageButtonModuleConnectorExtension prevPageButtonModuleConnectorExtension;
    @Inject
    private NextPageButtonModuleConnectorExtension nextPageButtonModuleConnectorExtension;
    @Inject
    private PageSwitchModuleConnectorExtension pageSwitchModuleConnectorExtension;

    public PrevPageButtonModuleConnectorExtension getPrevPageButtonModuleConnectorExtension() {
        return prevPageButtonModuleConnectorExtension;
    }

    public NextPageButtonModuleConnectorExtension getNextPageButtonModuleConnectorExtension() {
        return nextPageButtonModuleConnectorExtension;
    }

    public PageSwitchModuleConnectorExtension getPageSwitchModuleConnectorExtension() {
        return pageSwitchModuleConnectorExtension;
    }
}
