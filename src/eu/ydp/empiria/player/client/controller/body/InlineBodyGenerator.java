package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.IInlineContainerModule;
import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class InlineBodyGenerator implements InlineBodyGeneratorSocket {// NOPMD

	private InteractionEventsListener interactionEventsListener;
	private ModuleSocket moduleSocket;
	private ModulesRegistrySocket modulesRegistrySocket;
	private DisplayContentOptions options;
	private ParenthoodManager parenthood;
	private StyleNameConstants styleNames;

	public InlineBodyGenerator(ModulesRegistrySocket mrs, ModuleSocket moduleSocket, DisplayContentOptions options,
			InteractionEventsListener interactionEventsListener, ParenthoodManager parenthood) {
		this.modulesRegistrySocket = mrs;
		this.options = options;
		this.moduleSocket = moduleSocket;
		this.interactionEventsListener = interactionEventsListener;
		this.parenthood = parenthood;
		this.styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();
	}

	@Override
	public void generateInlineBody(String node, com.google.gwt.dom.client.Element parentElement) {
		generateInlineBody(getElementFromString(node), parentElement);
	}
	
	@Override
	public void generateInlineBody(Node mainNode, com.google.gwt.dom.client.Element parentElement) {
		if (mainNode != null && mainNode.hasChildNodes()) {
			parseXMLAndAppendToElement(mainNode.getChildNodes(), parentElement);
		}
	}

	@Override
	public Widget generateInlineWidget(Node mainNode) {
		Widget inlineHtml = prepareWidget();
		
		parseNodesAndAppendToWidget(mainNode.getChildNodes(), inlineHtml);
		
		return inlineHtml;
	}

	private Widget prepareWidget() {
		DivElement emptyDiv = Document.get().createDivElement();
		Document.get().getBody().appendChild(emptyDiv);

		Widget inlineHtml = InlineHTML.wrap(emptyDiv);

		// after wrapping, div is removed from main body
		Document.get().getBody().removeChild(emptyDiv);
		inlineHtml.setStyleName(styleNames.QP_TEXT_INLINE());

		return inlineHtml;
	}

	@Override
	public Widget generateInlinePanelWidget(Node mainNode) {
		Widget widget = new FlowPanel();
		widget.setStyleName(styleNames.QP_TEXT_INLINE());
		parseNodesAndAppendToWidget(mainNode.getChildNodes(), widget);

		return widget;
	}

	private Element getElementFromString(String value) {
		com.google.gwt.xml.client.Document doc = XMLParser.createDocument();
		com.google.gwt.xml.client.Element textElement = doc.createElement("content");

		textElement.appendChild(doc.createTextNode(value));

		return textElement;
	}

	private void parseElementNode(Node currNode, com.google.gwt.dom.client.Element parentElement) {
		com.google.gwt.dom.client.Element newElement = Document.get().createElement(currNode.getNodeName());
		
		copyXMLAttributes((Element) currNode, newElement);
		
		parentElement.appendChild(newElement);
		
		parseXMLAndAppendToElement(currNode.getChildNodes(), newElement);
	}

	private void parseNodeAndAppendToElement(Node currNode, com.google.gwt.dom.client.Element parentElement) {
		if (currNode.getNodeType() == Node.ELEMENT_NODE) {
			if (options.getIgnoredInlineTags().contains(currNode.getNodeName())) {
				return;
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
	}

	private void parseNodeAndAppendToWidget(Node node, Widget parent) {// NOPMD
		if (node == null) {
			return;
		}

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			String moduleName = node.getNodeName();

			if (modulesRegistrySocket.isModuleSupported(moduleName) && modulesRegistrySocket.isInlineModule(moduleName)) {
				IModule module = modulesRegistrySocket.createModule(element);
				if (module instanceof IInlineModule) {
					((IInlineModule) module).initModule(element , moduleSocket, interactionEventsListener);
					Widget moduleView = ((IInlineModule) module).getView();
					
					if (parent instanceof ComplexPanel && moduleView != null) {
						((Panel) parent).add(moduleView);
					}
				}

			} else {
				HTMLPanel panel = new HTMLPanel(node.getNodeName(), "");// NOPMD
				if (parent instanceof ComplexPanel) {
					((Panel) parent).add(panel);
					copyXMLAttributes(element, panel.getElement());
					parseNodesAndAppendToWidget(node.getChildNodes(), panel);
				}
			}

		} else if (node.getNodeType() == Node.TEXT_NODE) {
			Text textNode = Document.get().createTextNode(node.getNodeValue());
			parent.getElement().appendChild(textNode);
		}

	}

	private void parseXMLAndAppendToElement(NodeList nodes, com.google.gwt.dom.client.Element parentElement) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node currNode = nodes.item(i);
			parseNodeAndAppendToElement(currNode, parentElement);
		}
	}

	private void parseNodesAndAppendToWidget(NodeList nodes, Widget parentElement) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node currNode = nodes.item(i);
			parseNodeAndAppendToWidget(currNode, parentElement);
		}
	}

	private void copyXMLAttributes(com.google.gwt.xml.client.Element srcElement, com.google.gwt.dom.client.Element dstElement) {
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
}
