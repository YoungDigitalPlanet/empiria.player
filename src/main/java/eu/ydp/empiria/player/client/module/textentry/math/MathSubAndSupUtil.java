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

package eu.ydp.empiria.player.client.module.textentry.math;

import com.google.common.collect.Lists;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.List;

public class MathSubAndSupUtil {

    public boolean isSubOrSup(Element node, Node parentNode) {
        Node prevNode = node;
        boolean subsupParentFound = false;

        if (node.hasAttribute(EmpiriaTagConstants.ATTR_UID)) {
            while (parentNode != null && !isMathInteractionNode(parentNode)) {
                if (isSubOrSupElement(parentNode) && !XMLUtils.getFirstChildElement((Element) parentNode).equals(prevNode)) {
                    subsupParentFound = true;
                    break;
                }
                prevNode = parentNode;
                parentNode = parentNode.getParentNode();
            }
        }

        return subsupParentFound;
    }

    private boolean isMathInteractionNode(Node node) {
        return node.getNodeName().equals(ModuleTagName.MATH_INTERACTION.tagName());
    }

    private boolean isSubOrSupElement(Node node) {
        List<String> nodesNames = Lists.newArrayList("msub", "msup", "msubsup", "mmultiscripts");
        boolean isSubOrSup = nodesNames.contains(node.getNodeName());
        return isSubOrSup;
    }
}
