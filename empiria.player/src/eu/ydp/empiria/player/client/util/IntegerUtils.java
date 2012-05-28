package eu.ydp.empiria.player.client.util;

public class IntegerUtils {
	
	public static int tryParseInt(String s){
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return 0;
	}
	
	public static int tryParseInt(String s, int defaultValue){
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return defaultValue;
	}
	
	public static int tryParseInt(String s, int radix, int defaultValue){
		try {
			return Integer.parseInt(s, radix);
		} catch (Exception e) {
		}
		return defaultValue;
	}

}
