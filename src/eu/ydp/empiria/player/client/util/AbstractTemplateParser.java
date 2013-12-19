package eu.ydp.empiria.player.client.util;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.module.media.button.MediaController;

public abstract class AbstractTemplateParser {
	private final static String MODULE_ATTRIBUTE = "module";
	private final Set<String> templateModules = new HashSet<String>();

	private void parseXMLAttributes(com.google.gwt.xml.client.Element srcElement, com.google.gwt.dom.client.Element dstElement) {
		NamedNodeMap attributes = srcElement.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			if (attribute.getNodeValue().length() > 0) {
				if (attribute.getNodeName().equals("class")) {
					dstElement.addClassName(attribute.getNodeValue());
				} else if (!MODULE_ATTRIBUTE.equals(attribute.getNodeName())) {
					dstElement.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
				}
			}
		}
	}

	private void collectModules(Node mainNode, boolean complexPanel) {
		if (mainNode == null) {
			return;
		}
		NodeList nodes = mainNode.getChildNodes();
		for (int x = 0; x < nodes.getLength(); ++x) {
			Node node = nodes.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String moduleName = node.getNodeName();
				if (!moduleName.trim().isEmpty() && isModuleSupported(moduleName)) {
					templateModules.add(moduleName);
					collectModules(node, complexPanel);
				} else {
					collectModules(node, complexPanel);
				}
			}
		}
	}

	/**
	 * Zwraca true jezeli modul wystepuje w szablonie i parser go obsluguje
	 * false w przeciwnym razie
	 *
	 * @param moduleName
	 * @return
	 */
	public boolean isModuleInTemplate(String moduleName) {
		return templateModules.contains(moduleName);
	}

	private Panel getPanel(String nodeName) {
		Panel panel = null;
		if ("div".equals(nodeName)) {
			panel = new FlowPanel();
		} else {
			panel = new HTMLPanel(nodeName, "");
		}
		return panel;
	}

	private void parseNode(Node mainNode, Widget parent) {
		if (mainNode == null) {
			return;
		}
		NodeList nodes = mainNode.getChildNodes();
		for (int x = 0; x < nodes.getLength(); ++x) {
			Node node = nodes.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String moduleName = node.getNodeName();
				if (!moduleName.trim().isEmpty() && isModuleSupported(moduleName)) {
					MediaController<?> mediaController = getMediaControllerNewInstance(moduleName, node);
					mediaController.init();
					parseXMLAttributes((Element) node, mediaController.asWidget().getElement());
					if (parent instanceof HasWidgets) {
						((HasWidgets) parent).add(mediaController.asWidget());
						if (mediaController instanceof Widget) {
							parseNode(node, (Widget) mediaController);
						}
					}
				} else if (parent instanceof ComplexPanel) {
					Panel panel = getPanel(((Element) node).getNodeName());
					((Panel) parent).add(panel);
					parseXMLAttributes((Element) node, panel.getElement());
					parseNode(node, panel);
				}

			} else if (node.getNodeType() == Node.TEXT_NODE) {
				parent.getElement().appendChild(Document.get().createTextNode(node.getNodeValue()));
			}
		}
	}

	public void parse(Node mainNode, Widget parent) {
		templateModules.clear();
		collectModules(mainNode, parent instanceof ComplexPanel);
		beforeParse(mainNode, parent);
		parseNode(mainNode, parent);
	}

	/**
	 * Metoda jest wywolywana zanim rozpocznie sie parsowanie Elementu przyczym
	 * wstepna analiza szablonu zostala juz wykonana i dane na temat modulow w
	 * szablonie sa juz dostepne.
	 *
	 * @param mainNode
	 * @param parent
	 */
	public abstract void beforeParse(Node mainNode, Widget parent);

	/**
	 * Metoda zwraca nowy controler
	 *
	 * @param moduleName
	 * @return
	 */
	protected abstract MediaController<?> getMediaControllerNewInstance(String moduleName, Node node);

	/**
	 * Sprawdza czy dany modu jest obslugiwany
	 *
	 * @param moduleName
	 * @return true jeżeli modu jest obsugiwany false w przeciwnym razie
	 */
	protected abstract boolean isModuleSupported(String moduleName);
}
