package eu.ydp.empiria.player.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;

public class PathUtil {

	public static String resolvePath(String path, String base){
		if( GWT.getHostPageBaseURL().startsWith("file://") ){

			String localURL = URL.decode( GWT.getHostPageBaseURL() );
			return localURL + path;
			
		} else if (path.contains("://")  ||  path.startsWith("/")){
			
			return path;
			
		} else {
			return base + path;
		}
		
	}
}
