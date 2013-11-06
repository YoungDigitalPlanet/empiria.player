package eu.ydp.empiria.player.client.util.file.xml;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class XmlData {

	private static final String SRC_TAG = "src";

	@Deprecated
	// cala historia z fixowaniem linku jest od czapki
	public XmlData(Document doc, String url) {
		document = doc;
		baseURL = url;
		fix(doc, url);
	}

	/** XML DOM document connected with this Assessment Content */
	private final Document document;

	/** Base URL do document */
	private final String baseURL;

	public Document getDocument() {
		return document;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public boolean checkIntegrity() {
		return true;
	}

	@Deprecated
	private void fix(Document document, String baseUrl) {

		fixLinks(document, baseUrl, "img", SRC_TAG);
		fixLinks(document, baseUrl, "img", "srcFullScreen");
		fixLinks(document, baseUrl, "embed", SRC_TAG);
		fixLinks(document, baseUrl, "sound", SRC_TAG);
		fixLinks(document, baseUrl, "video", SRC_TAG);
		fixLinks(document, baseUrl, "object", "poster");
		fixLinks(document, baseUrl, "source", SRC_TAG);
		fixLinks(document, baseUrl, "a", "href");
		fixLinks(document, baseUrl, "button", "href");
		fixLinks(document, baseUrl, "object", "data");
		fixLinks(document, baseUrl, "audioPlayer", "data");
		fixLinks(document, baseUrl, "audioPlayer", SRC_TAG);
		fixLinks(document, baseUrl, "vocaItem", SRC_TAG);
		fixLinks(document, baseUrl, "flash", SRC_TAG);
		fixLinks(document, baseUrl, "simulationPlayer", SRC_TAG);
		fixLinks(document, baseUrl, "showUrl", "href");
		fixLinks(document, baseUrl, "image", SRC_TAG);
		fixLinks(document, baseUrl, "correctImage", SRC_TAG);
		fixLinks(document, baseUrl, "videoJs", SRC_TAG);
		fixLinks(document, baseUrl, "videoJs", "poster");
	}

	/**
	 * Fix links relative to xml file
	 * 
	 * @param tagName
	 *            tag name
	 * @param attrName
	 *            attribute name with link
	 */
	private void fixLinks(Document document, String baseUrl, String tagName, String attrName) {

		NodeList nodes = document.getElementsByTagName(tagName);

		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			String link = element.getAttribute(attrName);
			if (link != null && !link.startsWith("http")) {
				element.setAttribute(attrName, baseUrl + link);
			}
			// Links open in new window
			if (tagName.compareTo("a") == 0) {
				element.setAttribute("target", "_blank");
			}
		}
	}

}
