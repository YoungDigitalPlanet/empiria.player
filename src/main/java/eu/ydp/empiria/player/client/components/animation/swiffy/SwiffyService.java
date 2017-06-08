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

package eu.ydp.empiria.player.client.components.animation.swiffy;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.Map;

public class SwiffyService {

    @Inject
    private Provider<SwiffyObject> swiffyObjectProvider;

    private final Map<String, SwiffyObject> swiffyObjects = Maps.newHashMap();

    public SwiffyObject getSwiffyObject(String swifyName, String url) {
        SwiffyObject swiffyObject = swiffyObjectProvider.get();
        swiffyObject.setAnimationUrl(url);
        swiffyObjects.put(swifyName, swiffyObject);
        return swiffyObject;
    }

    public void clear(String swiffyName) {
        if (swiffyObjects.containsKey(swiffyName)) {
            swiffyObjects.remove(swiffyName).destroy();
        }
    }
}
