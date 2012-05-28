package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;

public class InlineBodyGenerator implements InlineBodyGeneratorSocket {

	protected ModulesRegistrySocket modulesRegistrySocket;
	protected ModuleSocket moduleSocket;
	protected DisplayContentOptions options;

	public InlineBodyGenerator(ModulesRegistrySocket mrs, ModuleSocket ms, DisplayContentOptions options) {
		this.modulesRegistrySocket = mrs;
		this.options = options;
		this.moduleSocket = ms;
	}

	public void generateInlineBody(Node mainNode, com.google.gwt.dom.client.Element parentElement) {
		if (mainNode != null && mainNode.hasChildNodes() && parentElement instanceof com.google.gwt.dom.client.Element)
			parseXML(mainNode.getChildNodes(), parentElement);
	}

	public Widget generateInlineBody(Node mainNode) {
		DivElement de = Document.get().createDivElement();
		Document.get().getBody().appendChild(de);
		Widget h = InlineHTML.wrap(de);
		//after wrapping, div is removed from main body
		Document.get().getBody().removeChild(de);
		h.setStyleName("qp-text-inline");
		parseXML(mainNode.getChildNodes(), h.getElement());
		return h;
	}

	public Widget generateInlineBodyForNode(Node mainNode) {
		DivElement de = Document.get().createDivElement();
		Document.get().getBody().appendChild(de);
		Widget h = InlineHTML.wrap(de);
		//after wrapping, div is removed from main body
		Document.get().getBody().removeChild(de);
		h.setStyleName("qp-text-inline");
		parseNode(mainNode, h.getElement());
		return h;
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
				return parentElement;
			} else if (modulesRegistrySocket.isModuleSupported(currNode.getNodeName()) && modulesRegistrySocket.isInlineModule(currNode.getNodeName())) {
				IModule module = modulesRegistrySocket.createModule(currNode.getNodeName());
				if (module instanceof IInlineModule) {
					((IInlineModule) module).initModule((Element) currNode, moduleSocket);
					Widget moduleView = ((IInlineModule) module).getView();
					if (moduleView != null)
						parentElement.appendChild(moduleView.getElement());
				}
			} else {
				com.google.gwt.dom.client.Element newElement = Document.get().createElement(currNode.getNodeName());
				parseXMLAttributes((Element) currNode, newElement);
				parentElement.appendChild(newElement);
				parseXML(currNode.getChildNodes(), newElement);
			}
		} else if (currNode.getNodeType() == Node.TEXT_NODE) {
			Document doc = Document.get();
			com.google.gwt.dom.client.SpanElement e =  doc.createSpanElement();
			e.appendChild(doc.createTextNode(currNode.getNodeValue()));
			e.setClassName("qp-text");
			parentElement.appendChild(e);
		}
		return parentElement;
	}

	/**
	 * Generuje widgety dla dzieci wskazanego wezla
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
