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

package eu.ydp.empiria.player.client.module.connection;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.conversion.StateToStateAndStructureConverter;
import eu.ydp.gwtutil.client.state.AbstractStateHelper;
import eu.ydp.gwtutil.client.state.converter.StateConverter;

import java.util.ArrayList;
import java.util.List;

public class InteractionModuleVersionConverter extends AbstractStateHelper {

    @Inject
    private StateToStateAndStructureConverter stateAndStructureConverter;

    @Override
    protected List<StateConverter> prepareStateConverters() {
        List<StateConverter> converters = new ArrayList<StateConverter>();
        converters.add(new StateConverter(stateAndStructureConverter));
        return converters;
    }

    @Override
    public int getTargetVersion() {
        return 1;
    }

}
