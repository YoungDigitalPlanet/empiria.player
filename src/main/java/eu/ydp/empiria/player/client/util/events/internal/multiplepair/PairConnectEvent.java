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

package eu.ydp.empiria.player.client.util.events.internal.multiplepair;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class PairConnectEvent extends AbstractEvent<PairConnectEventHandler, PairConnectEventTypes> {
    public static EventTypes<PairConnectEventHandler, PairConnectEventTypes> types = new EventTypes<PairConnectEventHandler, PairConnectEventTypes>();
    private String sourceItem;
    private String targetItem;
    private boolean userAction = true;

    public PairConnectEvent(PairConnectEventTypes type, String source, String target, boolean userAction) {
        super(type, null);
        this.sourceItem = source;
        this.targetItem = target;
        this.userAction = userAction;
    }

    public PairConnectEvent(PairConnectEventTypes type) {
        super(type, null);
    }

    public String getSourceItem() {
        return sourceItem;
    }

    public String getTargetItem() {
        return targetItem;
    }

    public String getItemsPair() {
        return getSourceItem() + " " + getTargetItem();
    }

    @Override
    protected EventTypes<PairConnectEventHandler, PairConnectEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(PairConnectEventHandler handler) {
        handler.onConnectionEvent(this);
    }

    public static EventType<PairConnectEventHandler, PairConnectEventTypes> getType(PairConnectEventTypes type) {
        return types.getType(type);
    }

    public boolean isUserAction() {
        return userAction;
    }

    @Override
    public String toString() {
        return "PairConnectEvent " + getType() + " [sourceItem=" + sourceItem + ", targetItem=" + targetItem + ", userAction=" + userAction + "]";
    }

}
