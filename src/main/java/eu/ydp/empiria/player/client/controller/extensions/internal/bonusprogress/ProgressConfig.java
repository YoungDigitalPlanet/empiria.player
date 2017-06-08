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

import com.google.common.collect.Lists;
import com.google.gwt.core.client.JsArray;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressAssetConfigJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressConfigJs;

import java.util.List;

public class ProgressConfig {

    private final int from;
    private final List<ProgressAssetConfig> assets;

    public ProgressConfig(int from, List<ProgressAssetConfig> assets) {
        this.from = from;
        this.assets = assets;
    }

    public int getFrom() {
        return this.from;
    }

    public List<ProgressAssetConfig> getAssets() {
        return this.assets;
    }

    public static ProgressConfig fromJs(ProgressConfigJs jsAction) {
        int from = jsAction.getFrom();
        List<ProgressAssetConfig> assets = getAssets(jsAction.getAssets());
        return new ProgressConfig(from, assets);
    }

    private static List<ProgressAssetConfig> getAssets(JsArray<ProgressAssetConfigJs> progresses) {
        List<ProgressAssetConfig> assets = Lists.newArrayList();

        for (int i = 0; i < progresses.length(); i++) {
            ProgressAssetConfigJs configJs = progresses.get(i);
            ProgressAssetConfig asset = ProgressAssetConfig.fromJs(configJs);
            assets.add(asset);
        }
        return assets;
    }

}
