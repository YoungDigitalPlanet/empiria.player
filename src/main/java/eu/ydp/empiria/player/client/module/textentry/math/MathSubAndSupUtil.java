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
