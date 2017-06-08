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

package eu.ydp.empiria.player.client.view.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {
    private final Map<K, V> cache = new HashMap<K, V>();

    /**
     * Sprawdza czy w cechu znajduje sie strona o danym indeksie
     *
     * @param index
     * @return
     */
    public boolean isPresent(K index) {
        return cache.get(index) != null;
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    /**
     * Zwraca pageview dla konkretnego indeksu jezeli nie istnieje w cechu zostanie utworzony
     *
     * @param index
     * @return
     */
    public V getOrCreateAndPut(K index) {
        V contentView = cache.get(index);
        if (contentView == null) {
            contentView = getElement(index);
            cache.put(index, contentView);
        }
        return contentView;
    }

    public void put(K index, V object) {
        cache.put(index, object);
    }

    protected abstract V getElement(K index);

    public Map<K, V> getCache() {
        return Collections.unmodifiableMap(cache);
    }
}
