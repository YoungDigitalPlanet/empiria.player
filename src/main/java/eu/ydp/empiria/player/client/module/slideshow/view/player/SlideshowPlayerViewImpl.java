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

package eu.ydp.empiria.player.client.module.slideshow.view.player;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlideshowPlayerViewImpl implements SlideshowPlayerView {

    @UiTemplate("SlideshowPlayerView.ui.xml")
    interface SlideshowModuleUiBinder extends UiBinder<Widget, SlideshowPlayerViewImpl> {
    }

    ;

    private final SlideshowModuleUiBinder uiBinder = GWT.create(SlideshowModuleUiBinder.class);

    @UiField
    protected FlowPanel titlePanel;

    @UiField
    protected Panel mainPanel;

    @UiField
    protected Panel slidesPanel;

    @UiField
    protected Panel pagerPanel;

    @Inject
    public SlideshowPlayerViewImpl(@ModuleScoped SlideView slideView, @ModuleScoped SlideshowButtonsView buttonsView) {
        uiBinder.createAndBindUi(this);
        slidesPanel.add(slideView);
        mainPanel.add(buttonsView.asWidget());
    }

    @Override
    public void setTitle(Widget title) {
        titlePanel.clear();
        titlePanel.add(title);
    }

    @Override
    public Widget asWidget() {
        return mainPanel;
    }

    @Override
    public void addPager(Widget pager) {
        pagerPanel.add(pager);
    }
}
