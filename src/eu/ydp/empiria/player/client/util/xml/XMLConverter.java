package eu.ydp.empiria.player.client.util.xml;

import java.util.List;
import java.util.Vector;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.body.ModuleEventsListener;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.module.ModuleSocket;

@Deprecated
public abstract class XMLConverter {

	public static Element getDOM(com.google.gwt.xml.client.Element element, Vector<String> ignoredTags){
		Element dom = createElement(element);
		parseXMLElement(element, dom, null, null, ignoredTags, null);
		return dom;
	}
	
	private static void parseXMLElement(com.google.gwt.xml.client.Element srcElement, com.google.gwt.dom.client.Element dstElement, 
			ModuleSocket moduleSocket, ModuleEventsListener moduleEventsListener,  
			Vector<String> ignoredNodes, List<String> ignoredTagNames){
		NodeList	nodes = srcElement.getChildNodes();
		Document	doc = Document.get();
		com.google.gwt.dom.client.Element domElement;
		
		for(int i = 0; i < nodes.getLength(); i++){
			Node node = nodes.item(i);
			
			if (ignoredTagNames != null  &&  node.getNodeName().compareTo("qy:tag") == 0  &&  ignoredTagNames.contains(((com.google.gwt.xml.client.Element)node).getAttribute("name"))){
			
			} else if(ignoredTagNames != null  &&  node.getNodeName().compareTo("qy:tag") != 0  &&  ignoredTagNames.contains("untagged")  &&  !detectTagNode(node)){
			
			} else if (node.getNodeName().compareTo("qy:tag") == 0){
				com.google.gwt.xml.client.Element xmlElement = (com.google.gwt.xml.client.Element)node;
				// Add children
				parseXMLElement(xmlElement, dstElement, moduleSocket, moduleEventsListener, ignoredNodes, null);
			
			}else if(node.getNodeType() == Node.TEXT_NODE){
				dstElement.appendChild(doc.createTextNode(node.getNodeValue()));
			} else  if (node.getNodeType() == Node.COMMENT_NODE){
			
			}else if (ignoredNodes != null  &&  ignoredNodes.contains(node.getNodeName())){
				
			}else
			{
				com.google.gwt.xml.client.Element xmlElement = (com.google.gwt.xml.client.Element)node;
				domElement = createElement((com.google.gwt.xml.client.Element)node);
				dstElement.appendChild(domElement);
				// Copy attributes
				parseXMLAttributes(xmlElement, domElement);
				// Add children
				parseXMLElement(xmlElement, domElement, moduleSocket, moduleEventsListener, ignoredNodes, ignoredTagNames);
			}
		}
	}

	/**
	 * copy html attributes from XML into DOM
	 * @param srcElement
	 * @param dstElement
	 */
	private static void parseXMLAttributes(com.google.gwt.xml.client.Element srcElement, com.google.gwt.dom.client.Element dstElement){
		NamedNodeMap attributes = srcElement.getAttributes();
		
		for(int i = 0; i < attributes.getLength(); i++){
			Node attribute = attributes.item(i);
			if (attribute.getNodeValue().length() > 0){
				if (attribute.getNodeName().equals("class")){
					dstElement.addClassName(attribute.getNodeValue());
				} else {
					dstElement.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
				}
			}
			
		}
	}
	
	private static boolean detectTagNode(Node node){
		if (node.getNodeType() == Node.TEXT_NODE)
			return false;
		com.google.gwt.xml.client.Element e = (com.google.gwt.xml.client.Element)node;
		return e.getElementsByTagName("qy:tag").getLength() > 0;
	}
	
	private static Element createElement(com.google.gwt.xml.client.Element element){
		Element dom = Document.get().createElement(translateNodeName(element.getNodeName()));
		updateAttributes(element.getNodeName(), dom);
		return dom;
	}
	
	private static String translateNodeName(String nodeName){
		if (nodeName.toLowerCase().compareTo("itembody") == 0)
			return "div";
		if (nodeName.toLowerCase().compareTo("prompt") == 0)
			return "div";
		if (nodeName.toLowerCase().compareTo("simplechoice") == 0)
			return "span";
		if (nodeName.toLowerCase().compareTo("feedbackinline") == 0)
			return "div";
		if (nodeName.toLowerCase().compareTo("modalfeedback") == 0)
			return "div";
		if (nodeName.toLowerCase().compareTo("contents") == 0)
			return "div";
		if (nodeName.toLowerCase().compareTo("dragelement") == 0)
			return "span";
		if (nodeName.toLowerCase().compareTo("simpleassociablechoice") == 0)
			return "span";
		if (nodeName.toLowerCase().compareTo("textentrymultipleinteraction") == 0)
			return "span";
		if (nodeName.toLowerCase().compareTo("group") == 0)
			return "div";
		if (nodeName.toLowerCase().compareTo("simpletext") == 0)
			return "p";
		if (nodeName.toLowerCase().compareTo("textinteraction") == 0)
			return "div";
		
		return nodeName;
	}
	
	private static void updateAttributes(String orgNodeName, Element element){
		if (orgNodeName.toLowerCase().equals("group")){
			element.setAttribute("class", "qp-group");
		}
		if (orgNodeName.toLowerCase().equals("simpletext")){
			element.setAttribute("class", "qp-simpletext");
		}
		if (orgNodeName.toLowerCase().equals("textinteraction")){
			element.setAttribute("class", "qp-textinteraction");
		}
		
	}
	
}
