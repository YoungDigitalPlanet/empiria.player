package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.parenthood.ParenthoodManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.IInlineContainerModule;
import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.inlinechoice.InlineChoiceStyleNameConstants;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;

public class InlineBodyGenerator implements InlineBodyGeneratorSocket {// NOPMD

    private ModulesRegistrySocket modulesRegistrySocket;
    private ModuleSocket moduleSocket;
    private DisplayContentOptions options;
    private InlineChoiceStyleNameConstants styleNames;
    private InteractionEventsListener interactionEventsListener;
    private ParenthoodManager parenthood;

    @Inject
    public InlineBodyGenerator(@Assisted ModulesRegistrySocket mrs, @Assisted ModuleSocket moduleSocket, @Assisted DisplayContentOptions options,
                               @Assisted InteractionEventsListener interactionEventsListener, @Assisted ParenthoodManager parenthood, InlineChoiceStyleNameConstants styleNames) {
        this.modulesRegistrySocket = mrs;
        this.options = options;
        this.moduleSocket = moduleSocket;
        this.interactionEventsListener = interactionEventsListener;
        this.parenthood = parenthood;
        this.styleNames = styleNames;
    }

    @Override
    public void generateInlineBody(Node mainNode, com.google.gwt.dom.client.Element parentElement) {
        if (mainNode != null && mainNode.hasChildNodes() && parentElement != null) {
            parseXML(mainNode.getChildNodes(), parentElement);
        }
    }

    @Override
    public Widget generateInlineBody(Node mainNode, boolean allAsWidget) {
        return generateInlineBody(mainNode, allAsWidget, true);
    }

    @Override
    public Widget generateInlineBodyForNode(Node mainNode, boolean allAsWidget) {
        return generateInlineBody(mainNode, allAsWidget, false);
    }

    private Widget generateInlineBody(Node mainNode, boolean allAsWidget, boolean nodeChildrens) {
        if (allAsWidget) {
            Widget widget = new FlowPanel();
            widget.setStyleName(styleNames.QP_TEXT_INLINE());
            if (nodeChildrens) {
                parseXML(mainNode.getChildNodes(), widget);
            } else {
                parseNode(mainNode, widget);
            }
            return widget;
        } else {
            return generateBody(mainNode, nodeChildrens);
        }
    }

    @Override
    public Widget generateInlineBody(Node mainNode) {
        return generateInlineBody(mainNode, false);
    }

    @Override
    public Widget generateInlineBodyForNode(Node mainNode) {
        return generateInlineBodyForNode(mainNode, false);
    }

    private Widget generateBody(Node mainNode, boolean nodeChildrens) {
        DivElement div = Document.get().createDivElement();
        Document.get().getBody().appendChild(div);
        Widget inlineHtml = InlineHTML.wrap(div);
        // after wrapping, div is removed from main body
        Document.get().getBody().removeChild(div);
        inlineHtml.setStyleName(styleNames.QP_TEXT_INLINE());
        if (nodeChildrens) {
            parseXML(mainNode.getChildNodes(), inlineHtml.getElement());
        } else {
            parseNode(mainNode, inlineHtml.getElement());
        }
        return inlineHtml;
    }

    /**
     * Parsuje pojedynczy wezel xml-a i generuje reprezentacje w postaci widgeta. Widget jest dodawany do parentElement
     *
     * @param currNode
     * @param parentElement
     * @return
     */
    protected com.google.gwt.dom.client.Element parseNode(Node currNode, com.google.gwt.dom.client.Element parentElement) {
        if (currNode.getNodeType() == Node.ELEMENT_NODE) {
            if (options.getIgnoredInlineTags().contains(currNode.getNodeName())) {
                return parentElement;
            } else if (modulesRegistrySocket.isModuleSupported(currNode.getNodeName()) && modulesRegistrySocket.isInlineModule(currNode.getNodeName())) {
                IModule module = modulesRegistrySocket.createModule((Element) currNode);
                if (module instanceof IInlineModule) {
                    parenthood.addChild(module);
                    ((IInlineModule) module).initModule((Element) currNode, moduleSocket, interactionEventsListener);
                    Widget moduleView = ((IInlineModule) module).getView();
                    if (moduleView != null) {
                        parentElement.appendChild(moduleView.getElement());
                    }
                    if (module instanceof IInlineContainerModule) {
                        parenthood.pushParent((IInlineContainerModule) module);
                        parseElementNode(currNode, parentElement);
                        parenthood.popParent();
                    }
                }
            } else {
                parseElementNode(currNode, parentElement);
            }
        } else if (currNode.getNodeType() == Node.TEXT_NODE) {
            Document doc = Document.get();
            com.google.gwt.dom.client.SpanElement span = doc.createSpanElement();
            span.appendChild(doc.createTextNode(currNode.getNodeValue()));
            span.setClassName(styleNames.QP_TEXT());
            parentElement.appendChild(span);
        }
        return parentElement;
    }

    private void parseElementNode(Node currNode, com.google.gwt.dom.client.Element parentElement) {
        com.google.gwt.dom.client.Element newElement = Document.get().createElement(currNode.getNodeName());
        parseXMLAttributes((Element) currNode, newElement);
        parentElement.appendChild(newElement);
        parseXML(currNode.getChildNodes(), newElement);
    }

    protected void parseNode(Node node, Widget parent) {
        if (node == null) {
            return;
        }
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String moduleName = node.getNodeName();
            if (modulesRegistrySocket.isModuleSupported(moduleName) && modulesRegistrySocket.isInlineModule(moduleName)) {
                IModule module = modulesRegistrySocket.createModule((Element) node);
                if (module instanceof IInlineModule) {
                    ((IInlineModule) module).initModule((Element) node, moduleSocket, interactionEventsListener);
                    Widget moduleView = ((IInlineModule) module).getView();
                    if (parent instanceof ComplexPanel && moduleView != null) {
                        Panel parentPanel = ((Panel) parent);
                        parentPanel.add(moduleView);

                        if (module instanceof IInlineContainerModule) {
                            IInlineContainerModule inlineContainer = (IInlineContainerModule) module;
                            createInlineFormattedModule(node, parentPanel, inlineContainer);
                        }
                    }
                }

            } else {
                HTMLPanel panel = new HTMLPanel(node.getNodeName(), "");
                if (parent instanceof ComplexPanel) {
                    ((Panel) parent).add(panel);
                    parseXMLAttributes((Element) node, panel.getElement());
                    parseXML(node.getChildNodes(), panel);
                }
            }
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            parent.getElement().appendChild(Document.get().createTextNode(node.getNodeValue()));
        }

    }

    private void createInlineFormattedModule(Node node, Panel parentPanel, IInlineContainerModule inlineContainer) {
        String htmlTag = inlineContainer.getType().getHtmlTag();
        Panel inlineFormattedPanel = new FlowPanel(htmlTag);
        parentPanel.add(inlineFormattedPanel);
        parseXML(node.getChildNodes(), inlineFormattedPanel);
    }

    /**
     * Generuje elementy dla dzieci wskazanego wezla
     *
     * @param nodes
     * @param parentElement
     * @return
     */
    protected com.google.gwt.dom.client.Element parseXML(NodeList nodes, com.google.gwt.dom.client.Element parentElement) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node currNode = nodes.item(i);
            parseNode(currNode, parentElement);
        }
        return parentElement;
    }

    /**
     * Generuje widgety dla dzieci wskazanego wezla
     *
     * @param nodes
     * @param parentElement
     * @return
     */
    protected Widget parseXML(NodeList nodes, Widget parentElement) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node currNode = nodes.item(i);
            parseNode(currNode, parentElement);
        }
        return parentElement;
    }

    private void parseXMLAttributes(com.google.gwt.xml.client.Element srcElement, com.google.gwt.dom.client.Element dstElement) {
        NamedNodeMap attributes = srcElement.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            if (attribute.getNodeValue().length() > 0) {
                if (attribute.getNodeName().equals("class")) {
                    dstElement.addClassName(attribute.getNodeValue());
                } else {
                    dstElement.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
                }
            }

        }
    }

    @Override
    public void generateInlineBody(String node, com.google.gwt.dom.client.Element parentElement) {

        generateInlineBody(getElementFromString(node), parentElement);
    }

    @Override
    public Widget generateInlineBody(String mainNode, boolean allAsWidget) {
        return generateInlineBody(getElementFromString(mainNode), allAsWidget);
    }

    private Element getElementFromString(String value) {
        com.google.gwt.xml.client.Document doc = XMLParser.createDocument();
        com.google.gwt.xml.client.Element textElement = doc.createElement("content");

        textElement.appendChild(doc.createTextNode(value));

        return textElement;
    }
}
