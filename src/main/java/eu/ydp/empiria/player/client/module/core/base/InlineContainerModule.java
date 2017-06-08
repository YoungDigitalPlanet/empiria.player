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

package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;

import java.util.List;

/**
 * Gives possibility to use inline formatting for modules implementing IInlineModule
 */
public class InlineContainerModule extends InlineModuleBase implements IInlineContainerModule {

    private ElementWrapperWidget widget;
    private InlineFormattingContainerType type;

    @Override
    public Widget getView() {
        return widget;
    }

    @Override
    protected void initModule(Element element) {
        widget = new ElementWrapperWidget(Document.get().createElement(element.getNodeName()));
        type = parseNodeName(element);
    }

    private InlineFormattingContainerType parseNodeName(Element element) {
        String nodeName = element.getNodeName().toUpperCase();

        InlineFormattingContainerType type = null;

        if ("B".equals(nodeName)) {
            type = InlineFormattingContainerType.BOLD;
        } else if ("STRONG".equals(nodeName)) {
            type = InlineFormattingContainerType.BOLD;
        } else if ("I".equals(nodeName)) {
            type = InlineFormattingContainerType.ITALIC;
        }

        return type;
    }

    @Override
    public InlineFormattingContainerType getType() {
        return type;
    }

    @Override
    public List<IModule> getChildrenModules() {
        return getModuleSocket().getChildren(this);
    }

    @Override
    public List<HasParent> getNestedChildren() {
        return getModuleSocket().getNestedChildren(this);
    }

}
