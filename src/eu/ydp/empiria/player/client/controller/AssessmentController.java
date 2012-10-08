package eu.ydp.empiria.player.client.controller;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.sockets.AssessmentInterferenceSocket;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsSocket;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.AssessmentSessionSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewCarrier;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class AssessmentController implements AssessmentInterferenceSocket {
	private final AssessmentSessionSocket assessmentSessionSocket;// NOPMD
	private ViewSocket headerViewSocket;
	private ViewSocket footerViewSocket;

	private Assessment assessment;
	protected PageController pageController;
	private int controllerPageNumber = -1; // NOPMD
	private StyleSocket styleSocket;
	private final InteractionEventsSocket interactionEventsSocket; // NOPMD
	private final ModulesRegistrySocket modulesRegistrySocket; // NOPMD
	private final PageControllerCache controllerCache = PlayerGinjector.INSTANCE.getPageControllerCache();
	private final IFlowSocket flowSocket;
	private final AssessmentViewSocket assessmentViewSocket; // NOPMD
	private final Page page = PlayerGinjector.INSTANCE.getPage();
	private final IItemProperties itemProperties = createItemProperties();
	
	public AssessmentController(AssessmentViewSocket avs, IFlowSocket fsocket, InteractionEventsSocket interactionsocket, AssessmentSessionSocket ass, ModulesRegistrySocket mrs) {
		assessmentViewSocket = avs;
		assessmentSessionSocket = ass;
		interactionEventsSocket = interactionsocket;
		modulesRegistrySocket = mrs;
		flowSocket = fsocket;
	}

	/**
	 * zwraca true jezeli element zostal pobrany z kesza
	 *
	 * @param pageNumber
	 * @return
	 */
	protected boolean loadPageController(int pageNumber) {
		boolean isInCache = controllerCache.isPresent(pageNumber);
		if (pageNumber != controllerPageNumber) {
			controllerPageNumber = pageNumber;
			if (isInCache) {
				pageController = controllerCache.get(pageNumber);
			} else {
				pageController = new PageController(assessmentViewSocket.getPageViewSocket(), flowSocket, interactionEventsSocket, assessmentSessionSocket.getPageSessionSocket(),
						modulesRegistrySocket);
				controllerCache.put(pageNumber, pageController);
			}
		}
		return isInCache;
	}

	public void setStyleSocket(StyleSocket styleSocket) {
		this.styleSocket = styleSocket;
	}

	public void setHeaderViewSocket(ViewSocket hvs) {
		headerViewSocket = hvs;
	}

	public void setFooterViewSocket(ViewSocket fvs) {
		footerViewSocket = fvs;
	}

	public void init(AssessmentData data, DisplayContentOptions options) {
		if (data != null) {
			assessment = new Assessment(data, options, interactionEventsSocket, styleSocket, modulesRegistrySocket, itemProperties);						
			assessmentViewSocket.setAssessmentViewCarrier(new AssessmentViewCarrier(assessment, headerViewSocket, footerViewSocket));
			assessment.setUp();
			assessment.start();
		}
	}

	public void initPage(PageData pageData) {
		if (!loadPageController(page.getCurrentPageNumber())) {
			pageController.setStyleSocket(styleSocket);
			pageController.initPage(pageData);
		}


		if (assessment != null) {
			pageController.setAssessmentParenthoodSocket(assessment.getAssessmentParenthoodSocket());
		}
	}

	public void closePage() {
		if (pageController != null) {
			pageController.close();
		}
	}

	public void reset() {
		if (pageController != null) {
			pageController.reset();
		}
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return createJsSocket(pageController.getJsSocket());
	}

	@SuppressWarnings("PMD")
	private native JavaScriptObject createJsSocket(JavaScriptObject pageControllerSocket)/*-{
		var socket = {};
		var pcs = pageControllerSocket;
		socket.getPageControllerSocket = function() {
			return pcs;
		}
		return socket;
	}-*/;

	@Override
	public PageInterferenceSocket getPageControllerSocket() {
		return pageController;
	}	
	
	protected IItemProperties createItemProperties() {
		return new IItemProperties() {
			
			@Override
			public boolean hasInteractiveModules() {		
				return (pageController != null && pageController.hasInteractiveModules());
			}
		};
	}
}
