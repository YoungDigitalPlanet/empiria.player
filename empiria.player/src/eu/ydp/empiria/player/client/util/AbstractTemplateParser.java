package eu.ydp.empiria.player.client.util;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.module.media.button.MediaController;

public abstract class AbstractTemplateParser {
	private final static String MODULE_ATTRIBUTE = "module";

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

	public void parse(Node mainNode, Widget parent) {
		if (mainNode == null) {
			return;
		}
		NodeList nodes = mainNode.getChildNodes();
		for (int x = 0; x < nodes.getLength(); ++x) {
			Node node = nodes.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String moduleName = node.getNodeName();
				if (!moduleName.trim().isEmpty() && isModuleSupported(moduleName)) {
					MediaController<?> mediaController = getMediaControllerNewInstance(moduleName,node);
					mediaController.init();
					parseXMLAttributes((Element) node, mediaController.getElement());
					if (parent instanceof ComplexPanel) {
						((Panel) parent).add(mediaController);
						parse(node, (Widget) mediaController);
					}
				} else {
					HTMLPanel panel = new HTMLPanel(((Element) node).getNodeName(), ""); //NOPMD
					if (parent instanceof ComplexPanel) {
						((Panel) parent).add(panel);
						parseXMLAttributes((Element) node, panel.getElement());
						parse(node, panel);
					}
				}
			} else if (node.getNodeType() == Node.TEXT_NODE) {
				parent.getElement().appendChild(Document.get().createTextNode(node.getNodeValue()));
			}
		}

	}

	/**
	 * Metoda zwraca nowy controler
	 *
	 * @param moduleName
	 * @return
	 */
	protected abstract MediaController<?> getMediaControllerNewInstance(String moduleName,Node node);

	/**
	 * Sprawdza czy dany modu jest obslugiwany
	 *
	 * @param moduleName
	 * @return true je¿eli modu jest obsugiwany false w przeciwnym razie
	 */
	protected abstract boolean isModuleSupported(String moduleName);
}
