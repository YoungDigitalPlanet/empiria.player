package eu.ydp.empiria.player.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Window;

public class PathUtil {

	public static String resolvePath(String path, String base){
		if (path.contains("://")  ||  path.startsWith("/")){
			return path;			
		} else {
			return base + path;
		}
		
	}
	
	private static String playerPathDir;
	
	public static String getPlayerPathDir(){
		if (playerPathDir != null)
			return playerPathDir;
		playerPathDir = findPlayerPathDir();
		return playerPathDir;
	}
	
	private static String findPlayerPathDir(){
		NodeList<Element> scriptNodes = Document.get().getElementsByTagName("script");
		String empiriaPlayerFileName = "/empiria.player.nocache.js";
		for (int s = 0 ; s < scriptNodes.getLength() ; s ++){
			if (((Element)scriptNodes.getItem(s)).hasAttribute("src")){
				String src = ((Element)scriptNodes.getItem(s)).getAttribute("src");				
				if (src.endsWith(empiriaPlayerFileName)){
					return src.substring(0, src.indexOf(empiriaPlayerFileName) +1);
				}
			}
		}
		return "";		
	}
	
	public static String normalizePath(String path){
		while (path.matches(".*\\\\[^\\\\]*\\\\[.]{2}.*")){
			path = path.replaceAll("\\\\[^\\\\]*\\\\[.]{2}", "");
		}
		while (path.matches(".*/[^/]*/[.]{2}.*")){
			path = path.replaceAll("/[^/]*/[.]{2}", "");
		}
		return path;
	}
}
