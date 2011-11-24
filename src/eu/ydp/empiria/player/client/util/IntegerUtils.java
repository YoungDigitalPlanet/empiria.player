package eu.ydp.empiria.player.client.util;

public class IntegerUtils {
	
	public static int tryParseInt(String s){
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return 0;
	}

}
