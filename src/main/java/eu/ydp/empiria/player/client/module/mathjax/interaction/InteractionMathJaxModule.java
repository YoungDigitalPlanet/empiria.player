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

package eu.ydp.empiria.player.client.module.mathjax.interaction;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.empiria.player.client.module.mathjax.interaction.view.InteractionMathJax;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;

public class InteractionMathJaxModule extends AbstractActivityContainerModuleBase implements PlayerEventHandler {

    private static final String REGEX_MATCH_FEEDBACKS = "<feedbacks>[\\s\\S]*?</feedbacks>";

    private MathJaxPresenter presenter;
    private RootPanelDelegate rootPanel;
    private boolean toRerender;

    @Inject
    public InteractionMathJaxModule(MathJaxModuleFactory factory, @InteractionMathJax MathJaxView view, RootPanelDelegate rootPanel, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        this.rootPanel = rootPanel;
        this.presenter = factory.getMathJaxPresenter(view);
        eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.SOURCE_LIST_CLIENTS_SET_SIZE_COMPLETED), this, pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    public void initModule(Element element) {
        initPresenter(element);
        generateGaps(element);
    }

    private void generateGaps(Element element) {
        Panel gapContainer = new FlowPanel();
        gapContainer.setVisible(false);
        rootPanel.getRootPanel().add(gapContainer);

        NodeList gaps = element.getElementsByTagName("gap");
        for (int i = 0; i < gaps.getLength(); i++) {
            Node gap = gaps.item(i);
            getBodyGenerator().processNode(gap, gapContainer);
        }
    }

    private void initPresenter(Element element) {
        String mmlScript = element.getChildNodes().toString();
        String cleanScript = clearFeedbacks(mmlScript);
        presenter.setMmlScript(cleanScript);
    }

    private String clearFeedbacks(String script){
        return script.replaceAll(REGEX_MATCH_FEEDBACKS, "");
    }

    public void markToRerender() {
        this.toRerender = true;
    }


    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (event.getType() == PlayerEventTypes.SOURCE_LIST_CLIENTS_SET_SIZE_COMPLETED) {
            if (toRerender) {
                rerender();
                toRerender = false;
            }
        }
    }

    private void rerender() {
        presenter.rerenderMathElement(getModuleId());
    }
}

