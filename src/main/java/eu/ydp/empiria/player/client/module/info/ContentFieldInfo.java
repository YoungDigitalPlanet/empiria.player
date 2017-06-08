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

package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.gwtutil.client.StringUtils;

public class ContentFieldInfo {

    public enum FieldType {
        TEST("test"), ITEM("item"), UNKNOWN("unknown");

        private final String value;

        private FieldType(String value) {
            this.value = value;
        }

        public static FieldType getType(String value) {
            FieldType type = UNKNOWN;

            for (FieldType typeValue : values()) {
                if (value.startsWith(typeValue.value)) {
                    type = typeValue;
                    break;
                }
            }

            return type;
        }
    }

    private static final String TAG_FORMAT = "$[%1$s]";

    private static final String PATTERN_FORMAT = "\\$\\[%1$s]";

    private String tag;

    private String pattern;

    private String valueName;

    private FieldValueHandler handler;

    private FieldType type;

    public ContentFieldInfo setTagName(String tagName) {
        this.tag = format(TAG_FORMAT, tagName);
        this.pattern = format(PATTERN_FORMAT, tagName);
        this.type = FieldType.getType(tagName);
        this.valueName = createValueName(tagName);
        return this;
    }

    private String createValueName(String tagName) {
        String[] tagNameParts = tagName.split("\\.");
        return (tagNameParts.length == 2) ? tagNameParts[1].toUpperCase() : StringUtils.EMPTY_STRING;
    }

    public String getValueName() {
        return valueName;
    }

    public String getPattern() {
        return pattern;
    }

    public String getTag() {
        return tag;
    }

    public ContentFieldInfo setHandler(FieldValueHandler handler) {
        this.handler = handler;
        return this;
    }

    public String getValue(int refItemIndex) {
        String value = StringUtils.EMPTY_STRING;

        if (handler != null) {
            value = handler.getValue(this, refItemIndex);
        }

        return value;
    }

    public FieldType getType() {
        return type;
    }

    private String format(String pattern, String value) {
        Splitter splitter = Splitter.on("%1$s");
        Joiner joiner = Joiner.on(value);
        return joiner.join(splitter.split(pattern));
    }
}
