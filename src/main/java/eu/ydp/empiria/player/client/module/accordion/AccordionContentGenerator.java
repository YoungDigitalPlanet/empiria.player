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

package eu.ydp.empiria.player.client.module.accordion;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

public class AccordionContentGenerator {

    private final InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    private final BodyGeneratorSocket bodyGeneratorSocket;

    public AccordionContentGenerator(InlineBodyGeneratorSocket inlineBodyGeneratorSocket, BodyGeneratorSocket bodyGeneratorSocket) {
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
        this.bodyGeneratorSocket = bodyGeneratorSocket;
    }

    public void generateBody(Node xmlNode, HasWidgets parent) {
        bodyGeneratorSocket.generateBody(xmlNode, parent);
    }

    public Widget generateInlineBody(Node xmlNode) {
        return inlineBodyGeneratorSocket.generateInlineBody(xmlNode);
    }
}
