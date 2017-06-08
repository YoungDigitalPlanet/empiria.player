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

package eu.ydp.empiria.player.client.module.link;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeTestClient;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.SimpleContainerModuleBase;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.command.SetStyleNameCommand;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

import javax.annotation.PostConstruct;

public class LinkModule extends SimpleContainerModuleBase implements WorkModeTestClient {

    protected FlowRequestInvoker flowRequestInvoker;
    protected Panel mainPanel;
    protected int itemIndex = -1;
    protected String url;
    private boolean locked;

    private final LinkStyleNameConstants styleNames;
    private final UserInteractionHandlerFactory userInteractionHandlerFactory;
    private final EventsBus eventsBus;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public LinkModule(@Assisted FlowRequestInvoker flowRequestInvoker, LinkStyleNameConstants styleNames,
                      UserInteractionHandlerFactory userInteractionHandlerFactory, EventsBus eventsBus,
                      PageScopeFactory pageScopeFactory) {
        this.flowRequestInvoker = flowRequestInvoker;
        this.styleNames = styleNames;
        this.userInteractionHandlerFactory = userInteractionHandlerFactory;
        this.eventsBus = eventsBus;
        this.pageScopeFactory = pageScopeFactory;
    }

    @PostConstruct
    public void postConstruct() {
        setLinkContentStyle();
        prepareMainPanel();
        addClickHandler();
        addMouseOverHandler();
        addMouseOutHandler();
        addLinkResetStyleNameOnPageChangeHandler();
    }

    private void addLinkResetStyleNameOnPageChangeHandler() {
        final CurrentPageScope startPage = pageScopeFactory.getCurrentPageScope();
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), new PlayerEventHandler() {

            @Override
            public void onPlayerEvent(PlayerEvent event) {
                final CurrentPageScope currentPage = pageScopeFactory.getCurrentPageScope();
                if (startPage.equals(currentPage)) {
                    setPrimaryStyleName();
                }
            }
        });
    }

    private void setLinkContentStyle() {
        setContainerStyleName(styleNames.QP_LINK_CONTENT());
    }

    private void prepareMainPanel() {
        mainPanel = new FlowPanel();
        setPrimaryStyleName();
        mainPanel.add(getContainer());
    }

    private void addMouseOutHandler() {
        userInteractionHandlerFactory.createUserOutHandler(createOutCommand()).apply(mainPanel);
    }

    private Command createOutCommand() {
        return new SetStyleNameCommand(mainPanel, styleNames.QP_LINK());
    }

    private void addMouseOverHandler() {
        userInteractionHandlerFactory.createUserOverHandler(createOverCommand()).apply(mainPanel);
    }

    private Command createOverCommand() {
        return new SetStyleNameCommand(mainPanel, styleNames.QP_LINK_OVER());
    }

    private void addClickHandler() {
        userInteractionHandlerFactory.createUserClickHandler(createClickCommand()).apply(mainPanel);
    }

    private Command createClickCommand() {
        return new Command() {
            @Override
            public void execute(NativeEvent event) {
                processLink();
            }
        };
    }

    @Override
    public void initModule(Element element) {
        super.initModule(element);
        if (element.hasAttribute("itemIndex")) {
            itemIndex = NumberUtils.tryParseInt(element.getAttribute("itemIndex"), -1);
        }
        if (itemIndex == -1 && element.hasAttribute("url")) {
            url = element.getAttribute("url");
        }
    }

    @Override
    public Widget getView() {
        return mainPanel;
    }

    protected void processLink() {
        if (locked) {
            return;
        }
        if (itemIndex != -1) {
            flowRequestInvoker.invokeRequest(new FlowRequest.NavigateGotoItem(itemIndex));
        } else if (url != null) {
            Window.open(url, "_blank", "");
        }

    }

    private void setPrimaryStyleName() {
        mainPanel.setStyleName(styleNames.QP_LINK());
    }

    @Override
    public void lock(boolean state) {
        super.lock(state);
        this.locked = state;
    }

    @Override
    public void enableTestMode() {
        lock(true);
    }

    @Override
    public void disableTestMode() {
        lock(false);
    }
}
