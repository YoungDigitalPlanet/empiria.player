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

package eu.ydp.empiria.player.client.module.connection.presenter;

import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEventHandler;

public class ConnectionEventHandler extends AbstractEventHandler<PairConnectEventHandler, PairConnectEventTypes, PairConnectEvent> {

    protected void fireConnectEvent(PairConnectEventTypes type, String source, String target, boolean userAction) {
        PairConnectEvent event = new PairConnectEvent(type, source, target, userAction);
        fireEvent(event);
    }

    @Override
    protected void dispatchEvent(PairConnectEventHandler handler, PairConnectEvent event) {
        handler.onConnectionEvent(event);
    }

    public void addPairConnectEventHandler(PairConnectEventHandler handler) {
        for (PairConnectEventTypes type : PairConnectEventTypes.values()) {
            addHandler(handler, PairConnectEvent.getType(type));
        }
    }

}
