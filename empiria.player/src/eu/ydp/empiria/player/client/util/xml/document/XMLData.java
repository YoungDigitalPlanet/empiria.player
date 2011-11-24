package eu.ydp.empiria.player.client.util.xml.document;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class XMLData {

	public XMLData(Document doc, String url){
		document = doc;
		baseURL = url;
		fix(doc, url);
	}
	
	/** XML DOM document connected with this Assessment Content */
	private Document document;
	
	/** Base URL do document */
	private String baseURL;

	public Document getDocument(){
		return document;
	}

	public String getBaseURL(){
		return baseURL;
	}
	
	public boolean checkIntegrity(){
		return true;
	}

	private void fix(Document document, String baseUrl){

		fixLinks(document, baseUrl, "img", "src");
		fixLinks(document, baseUrl, "embed", "src");
		fixLinks(document, baseUrl, "sound", "src");
		fixLinks(document, baseUrl, "video", "src");
		fixLinks(document, baseUrl, "source", "src");
		fixLinks(document, baseUrl, "a", "href");
		fixLinks(document, baseUrl, "object", "data");
		fixLinks(document, baseUrl, "audioPlayer", "data");
		fixLinks(document, baseUrl, "vocaItem", "src");
	}
	
	/**
	 * Fix links relative to xml file
	 * @param tagName tag name
	 * @param attrName attribute name with link
	 */
	private void fixLinks(Document document, String baseUrl, String tagName, String attrName){

		NodeList nodes = document.getElementsByTagName(tagName);

		for(int i = 0; i < nodes.getLength(); i++){
			Element element = (Element)nodes.item(i);
			String link = element.getAttribute(attrName);
			if(link != null && !link.startsWith("http")){
				element.setAttribute(attrName, baseUrl + link);
			}
			// Links open in new window
			if(tagName.compareTo("a") == 0){
				element.setAttribute( "target", "_blank");
			}
		}
	}
	
}
