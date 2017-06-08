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

package eu.ydp.empiria.player.client.module.tutor.actions;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.Set;

public class OutcomeDrivenActionTypeProvider {

    @Inject
    @ModuleScoped
    private OnPageAllOkAction pageAllOk;
    @Inject
    @ModuleScoped
    private OnOkAction onOk;
    @Inject
    @ModuleScoped
    private OnWrongAction onWrong;

    public Set<OutcomeDrivenAction> getActions() {
        return ImmutableSet.of(pageAllOk, onOk, onWrong);
    }
}
