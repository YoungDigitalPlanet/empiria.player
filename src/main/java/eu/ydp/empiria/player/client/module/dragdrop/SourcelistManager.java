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

package eu.ydp.empiria.player.client.module.dragdrop;

public interface SourcelistManager {

    void registerModule(SourcelistClient module);

    void registerSourcelist(Sourcelist sourcelist);

    void dragStart(String sourceModuleId);

    void dragEnd(String itemId, String sourceModuleId, String targetModuleId);

    void dragEndSourcelist(String itemId, String sourceModuleId);

    void dragFinished();

    SourcelistItemValue getValue(String itemId, String targetModuleId);

    void onUserValueChanged();

    void lockGroup(String clientId);

    void unlockGroup(String clientId);
}
