package eu.ydp.empiria.player.client.controller;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.PageDataError;
import eu.ydp.empiria.player.client.controller.communication.PageDataSummary;
import eu.ydp.empiria.player.client.controller.communication.PageDataTest;
import eu.ydp.empiria.player.client.controller.communication.PageDataToC;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.log.OperationLogEvent;
import eu.ydp.empiria.player.client.controller.log.OperationLogManager;
import eu.ydp.empiria.player.client.controller.session.sockets.PageSessionSocket;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;
import eu.ydp.empiria.player.client.view.page.PageViewCarrier;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;

public class PageController implements PageInterferenceSocket {
	private final PageViewSocket pageViewSocket;
	private final PageSessionSocket pageSessionSocket;
	private final IFlowSocket flowSocket;
	private ParenthoodSocket parenthoodSocket;
	List<ItemController> items;

	private final AssessmentControllerFactory controllerFactory;

	@Inject
	public PageController(@Assisted PageViewSocket pageViewSocket, @Assisted IFlowSocket flowSocket, 
			@Assisted PageSessionSocket pageSessionSocket, AssessmentControllerFactory controllerFactory) {
		this.pageViewSocket = pageViewSocket;
		this.flowSocket = flowSocket;
		this.pageSessionSocket = pageSessionSocket;
		this.controllerFactory = controllerFactory;
	}

	public void initPage(PageData pageData) {
		items = Lists.newArrayList();
		// conception compatibility issue
		if (pageData.type == PageType.ERROR) {
			pageViewSocket.setPageViewCarrier(new PageViewCarrier((PageDataError) pageData));
			OperationLogManager.logEvent(OperationLogEvent.DISPLAY_PAGE_FAILED);
		} else if (pageData.type == PageType.TEST) {
			PageDataTest pageDataTest = (PageDataTest) pageData;
			pageViewSocket.initItemViewSockets(pageDataTest.datas.length);
			pageViewSocket.setPageViewCarrier(new PageViewCarrier());

			for (int i = 0; i < pageDataTest.datas.length; i++) {
				ItemController controller = controllerFactory.getItemController(pageViewSocket.getItemViewSocket(i), flowSocket, 
						pageSessionSocket.getItemSessionSocket());
				controller.init(pageDataTest.displayOptions);
				controller.setAssessmentParenthoodSocket(parenthoodSocket);
				if (pageDataTest.flowOptions.activityMode == ActivityMode.CHECK) {
					controller.checkItem();
				}
				items.add(controller);
			}

		} else if (pageData.type == PageType.TOC) {
			items.clear();
			pageViewSocket.setPageViewCarrier(new PageViewCarrier((PageDataToC) pageData, flowSocket));
		} else if (pageData.type == PageType.SUMMARY) {
			items.clear();
			pageViewSocket.setPageViewCarrier(new PageViewCarrier((PageDataSummary) pageData, flowSocket));
		}
	}

	public void close() {
		for (ItemController itemController : items) {
			itemController.close();
		}
	}

	public void reset() {
		items.clear();
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return createPageSocket();
	}

	private JavaScriptObject getItemJsSockets() {
		JavaScriptObject itemSockets = JavaScriptObject.createArray();
		for (int i = 0; i < items.size(); i++) {
			JSArrayUtils.fillArray(itemSockets, i, items.get(i)
					.getItemSocket()
					.getJsSocket());
		}
		return itemSockets;
	}

	private native JavaScriptObject createPageSocket()/*-{
		var socket = {};
		var instance = this;
		socket.getItemSockets = function() {
			return instance.@eu.ydp.empiria.player.client.controller.PageController::getItemJsSockets()();
		}
		return socket;
	}-*/;

	public void setAssessmentParenthoodSocket(ParenthoodSocket assessmentParenthoodSocket) {
		parenthoodSocket = assessmentParenthoodSocket;
	}

	public boolean hasInteractiveModules() {
		return Iterables.any(items, new Predicate<ItemController>() {
			@Override
			public boolean apply(ItemController item) {
				return item.hasInteractiveModules();
			}
		});
	}

}
