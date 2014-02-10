package eu.ydp.empiria.player.client.controller.data.library;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.gwtutil.client.PathUtil;

public class LibraryLink {

	protected String link;

	public LibraryLink(NodeList libraryNodes, String baseUrl) {

		try {
			Element linkElement = (Element) ((Element) libraryNodes.item(0)).getElementsByTagName("link").item(0);
			link = PathUtil.resolvePath(linkElement.getAttribute("href"), baseUrl);
		} catch (Exception e) {
		}
	}

	public boolean hasLink() {
		return link != null;
	}

	public String getLink() {
		return link;
	}
}
