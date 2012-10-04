package eu.ydp.empiria.player.client.controller.communication;

public class PageDataError extends PageData {

	public PageDataError(String err){
		super(PageType.ERROR);
		errorMessage = err;
	}
	
	public String errorMessage;
}
