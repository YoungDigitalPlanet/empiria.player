package eu.ydp.empiria.player.client.view.page;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.communication.PageDataError;
import eu.ydp.empiria.player.client.controller.communication.PageDataSummary;
import eu.ydp.empiria.player.client.controller.communication.PageDataToC;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.feedback.AssessmentFeedbackSocket;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequestSocket;
import eu.ydp.empiria.player.client.controller.session.SessionDataCarrier;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemsCollectionSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.SessionDataSocket;

/**
 * 
 * Contains all data that is required to create Page View
 * 
 * @author Rafal Rybacki
 *
 */
public class PageViewCarrier {

	public PageType pageType;
		
	public String[] titles;
	public SessionDataSocket sessionDataSocket;
	public String errorMessage;
	
	public IFlowRequestSocket flowRequestSocket;
	public AssessmentFeedbackSocket assessmentFeedbackSocket;
	
	public PageViewCarrier(){
		pageType = PageType.TEST;
	}

	public PageViewCarrier(PageDataToC p, IFlowRequestSocket frs){
		pageType = PageType.TOC;
		titles = p.titles;
		flowRequestSocket = frs;
	}

	public PageViewCarrier(PageDataSummary p, IFlowRequestSocket frs){
		pageType = PageType.SUMMARY;
		titles = p.titles;
		sessionDataSocket = p.sessionData;
		flowRequestSocket = frs;
		assessmentFeedbackSocket = p.getAssessmentFeedbackSocket();
	}

	public PageViewCarrier(PageDataError p){
		pageType = PageType.ERROR;
		errorMessage = p.errorMessage;
	}
	
	public Widget getPageTitle(){
		return new Label("");
	}
	
	public boolean hasContent(){
		return pageType != PageType.TEST;
	}
	
	public Widget getPageContent(){
		return null;
	}
}
