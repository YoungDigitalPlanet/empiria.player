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

package eu.ydp.empiria.player.client.controller.events.interaction;

import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;

import java.util.HashMap;
import java.util.Map;

public class StateChangedInteractionEvent extends InteractionEvent {

    protected boolean userInteract;
    private boolean isReset;
    protected IUniqueModule sender;

    public StateChangedInteractionEvent(boolean userInteract, boolean isReset, IUniqueModule sender) {
        this.userInteract = userInteract;
        this.sender = sender;
        this.isReset = isReset;
    }

    public boolean isUserInteract() {
        return userInteract;
    }

    public boolean isReset() {
        return isReset;
    }

    public IUniqueModule getSender() {
        return sender;
    }

    @Override
    public InteractionEventType getType() {
        return InteractionEventType.STATE_CHANGED;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("userInteract", Boolean.valueOf(userInteract));
        p.put("sender", sender);
        return p;
    }
}
