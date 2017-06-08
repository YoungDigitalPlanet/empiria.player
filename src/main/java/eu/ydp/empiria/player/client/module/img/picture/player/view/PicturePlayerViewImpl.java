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

package eu.ydp.empiria.player.client.module.img.picture.player.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.img.picture.player.presenter.PicturePlayerPresenter;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class PicturePlayerViewImpl extends Composite implements PicturePlayerView {

    private PicturePlayerUiBinder uiBinder = GWT.create(PicturePlayerUiBinder.class);
    private MediaStyleNameConstants styleNameConstants;
    private PicturePlayerPresenter presenter;

    @UiTemplate(value = "PicturePlayerView.ui.xml")
    interface PicturePlayerUiBinder extends UiBinder<Widget, PicturePlayerViewImpl> {
    }

    @UiField
    protected Image image;
    @UiField
    protected FlowPanel container;

    @Inject
    public PicturePlayerViewImpl(MediaStyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void addFullscreenButton() {
        CustomPushButton fullScreenButton = new CustomPushButton();
        fullScreenButton.setStyleName(styleNameConstants.QP_MEDIA_FULLSCREEN_BUTTON());
        fullScreenButton.addClickHandler(createOnOpenFullscreenCommand());

        container.add(fullScreenButton);
    }

    @Override
    public void setPresenter(PicturePlayerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setImage(String altText, String url) {
        image.setAltText(altText);
        image.setUrl(url);
    }

    private ClickHandler createOnOpenFullscreenCommand() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.openFullscreen();
            }
        };
    }
}
