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

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressAssetConfigJs;
import eu.ydp.gwtutil.client.util.geom.Size;

public class ProgressAssetConfig {

    private final Size size;
    private final String path;
    private final int count;

    public ProgressAssetConfig(String path, int count, Size size) {
        this.path = path;
        this.count = count;
        this.size = size;
    }

    public String getPath() {
        return this.path;
    }

    public int getCount() {
        return this.count;
    }

    public Size getSize() {
        return this.size;
    }

    public static ProgressAssetConfig fromJs(ProgressAssetConfigJs configJs) {
        Size size = new Size(configJs.getWidth(), configJs.getHeight());
        String path = configJs.getAsset();
        int count = configJs.getCount();
        return new ProgressAssetConfig(path, count, size);
    }
}
