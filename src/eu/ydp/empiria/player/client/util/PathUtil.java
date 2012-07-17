package eu.ydp.empiria.player.client.util;

import com.google.gwt.core.client.GWT;

public class PathUtil {

	public static String resolvePath(String path, String base){
		if (path.contains("://")  ||  path.charAt(0)=='/'){
			return path;
		} else {
			return base + path;
		}

	}

	public static String getPlayerPathDir(){
		return GWT.getModuleBaseURL();
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
