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

package eu.ydp.empiria.player.client.style;

public enum JsCssRuleType {
    UNKNOWN_RULE(0), STYLE_RULE(1), CHARSET_RULE(2), IMPORT_RULE(3), MEDIA_RULE(4), FONT_FACE_RULE(5), PAGE_RULE(6), VARIABLES_RULE(7), NAMESPACE_RULE(100), COMMENT(
            101), WHITE_SPACE(102), STYLE_DECLARATION(1000);

    public final int type;

    JsCssRuleType(int type) {
        this.type = type;
    }

}
