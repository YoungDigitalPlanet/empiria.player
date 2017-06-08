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

package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import java.util.Map;

@Singleton
public class ProgressBonusService {

    private final Map<String, ProgressBonusConfig> cache = Maps.newHashMap();

    public void register(String id, ProgressBonusConfig config) {
        cache.put(id, config);
    }

    public ProgressBonusConfig getProgressBonusConfig(String id) {
        return cache.get(id);
    }
}
