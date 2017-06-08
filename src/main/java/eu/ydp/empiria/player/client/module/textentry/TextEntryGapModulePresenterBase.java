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

package eu.ydp.empiria.player.client.module.textentry;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.module.expression.ExpressionReplacer;
import eu.ydp.empiria.player.client.module.expression.TextBoxExpressionReplacer;
import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.module.gap.GapDropHandler;
import eu.ydp.empiria.player.client.module.gap.GapModulePesenterBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public abstract class TextEntryGapModulePresenterBase extends GapModulePesenterBase {

    @Inject
    private DragDataObjectFromEventExtractor dataObjectFromEventExtractor;
    @Inject
    private TextBoxExpressionReplacer expressionReplacer;
    @Inject
    protected StyleNameConstants styleNames;
    @Inject
    protected TextEntryStyleNameConstants textEntryStyleNameConstants;
    @Inject
    protected DragDropHelper dragDropHelper;

    protected DroppableObject<TextBox> droppable;
    protected TextBox textBox;
    protected DropZoneGuardian dropZoneGuardian;

    public abstract void addPresenterHandler(PresenterHandler handler);

    public void addDomHandlerOnObjectDrop(final GapDropHandler dragGapDropHandler) {
        droppable.addDropHandler(new DropHandler() {
            @Override
            public void onDrop(DropEvent event) {
                Optional<DragDataObject> objectFromEvent = dataObjectFromEventExtractor.extractDroppedObjectFromEvent(event);
                if (objectFromEvent.isPresent()) {
                    dragGapDropHandler.onDrop(objectFromEvent.get());
                }
            }
        });
    }

    @Override
    public void setMaxLength(int length) {
        textBox.setMaxLength(length);
    }

    public void removeFocusFromTextField() {
        textBox.getElement().blur();
    }

    public void makeExpressionReplacements(ExpressionReplacer replacer) {
        expressionReplacer.makeReplacements(textBox, replacer);
    }

    @Override
    public void setText(String text) {
        textBox.setValue(text, true);
    }

    public String getText() {
        return textBox.getText();
    }

    @Override
    public void setViewEnabled(boolean enabled) {
        textBox.setEnabled(enabled);
    }

    @Override
    public UIObject getComponent() {
        return textBox;
    }

    public void lockDragZone() {
        dropZoneGuardian.lockDropZone();
    }

    public void unlockDragZone() {
        dropZoneGuardian.unlockDropZone();
    }
}
