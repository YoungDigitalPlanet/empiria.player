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

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

class SourcelistGroup {

    private Sourcelist sourcelist;
    private List<SourcelistClient> clients = Lists.newArrayList();
    private boolean locked;

    public void addClient(SourcelistClient client) {
        clients.add(client);
    }

    public List<SourcelistClient> getClients() {
        return clients;
    }

    public void setSourcelist(Sourcelist sourcelist) {
        this.sourcelist = sourcelist;
    }

    public Sourcelist getSourcelist() {
        return sourcelist;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public Optional<SourcelistClient> getClientById(String clientid) {
        Predicate<SourcelistClient> idPredicates = getClientIdPredicates(clientid);
        return Iterables.tryFind(clients, idPredicates);
    }

    private Predicate<SourcelistClient> getClientIdPredicates(final String clientId) {
        return new Predicate<SourcelistClient>() {

            @Override
            public boolean apply(SourcelistClient client) {
                return client.getIdentifier().equals(clientId);
            }
        };
    }
}
