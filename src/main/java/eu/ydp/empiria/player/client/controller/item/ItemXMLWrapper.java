package eu.ydp.empiria.player.client.controller.item;

import javax.annotation.PostConstruct;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class ItemXMLWrapper {

	@Inject
	@PageScoped
	private XmlData xmlData;

	private Document document;

	@PostConstruct
	public void postConstruct() {
		document = xmlData.getDocument();
	}

	public NodeList getStyleDeclaration() {
		return getElementsByTagName("styleDeclaration");
	}

	public NodeList getAssessmentItems() {
		return getElementsByTagName("assessmentItem");
	}

	public Element getItemBody() {
		return (Element) getElementsByTagName("itemBody").item(0);
	}

	public NodeList getResponseDeclarations() {
		return getElementsByTagName("responseDeclaration");
	}

	public NodeList getExpressions() {
		return getElementsByTagName("expressions");
	}

	public String getBaseURL() {
		return xmlData.getBaseURL();
	}

	private NodeList getElementsByTagName(String nodeName) {
		return document.getElementsByTagName(nodeName);
	}

}
