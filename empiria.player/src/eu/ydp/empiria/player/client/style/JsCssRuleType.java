package eu.ydp.empiria.player.client.style;

public enum JsCssRuleType {
	UNKNOWN_RULE(0), 
	STYLE_RULE(1), 
	CHARSET_RULE(2), 
	IMPORT_RULE(3), 
	MEDIA_RULE(4), 
	FONT_FACE_RULE(5), 
	PAGE_RULE(6), 
	VARIABLES_RULE(7), 
	NAMESPACE_RULE(100), 
	COMMENT(101), 
	WHITE_SPACE(102), 
	STYLE_DECLARATION(1000);

	public final int type;

	JsCssRuleType(int type) {
		this.type = type;
	}

}
