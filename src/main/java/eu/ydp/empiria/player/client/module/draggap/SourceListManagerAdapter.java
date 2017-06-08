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

package eu.ydp.empiria.player.client.module.draggap;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;

public class SourceListManagerAdapter {

    private final SourcelistManager sourcelistManager;
    private String moduleId;

    @Inject
    public SourceListManagerAdapter(@PageScoped SourcelistManager sourcelistManager) {
        this.sourcelistManager = sourcelistManager;
    }

    public void initialize(String moduleId) {
        this.moduleId = moduleId;
    }

    public SourcelistItemValue getItemById(String itemId) {
        return sourcelistManager.getValue(itemId, moduleId);
    }

    public void dragEnd(String itemID, String sourceModuleId) {
        sourcelistManager.dragEnd(itemID, sourceModuleId, moduleId);
    }

    public void dragFinished() {
        sourcelistManager.dragFinished();
    }

    public void dragStart() {
        sourcelistManager.dragStart(moduleId);
    }

    public void registerModule(SourcelistClient sourcelistClient) {
        sourcelistManager.registerModule(sourcelistClient);
    }

    public void onUserValueChanged() {
        sourcelistManager.onUserValueChanged();
    }

    public void lockGroup() {
        sourcelistManager.lockGroup(moduleId);
    }

    public void unlockGroup() {
        sourcelistManager.unlockGroup(moduleId);
    }
}
