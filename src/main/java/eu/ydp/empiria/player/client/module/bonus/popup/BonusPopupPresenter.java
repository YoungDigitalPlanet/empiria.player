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

package eu.ydp.empiria.player.client.module.bonus.popup;

import com.google.common.base.Optional;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyObject;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.gwtutil.client.util.geom.Size;

import javax.annotation.PostConstruct;

@Singleton
public class BonusPopupPresenter {

    @Inject
    private BonusPopupView view;
    private Optional<EndHandler> lastEndHandler = Optional.absent();

    @PostConstruct
    public void initialize() {
        view.setPresenterOnView(this);
    }

    public void showImage(String url, Size size) {
        lastEndHandler = Optional.absent();
        view.showImage(url, size);
        view.attachToRoot();
    }

    public void showAnimation(SwiffyObject swiffy, Size size, EndHandler endHandler) {
        lastEndHandler = Optional.fromNullable(endHandler);
        IsWidget widget = swiffy.getWidget();
        view.setAnimationWidget(widget, size);
        view.attachToRoot();
        swiffy.start();
    }

    public void closeClicked() {
        view.reset();

        if (lastEndHandler.isPresent()) {
            lastEndHandler.get().onEnd();
            lastEndHandler = Optional.absent();
        }
    }
}
