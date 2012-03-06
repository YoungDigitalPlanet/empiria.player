package eu.ydp.empiria.player.client.style;

public class StyleUtil {
	
	private static final String STYLENAME_PREFIX = "qp";
	
	public static String getStyleName(String styleName){
		return STYLENAME_PREFIX + "-" + styleName;
	}
	
}
