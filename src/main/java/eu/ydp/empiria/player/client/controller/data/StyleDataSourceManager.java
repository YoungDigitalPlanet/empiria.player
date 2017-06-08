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

package eu.ydp.empiria.player.client.controller.data;

import com.google.common.collect.Lists;
import com.google.gwt.xml.client.Element;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.style.StyleDocument;
import eu.ydp.empiria.player.client.style.StyleSocket;

import java.util.*;

/**
 * Requires that jscssp.js script is added to module xml file
 *
 * @see http://glazman.org/JSCSSP/
 */
@Singleton
public class StyleDataSourceManager implements StyleSocket {

    // style declarations for assessment
    private final List<StyleDocument> assessmentStyle;

    // style declarations for all items
    // TODO consider using WeakHashMap to avoid problems with vector size at
    // lines 50, 96
    private final Vector<List<StyleDocument>> itemStyle;

    private final ElementStyleSelectorBuilder elementStyleSelectorBuilder = new ElementStyleSelectorBuilder();

    /**
     * Style declarations that should be searched for styles. When player changes displayed page activeItemStyles should be rebuild.
     */
    private final List<StyleDocument> currentStyles;

    public StyleDataSourceManager() {
        assessmentStyle = new ArrayList<StyleDocument>();

        itemStyle = new Vector<List<StyleDocument>>();
        currentStyles = new ArrayList<StyleDocument>();
    }

    public void addAssessmentStyle(StyleDocument styleDocument) {
        assessmentStyle.add(styleDocument);
        currentStyles.add(styleDocument);
    }

    public void addItemStyle(int index, StyleDocument styleDocument) {
        if (index >= itemStyle.size()) {
            itemStyle.setSize(index + 1);
        }
        List<StyleDocument> styles = itemStyle.get(index);
        if (styles == null) {
            styles = new ArrayList<StyleDocument>();
            itemStyle.set(index, styles);
        }
        styles.add(styleDocument);
    }

    @Override
    public Map<String, String> getStyles(Element element) {
        return getStyleProperties(element, true);
    }

    @Override
    public Map<String, String> getOrgStyles(Element element) {
        return getStyleProperties(element, false);
    }

    @Override
    public Map<String, String> getStyles(String selector) {
        return getStylePropertiesForSelectors(Lists.newArrayList(selector), true);
    }

    public Map<String, String> getStyleProperties(Element element, boolean lowerCase) {
        List<String> selectors = elementStyleSelectorBuilder.getElementSelectors(element);
        return getStylePropertiesForSelectors(selectors, lowerCase);
    }

    private Map<String, String> getStylePropertiesForSelectors(List<String> selectors, boolean lowerCase) {
        Map<String, String> result = new HashMap<String, String>();

        for (StyleDocument sheet : currentStyles) {
            Map<String, String> currResult = sheet.getDeclarationsForSelectors(selectors);
            if (lowerCase) {
                for (Map.Entry<String, String> entry : currResult.entrySet()) {
                    result.put(entry.getKey().toLowerCase(), entry.getValue());
                }
            } else {
                result.putAll(currResult);
            }
        }
        return result;
    }

    @Override
    public void setCurrentPages(PageReference pageReference) {
        Vector<List<StyleDocument>> activeItemStyles = new Vector<List<StyleDocument>>(pageReference.pageIndices.length);
        for (int pageIndex : pageReference.pageIndices) {
            if (pageIndex < itemStyle.size()) {
                activeItemStyles.add(itemStyle.get(pageIndex));
            }
        }
        currentStyles.clear();

        for (StyleDocument sheet : assessmentStyle) {
            currentStyles.add(sheet);
        }
        for (List<StyleDocument> styles : activeItemStyles) {
            currentStyles.addAll(styles);
        }
    }

}
