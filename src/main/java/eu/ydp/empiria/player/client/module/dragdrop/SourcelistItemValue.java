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

package eu.ydp.empiria.player.client.module.dragdrop;

public class SourcelistItemValue {

    private final SourcelistItemType type;
    private final String value;
    private final String itemId;

    public SourcelistItemValue(SourcelistItemType type, String value, String itemId) {
        super();
        this.type = type;
        this.value = value;
        this.itemId = itemId;
    }

    public SourcelistItemType getType() {
        return type;
    }

    public String getContent() {
        return value;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        SourcelistItemValue other = (SourcelistItemValue) obj;
        if (itemId == null) {
            if (other.itemId != null) {
                return false;
            }
        } else if (!itemId.equals(other.itemId)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

}
