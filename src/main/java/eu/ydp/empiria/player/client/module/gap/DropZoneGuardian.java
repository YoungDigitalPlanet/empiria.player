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

package eu.ydp.empiria.player.client.module.gap;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public class DropZoneGuardian {

    private final DroppableObject<? extends Widget> droppable;
    private final Widget moduleWidget;
    private final StyleNameConstants styleNames;

    @Inject
    public DropZoneGuardian(@Assisted DroppableObject<?> droppable, @Assisted Widget moduleWidget, StyleNameConstants styleNameConstants) {
        this.droppable = droppable;
        this.moduleWidget = moduleWidget;
        this.styleNames = styleNameConstants;
    }

    public void lockDropZone() {
        droppable.setDisableDrop(true);
        moduleWidget.addStyleName(styleNames.QP_DROP_ZONE_LOCKED());
    }

    public void unlockDropZone() {
        droppable.setDisableDrop(false);
        moduleWidget.removeStyleName(styleNames.QP_DROP_ZONE_LOCKED());
    }
}
