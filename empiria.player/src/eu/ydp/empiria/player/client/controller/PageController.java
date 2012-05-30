package eu.ydp.empiria.player.client.controller;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.PageDataError;
import eu.ydp.empiria.player.client.controller.communication.PageDataSummary;
import eu.ydp.empiria.player.client.controller.communication.PageDataTest;
import eu.ydp.empiria.player.client.controller.communication.PageDataToC;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventsHandler;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsSocket;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.log.OperationLogEvent;
import eu.ydp.empiria.player.client.controller.log.OperationLogManager;
import eu.ydp.empiria.player.client.controller.session.sockets.PageSessionSocket;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;
import eu.ydp.empiria.player.client.view.page.PageViewCarrier;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;


public final class PageController implements FlowActivityEventsHandler, PageInterferenceSocket {
	
	public PageController(PageViewSocket pvs, IFlowSocket fs, InteractionEventsSocket is, PageSessionSocket pss, ModulesRegistrySocket mrs){
		pageViewSocket = pvs;
		flowSocket = fs;
		interactionSocket = is;
		pageSessionSocket = pss;
		modulesRegistrySocket = mrs;
	}

	private Page page;
	private PageViewSocket pageViewSocket;
	private PageSessionSocket pageSessionSocket;
	private IFlowSocket flowSocket;
	private InteractionEventsSocket interactionSocket;
	private ModulesRegistrySocket modulesRegistrySocket;
	private ParenthoodSocket parenthoodSocket;
	private ItemController[] items;
	
	private StyleSocket styleSocket;
	public void setStyleSocket( StyleSocket ss) {
		styleSocket = ss;
	}
	
	public void initPage(PageData pageData){
		
		// conception compatibility issue
		page = new Page();

		if (pageData.type == PageType.ERROR ){
			
			pageViewSocket.setPageViewCarrier( new PageViewCarrier((PageDataError)pageData) );
			
			OperationLogManager.logEvent(OperationLogEvent.DISPLAY_PAGE_FAILED);
			
		} else if (pageData.type == PageType.TEST ){
			
			PageDataTest pageDataTest = (PageDataTest)pageData;
			
			items = new ItemController[pageDataTest.datas.length];
			pageViewSocket.initItemViewSockets(pageDataTest.datas.length);
			
			pageViewSocket.setPageViewCarrier(new PageViewCarrier());
	
			for (int i = 0 ; i < pageDataTest.datas.length ; i ++){
				ItemController controller = new ItemController(pageViewSocket.getItemViewSocket(i), flowSocket, interactionSocket, pageSessionSocket.getItemSessionSocket(), modulesRegistrySocket);
				controller.setStyleSocket( styleSocket );
				controller.init(pageDataTest.datas[i], pageDataTest.displayOptions);
				controller.setAssessmentParenthoodSocket(parenthoodSocket);
				if (pageDataTest.flowOptions.activityMode == ActivityMode.CHECK){
					controller.checkItem();
				}
				items[i] = controller;
			}
			
		} else if (pageData.type == PageType.TOC){
			items = null;
			pageViewSocket.setPageViewCarrier(new PageViewCarrier((PageDataToC)pageData, flowSocket));
		} else if (pageData.type == PageType.SUMMARY){
			items = null;
			pageViewSocket.setPageViewCarrier(new PageViewCarrier((PageDataSummary)pageData, flowSocket));
		}
	}
	
	public void close(){
		
		if (items != null){
			for (int i = 0 ; i < items.length ; i ++){
				items[i].close();
			}
		}
	}
	
	public void reset(){
		items = null;
	}

	@Override
	public void handleFlowActivityEvent(FlowActivityEvent event) {
		if (items != null){
			for (int i = 0 ; i < items.length ; i ++){
				items[i].handleFlowActivityEvent(event);
			}
		}
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return createPageSocket();
	}
	
	private JavaScriptObject getItemJsSockets(){
		JavaScriptObject itemSockets = JavaScriptObject.createArray();
		if (items != null){
			for (int i = 0 ; i < items.length ; i ++){
				if (items[i] != null){
					JSArrayUtils.fillArray(itemSockets, i, items[i].getItemSocket().getJsSocket());
				}
			}
		}
		return itemSockets;
	}
	
	private native JavaScriptObject createPageSocket()/*-{
		var socket = {};
		var instance = this;
		socket.getItemSockets = function(){
			return instance.@eu.ydp.empiria.player.client.controller.PageController::getItemJsSockets()();
		}
		return socket;
	}-*/;

	@Override
	public ItemInterferenceSocket[] getItemSockets() {
		int itemsCount = 0;
		
		if (items != null)
			itemsCount = items.length;
		
		ItemInterferenceSocket[] itemSockets = new ItemInterferenceSocket[itemsCount];

		if (items != null){
			for (int i = 0 ; i < items.length ; i ++){
				if (items[i] != null)
					itemSockets[i] = items[i].getItemSocket();
			}
		}
		
		return itemSockets;
	}

	public void setAssessmentParenthoodSocket(ParenthoodSocket assessmentParenthoodSocket) {
		parenthoodSocket = assessmentParenthoodSocket;
	}

	
}
