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

package eu.ydp.empiria.player.client.module.binding;

import java.util.HashMap;
import java.util.Map;

public abstract class BindingManagerBase implements BindingManager {

    private Map<BindingGroupIdentifier, BindingContext> contexts;
    private boolean acceptOnlyEmptyGroupIdentifier;

    public BindingManagerBase(boolean acceptOnlyEmptyGroupIdentifier) {
        contexts = new HashMap<BindingGroupIdentifier, BindingContext>();
        this.acceptOnlyEmptyGroupIdentifier = acceptOnlyEmptyGroupIdentifier;
    }

    @Override
    public BindingContext getBindingContext(BindingGroupIdentifier groupIdentifier) {
        if (groupIdentifier == null)
            return null;
        for (BindingGroupIdentifier gi : contexts.keySet()) {
            if (gi.equals(groupIdentifier)) {
                return contexts.get(gi);
            }
        }
        if (!groupIdentifier.isEmptyGroupIdentifier() && acceptOnlyEmptyGroupIdentifier) {
            return null;
        }
        BindingContext newContext = createNewBindingContext();
        contexts.put(groupIdentifier, newContext);
        return newContext;
    }

    protected abstract BindingContext createNewBindingContext();

}
