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

package eu.ydp.empiria.player.client.module.img.events.coordinates;

import com.google.gwt.event.shared.EventHandler;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerEvent;
import eu.ydp.empiria.player.client.util.position.Point;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class PointerEventsCoordinates {

    private final Map<Integer, Point> pointersPoints = new LinkedHashMap<>();

    public void addEvent(PointerEvent<? extends EventHandler> pointerEvent) {
        if (isFirstTouch(pointerEvent)) {
            pointersPoints.clear();
        }
        Point coordinates = new Point(pointerEvent.getClientX(), pointerEvent.getClientY());
        pointersPoints.put(pointerEvent.getPointerId(), coordinates);
    }

    public Point getPoint(int index) {
        return valuesAsList().get(index);
    }

    public void removeEvent(PointerEvent<?> pointerEvent) {
        pointersPoints.remove(pointerEvent.getPointerId());
    }

    public int getLength() {
        return pointersPoints.size();
    }

    public TouchOnImageEvent getTouchOnImageEvent() {
        return new TouchOnImageEvent(valuesAsList());
    }

    public boolean isOnePointer() {
        return pointersPoints.size() == 1;
    }

    public boolean isEmpty() {
        return pointersPoints.isEmpty();
    }

    private boolean isFirstTouch(PointerEvent<? extends EventHandler> pointerEvent) {
        return pointerEvent.isPrimary() && !pointersPoints.containsKey(pointerEvent.getPointerId());
    }

    private List<Point> valuesAsList() {
        return new ArrayList<>(pointersPoints.values());
    }
}
