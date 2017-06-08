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

package eu.ydp.empiria.player.client.util.events.internal.page;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class PageEvent extends AbstractEvent<PageEventHandler, PageEventTypes> {

    public static EventTypes<PageEventHandler, PageEventTypes> types = new EventTypes<PageEventHandler, PageEventTypes>();
    private final Object value;

    public PageEvent(PageEventTypes type, Object value) {
        this(type, value, null);
    }

    public PageEvent(PageEventTypes type, Object value, Object source) {
        super(type, source);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    protected EventTypes<PageEventHandler, PageEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(PageEventHandler handler) {
        handler.onPageEvent(this);
    }

    public static EventType<PageEventHandler, PageEventTypes> getType(PageEventTypes type) {
        return types.getType(type);
    }

    public static EventType<PageEventHandler, PageEventTypes>[] getTypes(PageEventTypes... typeList) {
        return typeList.length > 0 ? types.getTypes(typeList) : new EventType[0];
    }

}
