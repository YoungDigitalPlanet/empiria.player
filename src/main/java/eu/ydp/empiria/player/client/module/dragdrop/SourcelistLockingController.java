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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

import java.util.Collection;

public class SourcelistLockingController {

    @Inject
    @PageScoped
    private SourcelistManagerModel model;

    public void lockOthers(Sourcelist sourcelist) {
        for (Sourcelist src : model.getSourceLists()) {
            if (!sourcelist.equals(src)) {
                src.lockSourceList();
                lockClients(src);
            }
        }
    }

    private void lockClients(Sourcelist src) {
        Collection<SourcelistClient> clients = model.getClients(src);
        for (SourcelistClient client : clients) {
            client.lockDropZone();
        }
    }

    public void unlockAll() {
        for (Sourcelist sourcelist : model.getSourceLists()) {
            unlockGroupIfNotBlocked(sourcelist);
        }
    }

    private void unlockGroupIfNotBlocked(Sourcelist sourcelist) {
        if (!model.isGroupLocked(sourcelist)) {
            sourcelist.unlockSourceList();
            unlockClients(sourcelist);
        }
    }

    public void unlockClients(Sourcelist sourcelist) {
        Collection<SourcelistClient> clients = model.getClients(sourcelist);
        for (SourcelistClient client : clients) {
            client.unlockDropZone();
        }
    }

    public void lockGroup(String clientId) {
        if (model.containsClient(clientId)) {
            Sourcelist sourcelist = model.getSourcelistByClientId(clientId);
            lockGroup(sourcelist);
            model.lockGroup(sourcelist);
        }
    }

    private void lockGroup(Sourcelist sourcelist) {
        sourcelist.lockSourceList();
        lockClients(sourcelist);
    }

    public void unlockGroup(String clientId) {
        if (model.containsClient(clientId)) {
            Sourcelist sourcelist = model.getSourcelistByClientId(clientId);
            unlockGroup(sourcelist);
            model.unlockGroup(sourcelist);
        }
    }

    private void unlockGroup(Sourcelist sourcelist) {
        sourcelist.unlockSourceList();
        unlockClients(sourcelist);
    }
}
