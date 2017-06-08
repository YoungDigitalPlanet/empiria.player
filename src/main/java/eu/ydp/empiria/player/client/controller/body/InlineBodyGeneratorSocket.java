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

package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;

public interface InlineBodyGeneratorSocket {

    /**
     * generuje htmla dla dzieci wskazanego wezla i dolacza je do parentElement
     *
     * @param node
     * @param parentElement
     */
    public void generateInlineBody(Node node, Element parentElement);

    public void generateInlineBody(String node, Element parentElement);

    /**
     * generuje htmla dla dzieci wskazanego wezla
     *
     * @param mainNode
     * @return
     */
    public Widget generateInlineBody(Node mainNode);

    /**
     * Generuje htmla dla wskazanego wezla.
     *
     * @param mainNode
     * @return
     */
    public Widget generateInlineBodyForNode(Node mainNode);

    /**
     * generuje htmla dla dzieci wskazanego wezla w postaci hierarchi widgetow
     *
     * @param mainNode
     * @return
     */
    Widget generateInlineBodyForNode(Node mainNode, boolean allAsWidget);

    /**
     * Generuje htmla dla wskazanego wezla w postaci hierarchi widgetow
     *
     * @param mainNode
     * @return
     */
    Widget generateInlineBody(Node mainNode, boolean allAsWidget);

    Widget generateInlineBody(String mainNode, boolean allAsWidget);
}
