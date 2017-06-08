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

package eu.ydp.empiria.player.client.util.events.internal.dragdrop;

import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class DragDropEvent extends AbstractEvent<DragDropEventHandler, DragDropEventTypes> {
    public static EventTypes<DragDropEventHandler, DragDropEventTypes> types = new EventTypes<DragDropEventHandler, DragDropEventTypes>();

    DragDataObject dragDataObject;

    private IModule module;

    public DragDropEvent(DragDropEventTypes type, Object source) {
        super(type, source);
    }

    @Override
    protected EventTypes<DragDropEventHandler, DragDropEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(DragDropEventHandler handler) {
        handler.onDragEvent(this);
    }

    public void setIModule(IModule module) {
        this.module = module;
    }

    public IModule getIModule() {
        return module;
    }

    public void setDragDataObject(DragDataObject dragDataObject) {
        this.dragDataObject = dragDataObject;
    }

    public DragDataObject getDragDataObject() {
        return dragDataObject;
    }

    public static EventType<DragDropEventHandler, DragDropEventTypes> getType(DragDropEventTypes type) {
        return types.getType(type);
    }

    public static EventType<DragDropEventHandler, DragDropEventTypes>[] getTypes(DragDropEventTypes... type) {
        return types.getTypes(type);
    }

}
