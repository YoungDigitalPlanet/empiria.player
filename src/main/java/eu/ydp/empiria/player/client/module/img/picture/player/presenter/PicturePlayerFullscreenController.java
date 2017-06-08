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
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBoxProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PictureTitleProvider;

@Singleton
public class PicturePlayerFullscreenController {

    private LightBoxProvider lightBoxProvider;
    private UserAgentCheckerWrapper userAgentCheckerWrapper;
    private PicturePlayerFullscreenDelay fullscreenDelay;
    private PictureTitleProvider pictureTitleProvider;

    @Inject
    public PicturePlayerFullscreenController(LightBoxProvider lightBoxProvider, UserAgentCheckerWrapper userAgentCheckerWrapper,
                                             PicturePlayerFullscreenDelay fullscreenDelay, PictureTitleProvider pictureTitleProvider) {
        this.lightBoxProvider = lightBoxProvider;
        this.userAgentCheckerWrapper = userAgentCheckerWrapper;
        this.fullscreenDelay = fullscreenDelay;
        this.pictureTitleProvider = pictureTitleProvider;
    }

    public void openFullscreen(PicturePlayerBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        LightBox lightBox = lightBoxProvider.getFullscreen(bean.getFullscreenMode());

        Widget titleBody = pictureTitleProvider.getPictureTitleWidget(bean, inlineBodyGeneratorSocket);

        String srcFullScreen = bean.getSrcFullScreen();

        if (userAgentCheckerWrapper.isStackAndroidBrowser()) {
            fullscreenDelay.openImageWithDelay(lightBox, srcFullScreen, titleBody);
        } else {
            lightBox.openImage(srcFullScreen, titleBody);
        }
    }
}
