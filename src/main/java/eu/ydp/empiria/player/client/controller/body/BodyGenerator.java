package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;

import java.util.List;

public class BodyGenerator implements BodyGeneratorSocket {

    private static final String SECTION = "section";
    private List<String> ignoredTagNames;
    private ModulesInstalatorSocket moduleInstalatorSocket;

    public BodyGenerator(ModulesInstalatorSocket moduleCreatorSocket, DisplayContentOptions options) {
        this.moduleInstalatorSocket = moduleCreatorSocket;
        this.ignoredTagNames = options.getIgnoredTags();
    }

    @Override
    public void generateBody(Node upNode, HasWidgets parent) {
        NodeList nodes = upNode.getChildNodes();
        for (int n = 0; n < nodes.getLength(); n++) {
            Node node = nodes.item(n);
            processNode(node, parent);
        }
    }

    @Override
    public void processNode(Node node, HasWidgets parent) {
        if (ignoredTagNames != null && SECTION.equals(node.getNodeName())
                && ignoredTagNames.contains(((com.google.gwt.xml.client.Element) node).getAttribute("name"))) {

        } else if (ignoredTagNames != null && SECTION.equals(node.getNodeName()) && ignoredTagNames.contains("untagged") && !detectTagNode(node)) {

        } else if (SECTION.equals(node.getNodeName())) {
            generateBody(node, parent);
        } else if (node.getNodeType() == Node.COMMENT_NODE) {

        } else if (moduleInstalatorSocket != null && moduleInstalatorSocket.isModuleSupported(node.getNodeName())) {

            if (moduleInstalatorSocket.isMultiViewModule(node.getNodeName())) {
                moduleInstalatorSocket.registerModuleView((Element) node, parent);
            } else {
                moduleInstalatorSocket.createSingleViewModule((Element) node, parent, this);
            }
        }

    }

    protected boolean detectTagNode(Node node) {
        if (node.getNodeType() == Node.TEXT_NODE) {
            return false;
        }
        com.google.gwt.xml.client.Element e = (com.google.gwt.xml.client.Element) node;
        return e.getElementsByTagName(SECTION).getLength() > 0;
    }

}
