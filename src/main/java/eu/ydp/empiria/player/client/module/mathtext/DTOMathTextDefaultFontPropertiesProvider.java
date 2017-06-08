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

package eu.ydp.empiria.player.client.module.mathtext;

public class DTOMathTextDefaultFontPropertiesProvider {

    public static final int SIZE = 16;
    public static final String NAME = "Arial";
    public static final boolean BOLD = false;
    public static final boolean ITALIC = false;
    public static final String COLOR = "#000000";

    public DTOMathTextFontProperties createDefaultProprerties() {
        DTOMathTextFontProperties defaultProperties = new DTOMathTextFontProperties();
        defaultProperties.setBold(BOLD);
        defaultProperties.setName(NAME);
        defaultProperties.setColor(COLOR);
        defaultProperties.setItalic(ITALIC);
        defaultProperties.setSize(SIZE);

        return defaultProperties;
    }
}
