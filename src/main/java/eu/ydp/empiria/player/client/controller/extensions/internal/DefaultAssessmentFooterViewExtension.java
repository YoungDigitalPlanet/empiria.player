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

package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.components.TwoStateButton;
import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.extensions.types.*;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class DefaultAssessmentFooterViewExtension extends InternalExtension implements AssessmentFooterViewExtension, FlowRequestSocketUserExtension,
        DataSourceDataSocketUserExtension, FlowDataSocketUserExtension, DeliveryEventsListenerExtension, PageInterferenceSocketUserExtension {

    protected DataSourceDataSupplier dataSourceDataSupplier;
    protected FlowDataSupplier flowDataSupplier;
    protected FlowRequestInvoker flowRequestInvoker;
    protected PageInterferenceSocket pageInterferenceSocket;

    private Panel menuPanel;
    private TwoStateButton checkButton;
    private TwoStateButton showAnswersButton;
    private PushButton resetButton;
    private PushButton prevButton;
    private PushButton nextButton;
    private PushButton finishButton;
    private PushButton summaryButton;
    private PushButton continueAssessmentButton;
    private PushButton previewAssessmentButton;

    public DefaultAssessmentFooterViewExtension() {
    }

    @Override
    public void init() {
        createView();
    }

    @Override
    public void setFlowDataSupplier(FlowDataSupplier supplier) {
        flowDataSupplier = supplier;
    }

    @Override
    public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
        dataSourceDataSupplier = supplier;
    }

    @Override
    public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
        flowRequestInvoker = fri;
    }

    @Override
    public void setPageInterferenceSocket(PageInterferenceSocket acs) {
        pageInterferenceSocket = acs;
    }

    @Override
    public ViewSocket getAssessmentFooterViewSocket() {
        return new ViewSocket() {

            @Override
            public Widget getView() {
                return menuPanel;
            }
        };
    }

    protected void createView() {

        // BUTTONS MENU

        menuPanel = new FlowPanel();
        menuPanel.setStyleName("qp-defaultassessmentfooter-buttons");

        checkButton = new TwoStateButton("qp-defaultassessmentfooter-check-button", "qp-defaultassessmentfooter-continue-button");
        checkButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (checkButton.isStateDown()) {
                    flowRequestInvoker.invokeRequest(new FlowRequest.Check());
                } else {
                    flowRequestInvoker.invokeRequest(new FlowRequest.Continue());
                }
            }
        });
        menuPanel.add(checkButton);

        showAnswersButton = new TwoStateButton("qp-defaultassessmentfooter-showanswers-button", "qp-defaultassessmentfooter-hideanswers-button");
        showAnswersButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (showAnswersButton.isStateDown()) {
                    flowRequestInvoker.invokeRequest(new FlowRequest.ShowAnswers());
                } else {
                    flowRequestInvoker.invokeRequest(new FlowRequest.Continue());
                }
            }
        });
        menuPanel.add(showAnswersButton);

        resetButton = new PushButton();
        resetButton.setStylePrimaryName("qp-defaultassessmentfooter-reset-button");
        resetButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flowRequestInvoker.invokeRequest(new FlowRequest.Reset());
            }
        });
        menuPanel.add(resetButton);

        prevButton = new PushButton();
        prevButton.setStylePrimaryName("qp-defaultassessmentfooter-prev-button");
        prevButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flowRequestInvoker.invokeRequest(new FlowRequest.NavigatePreviousItem());
            }
        });
        menuPanel.add(prevButton);

        nextButton = new PushButton();
        nextButton.setStylePrimaryName("qp-defaultassessmentfooter-next-button");
        nextButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flowRequestInvoker.invokeRequest(new FlowRequest.NavigateNextItem());
            }
        });
        menuPanel.add(nextButton);

        finishButton = new PushButton();
        finishButton.setStylePrimaryName("qp-defaultassessmentfooter-finish-button");
        finishButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flowRequestInvoker.invokeRequest(new FlowRequest.NavigateSummary());
            }
        });
        menuPanel.add(finishButton);

        summaryButton = new PushButton();
        summaryButton.setStylePrimaryName("qp-defaultassessmentfooter-summary-button");
        summaryButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flowRequestInvoker.invokeRequest(new FlowRequest.NavigateSummary());
            }
        });
        menuPanel.add(summaryButton);

        continueAssessmentButton = new PushButton();
        continueAssessmentButton.setStylePrimaryName("qp-defaultassessmentfooter-summary-continue-button");
        continueAssessmentButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flowRequestInvoker.invokeRequest(new FlowRequest.NavigateFirstItem());
            }
        });
        menuPanel.add(continueAssessmentButton);

        previewAssessmentButton = new PushButton();
        previewAssessmentButton.setStylePrimaryName("qp-defaultassessmentfooter-summary-preview-button");
        previewAssessmentButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flowRequestInvoker.invokeRequest(new FlowRequest.NavigatePreviewItem(0));
            }
        });
        menuPanel.add(previewAssessmentButton);

    }

    @Override
    public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
        if (deliveryEvent.getType() == DeliveryEventType.SHOW_ANSWERS) {
            checkButton.setStateDown(false);
        } else if (deliveryEvent.getType() == DeliveryEventType.CHECK) {
            showAnswersButton.setStateDown(false);
        } else if (deliveryEvent.getType() == DeliveryEventType.RESET || deliveryEvent.getType() == DeliveryEventType.PAGE_UNLOADING) {
            checkButton.setStateDown(false);
            showAnswersButton.setStateDown(false);
        }

        if (deliveryEvent.getType() == DeliveryEventType.TEST_PAGE_LOADED || deliveryEvent.getType() == DeliveryEventType.TOC_PAGE_LOADED
                || deliveryEvent.getType() == DeliveryEventType.SUMMARY_PAGE_LOADED || deliveryEvent.getType() == DeliveryEventType.CHECK
                || deliveryEvent.getType() == DeliveryEventType.CONTINUE || deliveryEvent.getType() == DeliveryEventType.SHOW_ANSWERS) {
            updateButtons();
        }
    }

    public void updateButtons() {

        boolean isPreview = flowDataSupplier.getFlowOptions().activityMode == ActivityMode.CHECK;

        PageType currPageType = flowDataSupplier.getCurrentPageType();
        PageItemsDisplayMode currItemsDisplayMode = flowDataSupplier.getFlowOptions().itemsDisplayMode;

        checkButton.setVisible(currPageType == PageType.TEST && !isPreview);
        showAnswersButton.setVisible(currPageType == PageType.TEST && !isPreview);
        resetButton.setVisible(currPageType == PageType.TEST && !isPreview);
        prevButton.setVisible(currPageType == PageType.TEST && currItemsDisplayMode == PageItemsDisplayMode.ONE);
        prevButton.setEnabled(flowDataSupplier.getFlowOptions().showToC || flowDataSupplier.getCurrentPageIndex() > 0);
        nextButton.setVisible((currPageType == PageType.TEST && currItemsDisplayMode == PageItemsDisplayMode.ONE) || currPageType == PageType.TOC);
        nextButton.setEnabled(flowDataSupplier.getCurrentPageIndex() < dataSourceDataSupplier.getItemsCount() - 1);
        finishButton.setVisible(currPageType == PageType.TEST && flowDataSupplier.getFlowOptions().showSummary && !isPreview);
        finishButton.setEnabled(flowDataSupplier.getCurrentPageIndex() == dataSourceDataSupplier.getItemsCount() - 1);
        summaryButton.setVisible(isPreview && currPageType == PageType.TEST);
        continueAssessmentButton.setVisible(currPageType == PageType.SUMMARY);
        previewAssessmentButton.setVisible(currPageType == PageType.SUMMARY);

    }

}
