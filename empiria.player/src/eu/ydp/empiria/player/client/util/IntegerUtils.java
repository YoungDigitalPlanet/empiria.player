package eu.ydp.empiria.player.client.util;

public class IntegerUtils {
	
	public static Integer tryParseInt(String s){
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return 0;
	}
	
	public static Integer tryParseInt(String s, Integer defaultValue){
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return defaultValue;
	}
	
	public static Integer tryParseInt(String s, int radix, Integer defaultValue){
		try {
			return Integer.parseInt(s, radix);
		} catch (Exception e) {
		}
		return defaultValue;
	}

}
