package eu.ydp.empiria.player.client.model.feedback;

public enum FeedbackMark {
	NONE, CORRECT, WRONG;
	
	public static FeedbackMark fromString(String s){
		if (s.toUpperCase().compareTo("CORRECT") == 0)
			return CORRECT;
		if (s.toUpperCase().compareTo("WRONG") == 0)
			return WRONG;
		return NONE;
	}
}
