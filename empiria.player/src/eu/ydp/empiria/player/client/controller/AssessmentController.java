package eu.ydp.empiria.player.client.controller;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.sockets.AssessmentInterferenceSocket;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventsHandler;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsSocket;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.AssessmentSessionSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewCarrier;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;


public class AssessmentController implements FlowActivityEventsHandler, AssessmentInterferenceSocket {

	public AssessmentController(AssessmentViewSocket avs, IFlowSocket fs, InteractionEventsSocket is, AssessmentSessionSocket ass, ModulesRegistrySocket mrs){
		assessmentViewSocket = avs;
		assessmentSessionSocket = ass;
		interactionEventsSocket = is;
		modulesRegistrySocket = mrs;
		pageController = new PageController(avs.getPageViewSocket(), fs, is, ass.getPageSessionSocket(), modulesRegistrySocket);
	}
	
	private AssessmentViewSocket assessmentViewSocket;
	
	public void setStyleSocket( StyleSocket ss) {
		styleSocket = ss;
		pageController.setStyleSocket( styleSocket );
	}
	
	private AssessmentSessionSocket assessmentSessionSocket;
	private ViewSocket headerViewSocket;
	private ViewSocket footerViewSocket;
	
	private Assessment assessment;
	private PageController pageController;
	private StyleSocket styleSocket;
	private InteractionEventsSocket interactionEventsSocket;
	private ModulesRegistrySocket modulesRegistrySocket;
	
	public void setHeaderViewSocket(ViewSocket hvs){
		headerViewSocket = hvs;
	}
	
	public void setFooterViewSocket(ViewSocket fvs){
		footerViewSocket = fvs;
	}
	
	public void init(AssessmentData data, DisplayContentOptions options){
		if (data != null){
			assessment = new Assessment(data, options, interactionEventsSocket, styleSocket, modulesRegistrySocket);
			assessmentViewSocket.setAssessmentViewCarrier(new AssessmentViewCarrier(assessment, headerViewSocket, footerViewSocket));
			assessment.setUp();
			assessment.start();
		}
	}
	
	public void initPage(PageData pageData){
		pageController.initPage(pageData);
		if (assessment != null)
			pageController.setAssessmentParenthoodSocket(assessment.getAssessmentParenthoodSocket());
	}
	
	public void closePage(){
		pageController.close();
	}
	
	public void reset(){
		pageController.reset();
	}

	@Override
	public void handleFlowActivityEvent(FlowActivityEvent event) {
		if (pageController != null)
			pageController.handleFlowActivityEvent(event);	
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return createJsSocket(pageController.getJsSocket());
	}
	
	private native JavaScriptObject createJsSocket(JavaScriptObject pageControllerSocket)/*-{
		var socket = {};
		var pcs = pageControllerSocket;
		socket.getPageControllerSocket = function(){
			return pcs;
		}
		return socket;
	}-*/;
	
	@Override
	public PageInterferenceSocket getPageControllerSocket() {
		return pageController;
	}
}
