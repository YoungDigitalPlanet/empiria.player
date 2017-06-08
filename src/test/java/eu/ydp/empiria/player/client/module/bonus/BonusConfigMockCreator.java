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

package eu.ydp.empiria.player.client.module.bonus;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusAction;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResource;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType;
import eu.ydp.gwtutil.client.util.geom.Size;

import java.util.List;

import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusActionType.ON_PAGE_ALL_OK_FIRST_TIME;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BonusConfigMockCreator {

    public static BonusAction createMockAction(List<BonusResource> resources) {
        BonusAction action = mock(BonusAction.class);
        when(action.getType()).thenReturn(ON_PAGE_ALL_OK_FIRST_TIME);
        when(action.getBonuses()).thenReturn(resources);
        return action;
    }

    public static BonusResource createBonus(String url, Size size, BonusResourceType resourceType) {
        BonusResource resource = mock(BonusResource.class);
        when(resource.getAsset()).thenReturn(url);
        when(resource.getSize()).thenReturn(size);
        when(resource.getType()).thenReturn(resourceType);
        return resource;
    }
}
