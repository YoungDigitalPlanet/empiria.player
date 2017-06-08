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

package eu.ydp.empiria.player.client.module.model.media;

public enum MimeType {

    VIDEO_MPEG("video/mpeg"), VIDEO_MP4("video/mp4"), VIDEO_OGG("video/ogg"), VIDEO_WEBM("video/webm"), UNKNOWN("unknown");

    private final String value;

    private MimeType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static MimeType fromValue(String value) {
        for (MimeType type : MimeType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
