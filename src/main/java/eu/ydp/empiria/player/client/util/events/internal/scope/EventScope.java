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

package eu.ydp.empiria.player.client.util.events.internal.scope;

/**
 * Zakres widocznosci eventu
 */
public interface EventScope<T> extends Comparable<T> {
    /**
     * do uproszczenia porownywania budujemy hierarchie zakresow przoprzez kolejne definiowanie enumow. Scope.ordinal == 0 okresla element najwyzszego poziomu
     * dalsze elementy to wezszy zakres.<br/>
     * <b>UWAGA zmiany w kolejnosci maja wplyw na dzialanie {@link PlayerEventsBusOld} </b>
     */
    public enum Scope {
        LESSON, PAGE, GROUP
    }

    public Scope getScope();

}
