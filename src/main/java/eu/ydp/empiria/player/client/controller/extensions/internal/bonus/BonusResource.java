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

package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusResourceJs;
import eu.ydp.gwtutil.client.util.geom.Size;

public class BonusResource {

    private final String asset;
    private final BonusResourceType type;
    private final Size size;

    public BonusResource(String asset, BonusResourceType type, Size size) {
        this.asset = asset;
        this.type = type;
        this.size = size;
    }

    public String getAsset() {
        return this.asset;
    }

    public BonusResourceType getType() {
        return this.type;
    }

    public Size getSize() {
        return this.size;
    }

    public static BonusResource fromJs(BonusResourceJs jsBonus) {
        String asset = jsBonus.getAsset();
        String stringType = jsBonus.getType();
        BonusResourceType type = BonusResourceType.valueOf(stringType);
        Size size = new Size(jsBonus.getWidth(), jsBonus.getHeight());

        return new BonusResource(asset, type, size);
    }
}
