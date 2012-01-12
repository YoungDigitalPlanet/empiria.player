package eu.ydp.empiria.player.client.controller.feedback;

public enum InlineFeedbackAlign {
	TOP_LEFT, TOP_CENTER, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
	
	public static InlineFeedbackAlign fromString(String s){
		InlineFeedbackAlign tmp = InlineFeedbackAlign.valueOf(s.toUpperCase());
		if (tmp != null)
			return tmp;
		
		return TOP_LEFT;
	}
	
	public static boolean isTop(InlineFeedbackAlign a){
		return (a == TOP_LEFT ||  a == TOP_CENTER ||  a == TOP_RIGHT);
	}

	public static boolean isLeft(InlineFeedbackAlign a){
		return (a == TOP_LEFT ||  a == BOTTOM_LEFT);
	}

	public static boolean isRight(InlineFeedbackAlign a){
		return (a == TOP_RIGHT ||  a == BOTTOM_RIGHT);
	}
}
