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

package eu.ydp.empiria.player.client.module.binding.gapmaxlength;

import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingType;

import java.util.ArrayList;
import java.util.List;

public class GapMaxlengthBindingContext implements BindingContext {

    List<Bindable> bindables;
    private BindingType type;

    public GapMaxlengthBindingContext(BindingType type) {
        this.type = type;
        bindables = new ArrayList<Bindable>();
    }

    @Override
    public boolean add(Bindable bindable) {
        bindables.add(bindable);
        return true;
    }

    public GapMaxlengthBindingValue getGapMaxlengthBindingOutcomeValue() {
        GapMaxlengthBindingValue value = new GapMaxlengthBindingValue(0);
        for (Bindable b : bindables) {
            value.merge((GapMaxlengthBindingValue) b.getBindingValue(type));
        }
        return value;
    }

}
