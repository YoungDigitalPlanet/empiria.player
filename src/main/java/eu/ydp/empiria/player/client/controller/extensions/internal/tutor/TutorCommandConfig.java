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

package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorCommandJs;
import eu.ydp.empiria.player.client.module.tutor.CommandType;

import static eu.ydp.empiria.player.client.module.tutor.CommandType.valueOf;

public class TutorCommandConfig {

    public static TutorCommandConfig fromJs(TutorCommandJs commandJs) {
        CommandType type = valueOf(commandJs.getType().toString());
        String asset = commandJs.getAsset();
        return new TutorCommandConfig(asset, type);
    }

    private final String asset;
    private final CommandType type;

    public TutorCommandConfig(String asset, CommandType type) {
        this.asset = asset;
        this.type = type;
    }

    public CommandType getType() {
        return type;
    }

    public String getAsset() {
        return asset;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((asset == null) ? 0 : asset.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TutorCommandConfig)) {
            return false;
        }
        TutorCommandConfig other = (TutorCommandConfig) obj;
        if (asset == null) {
            if (other.asset != null) {
                return false;
            }
        } else if (!asset.equals(other.asset)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

}
