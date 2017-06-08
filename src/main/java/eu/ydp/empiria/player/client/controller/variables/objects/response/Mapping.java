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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import java.util.HashMap;

public class Mapping {

    public Float lowerBound;

    public Float upperBound;

    public Float defaultValue = new Float(0);

    public HashMap<String, Float> mapEntries;

    public Mapping(Node mappingNode) {

        mapEntries = new HashMap<String, Float>();

        if (mappingNode == null)
            return;

        try {

            Node node;

            node = mappingNode.getAttributes().getNamedItem("lowerBound");
            if (node != null)
                lowerBound = new Float(node.getNodeValue());

            node = mappingNode.getAttributes().getNamedItem("upperBound");
            if (node != null)
                upperBound = new Float(node.getNodeValue());

            node = mappingNode.getAttributes().getNamedItem("defaultValue");
            if (node != null)
                defaultValue = new Float(node.getNodeValue());

            NodeList entries = ((Element) mappingNode).getElementsByTagName("mapEntry");

            for (int i = 0; i < entries.getLength(); i++)
                mapEntries.put(entries.item(i).getAttributes().getNamedItem("mapKey").getNodeValue(),
                        new Float(entries.item(i).getAttributes().getNamedItem("mappedValue").getNodeValue()));

        } catch (Exception e) {
        }

    }

}
