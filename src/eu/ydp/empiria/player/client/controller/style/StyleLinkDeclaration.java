package eu.ydp.empiria.player.client.controller.style;

import java.util.Vector;

import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

public class StyleLinkDeclaration {

	public StyleLinkDeclaration(NodeList styleNodes, String pbaseUrl){
		
		styles = new Vector<StyleLink>();
		baseUrl = pbaseUrl;
		
		if (styleNodes == null  ||  styleNodes.getLength() == 0)
			return;
		
		NodeList styleLinkNodes = styleNodes.item(0).getChildNodes();
		
		for (int n = 0 ; n < styleLinkNodes.getLength() ; n ++){
			Node styleLinkNode = styleLinkNodes.item(n);
			try {
				StyleLink sd = new StyleLink(styleLinkNode.getAttributes().getNamedItem("href").getNodeValue(),
					styleLinkNode.getAttributes().getNamedItem("userAgent").getNodeValue());
				styles.add(sd);
			} catch (Exception e) {
			}
		}
		
	}
	
	private Vector<StyleLink> styles;
	private String baseUrl;
	
	public Vector<String> getStyleLinksForUserAgent(String userAgent){
		
		Vector<String> links = new Vector<String>();
		
		for (int s = 0 ; s < styles.size() ; s ++){
			if (userAgent.matches(styles.get(s).userAgent)){
				links.add( createAbsolutePath(styles.get(s).href) );
			}
		}
		
		return links;
	}
	
	private String createAbsolutePath(String file){
		if (file.length() > 0  &&  !file.contains("http://")  &&  !file.contains("file:///")){
			if (baseUrl.endsWith("/")  ||  baseUrl.endsWith("\\"))
				return baseUrl + file;
			else
				return baseUrl + "/" + file;
		}
		
		return file;
	}
	
	
}
