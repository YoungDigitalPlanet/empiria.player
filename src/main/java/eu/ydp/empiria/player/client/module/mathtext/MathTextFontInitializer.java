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

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import eu.ydp.empiria.player.client.module.core.base.IInlineModule;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

import java.util.Map;
import java.util.Set;

public class MathTextFontInitializer {
    @Inject
    private StyleSocket styleSocket;

    private DTOMathTextDefaultFontPropertiesProvider defaultFontPropertiesProvider;

    public Font initialize(IInlineModule module, ModuleSocket moduleSocket, Element element) {

        defaultFontPropertiesProvider = new DTOMathTextDefaultFontPropertiesProvider();
        DTOMathTextFontProperties fontProperties = defaultFontPropertiesProvider.createDefaultProprerties();
        updateFontPropertiesAccordingToStyles(styleSocket.getStyles(element), fontProperties);
        updateFontPropertiesAccordingToInlineFormatters(moduleSocket.getInlineFormattingTags(module), fontProperties);

        Integer fontColorInt = NumberUtils.tryParseInt(fontProperties.getColor().trim().substring(1), 16, 0);
        Color color = new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256);
        Font font = new Font(fontProperties.getSize(), fontProperties.getName(), fontProperties.isItalic(), fontProperties.isBold(), color);

        return font;
    }

    /**
     * @param styles - style name key / style value
     * @return
     */
    public void updateFontPropertiesAccordingToStyles(Map<String, String> styles, DTOMathTextFontProperties fontProperties) {
        if (styles.containsKey("-empiria-math-font-size")) {
            fontProperties.setSize(NumberUtils.tryParseInt(styles.get("-empiria-math-font-size")));
        }
        if (styles.containsKey("-empiria-math-font-family")) {
            fontProperties.setName(styles.get("-empiria-math-font-family"));
        }
        if (styles.containsKey("-empiria-math-font-weight")) {
            fontProperties.setBold(styles.get("-empiria-math-font-weight").equalsIgnoreCase("bold"));
        }
        if (styles.containsKey("-empiria-math-font-style")) {
            fontProperties.setItalic(styles.get("-empiria-math-font-style").equalsIgnoreCase("italic"));
        }
        if (styles.containsKey("-empiria-math-color")) {
            fontProperties.setColor(styles.get("-empiria-math-color").toUpperCase());
        }
    }

    private void updateFontPropertiesAccordingToInlineFormatters(Set<InlineFormattingContainerType> inlineTags, DTOMathTextFontProperties fontProperties) {
        for (InlineFormattingContainerType inlineFormattingContainerType : inlineTags) {
            switch (inlineFormattingContainerType) {
                case BOLD:
                    fontProperties.setBold(true);
                    break;
                case ITALIC:
                    fontProperties.setItalic(true);
                    break;
                default:
            }
        }
    }

}
