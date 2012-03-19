package eu.ydp.empiria.player.client.util;

public class PathUtil {

	public static String resolvePath(String path, String base){
		if (path.contains("://")  ||  path.startsWith("/")){
			return path;			
		} else {
			return base + path;
		}
		
	}
}
