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

package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.preloader.Preloader;

public class SimulationPreloader implements IsWidget {

    @Inject
    private Preloader preloader;

    public void hidePreloaderAndRemoveFromParent() {
        preloader.hide();
        preloader.asWidget().removeFromParent();
    }

    public void show(int preloaderWidth, int preloaderHeight) {
        preloader.setPreloaderSize(preloaderWidth, preloaderHeight);
        preloader.show();
    }

    @Override
    public Widget asWidget() {
        return preloader.asWidget();
    }
}
