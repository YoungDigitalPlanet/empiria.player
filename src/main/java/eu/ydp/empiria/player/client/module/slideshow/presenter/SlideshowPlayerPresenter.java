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

package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowBean;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;

public class SlideshowPlayerPresenter {

    private final SlideshowPlayerView view;

    @Inject
    public SlideshowPlayerPresenter(SlideshowPlayerView view) {
        this.view = view;
    }

    public void init(SlideshowBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        Element title = bean.getTitle().getTitleValue().getValue();
        Widget titleView = inlineBodyGeneratorSocket.generateInlineBody(title);
        view.setTitle(titleView);
    }

    public void setPager(Widget pagerWidget) {
        view.addPager(pagerWidget);
    }

    public Widget getView() {
        return view.asWidget();
    }
}
