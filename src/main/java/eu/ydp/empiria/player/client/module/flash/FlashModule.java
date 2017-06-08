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

package eu.ydp.empiria.player.client.module.flash;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;

public class FlashModule extends SimpleModuleBase {

    public HTMLPanel panel;

    public FlashModule() {
        panel = new HTMLPanel("object", "");
    }

    @Override
    protected void initModule(Element element) {
        String src = element.getAttribute("src");
        int lastSlash = ((src.lastIndexOf("/") > src.lastIndexOf("\\")) ? src.lastIndexOf("/") : src.lastIndexOf("\\"));
        String basePath = src.substring(0, lastSlash + 1);
        com.google.gwt.dom.client.Element paramNameElement = Document.get().createElement("param");
        paramNameElement.setAttribute("name", "movie");
        paramNameElement.setAttribute("value", src);
        com.google.gwt.dom.client.Element paramBaseElement = Document.get().createElement("param");
        paramBaseElement.setAttribute("name", "base");
        paramBaseElement.setAttribute("value", basePath);
        com.google.gwt.dom.client.Element embedElement = Document.get().createElement("embed");
        embedElement.setAttribute("src", src);
        embedElement.setAttribute("base", basePath);
        panel.getElement().appendChild(paramNameElement);
        panel.getElement().appendChild(paramBaseElement);
        panel.getElement().appendChild(embedElement);

    }

    @Override
    public Widget getView() {
        return panel;
    }
}
