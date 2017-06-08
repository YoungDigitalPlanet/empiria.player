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

package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class PlayerContainersAccessor implements IPlayerContainersAccessor {

    private Map<Integer, HasWidgets> itemContainers = new HashMap<Integer, HasWidgets>();
    private HasWidgets playerContainer;

    @Override
    public void registerItemBodyContainer(int itemIndex, HasWidgets container) {
        this.itemContainers.put(itemIndex, container);
    }

    @Override
    public HasWidgets getItemBodyContainer(int itemIndex) {
        return itemContainers.get(itemIndex);
    }

    @Override
    public void setPlayerContainer(HasWidgets playerContainer) {
        this.playerContainer = playerContainer;
    }

    @Override
    public HasWidgets getPlayerContainer() {
        return playerContainer;
    }

}
