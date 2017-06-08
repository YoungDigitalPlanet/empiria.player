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

package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.ui.drop.FlowPanelWithDropZone;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public interface DragGapView extends IsWidget {

    void removeContent();

    void lock(boolean lock);

    void setDragDisabled(boolean disabled);

    void updateStyle(UserAnswerType answerType);

    void setDragStartHandler(DragStartHandler dragGapStartDragHandler);

    void setDragEndHandler(DragEndHandler dragEndHandler);

    DroppableObject<FlowPanelWithDropZone> enableDropCapabilities();

    void setHeight(int height);

    void setWidth(int width);

    void setItemContent(SourcelistItemValue item, InlineBodyGeneratorSocket inlineBodyGeneratorSocket);
}
