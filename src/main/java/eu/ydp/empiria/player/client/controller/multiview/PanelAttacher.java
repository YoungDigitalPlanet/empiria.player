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

package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.scheduler.Scheduler;

import java.util.List;

import static com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class PanelAttacher {

    @Inject
    private Scheduler scheduler;
    @Inject
    private PanelCache panelsCache;

    public void attach(MultiPageController multiPageController, List<Integer> pagesToAttache) {
        for (Integer pageIndex : pagesToAttache) {
            final KeyValue<FlowPanel, FlowPanel> pair = panelsCache.getOrCreateAndPut(pageIndex);
            scheduleDeferredAttachToParent(multiPageController, pair, pageIndex);
        }
    }

    private void scheduleDeferredAttachToParent(final MultiPageController multiPageController, final KeyValue<FlowPanel, FlowPanel> pair,
                                                final int pageNumber) {
        scheduler.scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                FlowPanel placeHolderPanel = pair.getKey();
                FlowPanel pageContentPanel = pair.getValue();
                placeHolderPanel.add(pageContentPanel);
                if (multiPageController.isCurrentPage(pageNumber)) {
                    multiPageController.setCurrentPageHeight();
                }
            }
        });
    }
}
