package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
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

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class InlineBodyGenerator implements InlineBodyGeneratorSocket {// NOPMD

	protected ModulesRegistrySocket modulesRegistrySocket;
	protected ModuleSocket moduleSocket;
	protected DisplayContentOptions options;
	private final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();

	public InlineBodyGenerator(ModulesRegistrySocket mrs, ModuleSocket moduleSocket, DisplayContentOptions options) {
		this.modulesRegistrySocket = mrs;
		this.options = options;
		this.moduleSocket = moduleSocket;
	}

	@Override
	public void generateInlineBody(Node mainNode, com.google.gwt.dom.client.Element parentElement) {
		if (mainNode != null && mainNode.hasChildNodes() && parentElement instanceof com.google.gwt.dom.client.Element) {
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
		Widget widget = null;
		if (allAsWidget) {
			widget = new FlowPanel();
			widget.setStyleName(styleNames.QP_TEXT_INLINE());
			if (nodeChildrens) {
				parseXML(mainNode.getChildNodes(), widget);
			} else {
				parseNode(mainNode, widget);
			}
		} else {
			widget = generateBody(mainNode, nodeChildrens);
		}
		return widget;
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
	 * Parsuje pojedynczy wezel xml-a i generuje reprezentacje w postaci
	 * widgeta. Widget jest dodawany do parentElement
	 *
	 * @param currNode
	 * @param parentElement
	 * @return
	 */
	protected com.google.gwt.dom.client.Element parseNode(Node currNode, com.google.gwt.dom.client.Element parentElement) {
		if (currNode.getNodeType() == Node.ELEMENT_NODE) {
			if (options.getIgnoredInlineTags().contains(currNode.getNodeName())) {
				return parentElement; // NOPMD
			} else if (modulesRegistrySocket.isModuleSupported(currNode.getNodeName()) && modulesRegistrySocket.isInlineModule(currNode.getNodeName())) {
				IModule module = modulesRegistrySocket.createModule(currNode.getNodeName());
				if (module instanceof IInlineModule) {
					((IInlineModule) module).initModule((Element) currNode, moduleSocket);
					Widget moduleView = ((IInlineModule) module).getView();
					if (moduleView != null) {
						parentElement.appendChild(moduleView.getElement());
					}
				}
			} else {
				com.google.gwt.dom.client.Element newElement = Document.get().createElement(currNode.getNodeName());
				parseXMLAttributes((Element) currNode, newElement);
				parentElement.appendChild(newElement);
				parseXML(currNode.getChildNodes(), newElement);
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

	protected void parseNode(Node node, Widget parent) {// NOPMD
		if (node == null) {
			return;
		}
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			String moduleName = node.getNodeName();
			if (modulesRegistrySocket.isModuleSupported(moduleName) && modulesRegistrySocket.isInlineModule(moduleName)) {
				IModule module = modulesRegistrySocket.createModule(moduleName);
				if (module instanceof IInlineModule) {
					((IInlineModule) module).initModule((Element) node, moduleSocket);
					Widget moduleView = ((IInlineModule) module).getView();
					if (parent instanceof ComplexPanel && moduleView != null) {
						((Panel) parent).add(moduleView);
					}
				}

			} else {
				HTMLPanel panel = new HTMLPanel(((Element) node).getNodeName(), "");// NOPMD
				if (parent instanceof ComplexPanel) {
					((Panel) parent).add(panel);
					parseXMLAttributes((Element) node, panel.getElement());
					parseNode(node, panel);
				}
			}
		} else if (node.getNodeType() == Node.TEXT_NODE) {
			parent.getElement().appendChild(Document.get().createTextNode(node.getNodeValue()));
		}

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
}
