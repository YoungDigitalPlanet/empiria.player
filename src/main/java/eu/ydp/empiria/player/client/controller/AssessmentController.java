/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.sockets.AssessmentInterferenceSocket;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
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
    private final ModulesRegistrySocket modulesRegistrySocket;
    private final IFlowSocket flowSocket;
    private final AssessmentViewSocket assessmentViewSocket;
    private final Page page;
    private final AssessmentControllerFactory controllerFactory;
    private final PageControllerCache controllerCache;
    private final EventsBus eventBus;
    private final AssessmentFactory assessmentFactory;

    @Inject
    public AssessmentController(@Assisted AssessmentViewSocket avs, @Assisted IFlowSocket fsocket, AssessmentSessionSocket ass,
                                ModulesRegistrySocket mrs, Page page, AssessmentControllerFactory controllerFactory,
                                PageControllerCache controllerCache, EventsBus eventBus, AssessmentFactory assessmentFactory) {
        assessmentViewSocket = avs;
        assessmentSessionSocket = ass;
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
            assessment = assessmentFactory.createAssessment(data, options, modulesRegistrySocket);
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
