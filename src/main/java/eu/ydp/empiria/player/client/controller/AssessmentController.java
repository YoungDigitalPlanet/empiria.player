package eu.ydp.empiria.player.client.controller;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.sockets.AssessmentInterferenceSocket;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsSocket;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.AssessmentSessionSocket;
import eu.ydp.empiria.player.client.gin.factory.AssessmentFactory;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewCarrier;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class AssessmentController implements AssessmentInterferenceSocket {
    private final AssessmentSessionSocket assessmentSessionSocket;
    private ViewSocket headerViewSocket;
    private ViewSocket footerViewSocket;

    private Assessment assessment;
    protected PageController pageController;
    private int controllerPageNumber = -1;
    private final InteractionEventsSocket interactionEventsSocket;
    private final ModulesRegistrySocket modulesRegistrySocket;
    private final IFlowSocket flowSocket;
    private final AssessmentViewSocket assessmentViewSocket;
    private final Page page;
    private final AssessmentControllerFactory controllerFactory;
    private final PageControllerCache controllerCache;
    private final EventsBus eventBus;
    private final AssessmentFactory assessmentFactory;

    @Inject
    public AssessmentController(@Assisted AssessmentViewSocket avs, @Assisted IFlowSocket fsocket, @Assisted InteractionEventsSocket interactionsocket,
                                AssessmentSessionSocket ass, ModulesRegistrySocket mrs, Page page, AssessmentControllerFactory controllerFactory,
                                PageControllerCache controllerCache, EventsBus eventBus, AssessmentFactory assessmentFactory) {
        assessmentViewSocket = avs;
        assessmentSessionSocket = ass;
        interactionEventsSocket = interactionsocket;
        modulesRegistrySocket = mrs;
        flowSocket = fsocket;
        this.page = page;
        this.controllerFactory = controllerFactory;
        this.controllerCache = controllerCache;
        this.eventBus = eventBus;
        this.assessmentFactory = assessmentFactory;
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
                pageController = controllerCache.getOrCreateAndPut(pageNumber);
            } else {
                pageController = controllerFactory.getPageController(assessmentViewSocket.getPageViewSocket(), flowSocket,
                        assessmentSessionSocket.getPageSessionSocket());
                controllerCache.put(pageNumber, pageController);
            }
        }
        return isInCache;
    }

    public void setHeaderViewSocket(ViewSocket hvs) {
        headerViewSocket = hvs;
    }

    public void setFooterViewSocket(ViewSocket fvs) {
        footerViewSocket = fvs;
    }

    public void init(AssessmentData data, DisplayContentOptions options) {
        if (data != null) {
            assessment = assessmentFactory.createAssessment(data, options, interactionEventsSocket, modulesRegistrySocket);
            assessmentViewSocket.setAssessmentViewCarrier(new AssessmentViewCarrier(assessment, headerViewSocket, footerViewSocket));
            assessment.setUp();
            assessment.start();
        }
    }

    public void initPage(PageData pageData) {
        if (!loadPageController(page.getCurrentPageNumber())) {
            eventBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_INITIALIZING, page.getCurrentPageNumber(), this));
            pageController.initPage(pageData);
            eventBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_INITIALIZED, page.getCurrentPageNumber(), this));
        }
        pageController.onShow();

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
        socket.getPageControllerSocket = function () {
            return pcs;
        }
        return socket;
    }-*/;

    @Override
    public PageInterferenceSocket getPageControllerSocket() {
        return pageController;
    }
}
