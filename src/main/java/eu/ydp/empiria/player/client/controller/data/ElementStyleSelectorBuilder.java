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

import com.google.common.base.Strings;
import com.google.gwt.xml.client.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementStyleSelectorBuilder {
    public List<String> getElementSelectors(Element element) {
        String name = element.getNodeName().toLowerCase();
        String[] classes = null;
        String id = null; // NOPMD
        if (!Strings.isNullOrEmpty(element.getAttribute("class"))) {
            classes = element.getAttribute("class").split(" ");
        }
        if (!Strings.isNullOrEmpty(element.getAttribute("id"))) {
            id = element.getAttribute("id");
        }

        return buildSelectors(name, classes, id);
    }

    protected List<String> buildSelectors(String name, String[] classes, String id) {// NOPMD
        List<String> selectors = new ArrayList<String>();

        selectors.add(name);
        if (classes != null) {
            for (int i = 0; i < classes.length; i++) {
                selectors.add("." + classes[i]);
            }
            for (int i = 0; i < classes.length; i++) {
                selectors.add(name + "." + classes[i]);
            }
        }
        if (id != null) {
            selectors.add("#" + id);
            selectors.add(name + "#" + id);
        }

        return selectors;
    }
}
