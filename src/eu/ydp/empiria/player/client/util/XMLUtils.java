/*
  The MIT License

  Copyright (c) 2009 Krzysztof Langner

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */
package eu.ydp.empiria.player.client.util;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

public final class XMLUtils {

	private XMLUtils() {
	}

	/**
	 * Helper function for getting element attribute as string
	 *
	 * @param name
	 *            Attribute name
	 * @return attribute text or empty string if not found
	 */
	public static String getAttributeAsString(Element element, String name) {
		String attribute = element.getAttribute(name);
		return attribute == null ? "" : attribute;
	}

	/**
	 * Helper function for getting element attribute as boolean
	 *
	 * @param name
	 *            Attribute name
	 * @return attribute value or false if not found
	 */
	public static boolean getAttributeAsBoolean(Element element, String name) {
		String attribute = element.getAttribute(name);
		return "true".equals(attribute);
	}

	/**
	 * Helper function for getting element attribute as int
	 *
	 * @param name
	 *            Attribute name
	 * @return attribute value or 0 if not found
	 */
	public static int getAttributeAsInt(Element element, String name) {
		String attribute = element.getAttribute(name);
		return attribute == null ? 0 : Integer.parseInt(attribute);
	}

	/**
	 * Helper function for getting element attribute as double
	 *
	 * @param name
	 *            Attribute name
	 * @return attribute value or 0 if not found
	 */
	public static double getAttributeAsDouble(Element element, String name) {
		String attribute = element.getAttribute(name);
		return attribute == null ? 0 : Double.parseDouble(attribute);
	}

	/**
	 * get all TEXT nodes
	 *
	 * @return contents of tag
	 */
	public static String getText(Element element) {
		return getText(element, false);
	}

	/**
	 * zwraca tekst z elementow {@link Node.TEXT_NODE} wszystkich podelementow
	 * elementu
	 *
	 * @param element
	 *            przeszukiwany obiekt
	 * @return
	 */
	public static String getTextFromChilds(Element element) {
		return getText(element, true);
	}

	/**
	 * Zwraca tekst z dzieci typu {@link Node.TEXT_NODE} dla element
	 *
	 * @param element
	 *            rodzic z którego pobieramy teksty
	 * @param allChilds
	 *            czy przechodzimy rekurencyjnie przez wszystkie dzieci
	 * @return
	 */
	private static String getText(Element element, boolean allChilds) {
		StringBuilder text = new StringBuilder();
		if (element != null) {
			NodeList nodes = element.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.TEXT_NODE) {
					text.append(node.getNodeValue());
					text.append(' ');
				} else if (allChilds && node.getNodeType() == Node.ELEMENT_NODE) {
					text.append(getText((Element) node, allChilds));
					text.append(' ');
				}
			}
		}
		return text.toString().trim();
	}

	/**
	 * Get first element with given tag name
	 *
	 * @param tagName
	 * @return first element or null if not found
	 */
	public static Element getFirstElementWithTagName(Element element, String tagName) {

		Element node = null;
		NodeList nodeList = element.getElementsByTagName(tagName);

		if (nodeList.getLength() > 0) {
			node = (Element) nodeList.item(0);
		}

		return node;

	}

	public static Element getFirstChildElement(Element element) {
		for (int i = 0; i < element.getChildNodes().getLength(); i++) {
			if (element.getChildNodes().item(i) instanceof Element) {
				return (Element) element.getChildNodes().item(i);// NOPMD
			}
		}
		return null;
	}

	@SuppressWarnings("PMD")
	public static boolean hasParentWithNodeName(Element element, String parentNodeName, String searchUpToNodeName) {
		if (element != null && element.getNodeName().equals(parentNodeName)) {
			return true;
		}
		if (element == null || element.getNodeName().equals(searchUpToNodeName) || !(element.getParentNode() instanceof Element)) {
			return false;
		}
		return hasParentWithNodeName((Element) element.getParentNode(), parentNodeName, searchUpToNodeName);
	}

}
