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

package eu.ydp.empiria.player.client.module.containers;

import eu.ydp.empiria.player.client.module.binding.BindingManager;
import eu.ydp.empiria.player.client.module.binding.BindingProxy;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingManager;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingManager;

public abstract class BindingContainerModule extends SimpleContainerModuleBase implements BindingProxy {

    private GapWidthBindingManager gapWidthBindingManager;
    private GapMaxlengthBindingManager gapMaxlengthBindingManager;

    @Override
    public BindingManager getBindingManager(BindingType bindingType) {
        if (bindingType == BindingType.GAP_WIDTHS) {
            if (gapWidthBindingManager == null)
                gapWidthBindingManager = createGapWidthBindingManager();
            return gapWidthBindingManager;
        }

        if (bindingType == BindingType.GAP_MAXLENGHTS) {
            if (gapMaxlengthBindingManager == null)
                gapMaxlengthBindingManager = createGapMaxlengthBindingManager();
            return gapMaxlengthBindingManager;
        }

        return null;
    }

    protected GapWidthBindingManager createGapWidthBindingManager() {
        return new GapWidthBindingManager(true);
    }

    protected GapMaxlengthBindingManager createGapMaxlengthBindingManager() {
        return new GapMaxlengthBindingManager(true);
    }
}
