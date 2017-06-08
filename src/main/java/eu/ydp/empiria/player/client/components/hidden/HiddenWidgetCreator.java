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

package eu.ydp.empiria.player.client.components.hidden;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provides possibility to add widget to RootPanel and hide it. i.e.: hidden native media controls or buttons impossible to be styled (when dummy button calls
 * hidden button)
 */
public class HiddenWidgetCreator {

    /**
     * IOS nie lubi jak kilka elelemntow audio znajduje sie obok siebie dlatego dla kazdego nowo dodawanego elementu tworzymy nowy kontener.
     */
    @Inject
    Provider<HiddenContainerWidget> containerWidget;

    public void addWidgetToHiddenContainerOnRootPanel(final Widget widget) {

        HiddenContainerWidget hiddenContainerWidget = containerWidget.get();
        hiddenContainerWidget.addWidgetToContainer(widget);
        addPanelToRootPanel(hiddenContainerWidget);

    }

    private void addPanelToRootPanel(Widget hiddenPanel) {
        RootPanel.get().add(hiddenPanel);
    }
}
