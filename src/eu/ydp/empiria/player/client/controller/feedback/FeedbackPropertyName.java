package eu.ydp.empiria.player.client.controller.feedback;

public enum FeedbackPropertyName{
	RESULT("result"), OK("ok"), WRONG("wrong"), ALL_OK("allOk"), TEXT("text"), DEFAULT("default"), COUNT("count");
	
	private String name;
	
	private FeedbackPropertyName(String name){
		this.name = name;
	}
	
	/**
	 * Searches for FeedbackPropertyName by given string name
	 * @param value string name for which FeedbackPropertyName will be searched
	 * @return retruns instance of FeedbackPropertyName which string name
	 * is equal to geven in 'value' paremeter. If it is not found then
	 * FeedbackPropertyName.DEFAULT is returned.
	 */
	public static FeedbackPropertyName getPropertyName(String value){
		FeedbackPropertyName searchedName = DEFAULT;
		
		for(FeedbackPropertyName feedbackPropertyName: values()){
			if(feedbackPropertyName.name.equals(value)){
				searchedName = feedbackPropertyName;
				break;
			}
		}
		
		return searchedName;
	}
	
	public static boolean exists(FeedbackPropertyName name){
		return !DEFAULT.equals(name);
	}
	
	public static boolean exists(String value){
		return !DEFAULT.equals(getPropertyName(value));
	}
}
