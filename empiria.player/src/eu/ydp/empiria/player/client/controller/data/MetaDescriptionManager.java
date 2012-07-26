package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.dom.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.util.XMLUtils;

/**
 * Responsible for putting meta nodes into HTML Document.
 * 
 * @author Rafal Rybacki
 */
public class MetaDescriptionManager {

	public void processDocument(Element documentElement){
		Element metaDescriptionNode = XMLUtils.getFirstElementWithTagName(documentElement, "metaDescription");
		NodeList metaNodes = metaDescriptionNode.getElementsByTagName("meta");
		com.google.gwt.dom.client.Element htmlHeadNode = Document.get().getElementsByTagName("head").getItem(0);
		for (int n = 0 ; n < metaNodes.getLength() ; n ++){
			com.google.gwt.dom.client.Element currElement = convertToDomElement((Element)metaNodes.item(n));
			htmlHeadNode.appendChild(currElement);
		}
	}
	
	private com.google.gwt.dom.client.Element convertToDomElement(Element xmlElement){
		com.google.gwt.dom.client.Element domElement = Document.get().createElement(xmlElement.getNodeName());
		for (int a = 0 ; a < xmlElement.getAttributes().getLength() ; a ++){
			Node currAttr = xmlElement.getAttributes().item(a);
			domElement.setAttribute(currAttr.getNodeName(), currAttr.getNodeValue());			
		}
		return domElement;
	}
}
