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

package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PictureAltProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerView;

public class PicturePlayerPresenter {

    private PicturePlayerView view;
    private PicturePlayerFullscreenController fullscreenController;
    private PicturePlayerBean bean;
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    private PictureAltProvider pictureAltProvider;

    private boolean template = false;

    @Inject
    public PicturePlayerPresenter(PicturePlayerView view, PicturePlayerFullscreenController fullscreenController, PictureAltProvider pictureAltProvider) {
        this.view = view;
        this.fullscreenController = fullscreenController;
        this.pictureAltProvider = pictureAltProvider;
    }

    public void init(PicturePlayerBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        this.bean = bean;
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
        view.setPresenter(this);
        String pictureAlt = pictureAltProvider.getPictureAltString(bean);
        String pictureSource = bean.getSrc();
        view.setImage(pictureAlt, pictureSource);
        initFullScreenMediaButton(bean);
    }

    private void initFullScreenMediaButton(PicturePlayerBean bean) {
        if (isFullscreenSupported(bean)) {
            view.addFullscreenButton();
        }
    }

    private boolean isFullscreenSupported(PicturePlayerBean bean) {
        return bean.hasFullscreen() && !template;
    }

    public void openFullscreen() {
        fullscreenController.openFullscreen(bean, inlineBodyGeneratorSocket);
    }

    public Widget getView() {
        return view.asWidget();
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }
}
