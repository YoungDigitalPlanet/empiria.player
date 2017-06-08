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

package eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.shared.EventHandler;

public abstract class PointerEvent<H extends EventHandler> extends MouseEvent<H> {

    public boolean isTouchEvent() {
        return PointerNativeMethods.methods.pointerType(this.getNativeEvent()).equals(PointerEventsConstants.POINTER_TYPE_TOUCH);
    }

    public boolean isPrimary() {
        return PointerNativeMethods.methods.isPrimary(this.getNativeEvent());
    }

    public Integer getPointerId() {
        return PointerNativeMethods.methods.getPointerId(this.getNativeEvent());
    }
}
