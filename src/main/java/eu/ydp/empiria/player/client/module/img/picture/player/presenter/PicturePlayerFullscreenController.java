package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBoxProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;

public class PicturePlayerFullscreenController {

    private LightBoxProvider lightBoxProvider;
    private UserAgentCheckerWrapper userAgentCheckerWrapper;
    private PicturePlayerFullscreenDelay fullscreenDelay;

    @Inject
    public PicturePlayerFullscreenController(LightBoxProvider lightBoxProvider, UserAgentCheckerWrapper userAgentCheckerWrapper,
                                             PicturePlayerFullscreenDelay fullscreenDelay) {
        this.lightBoxProvider = lightBoxProvider;
        this.userAgentCheckerWrapper = userAgentCheckerWrapper;
        this.fullscreenDelay = fullscreenDelay;
    }

    public void openFullscreen(PicturePlayerBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        LightBox lightBox = lightBoxProvider.getFullscreen(bean.getFullscreenMode());

        Widget titleBody = inlineBodyGeneratorSocket.generateInlineBody(bean.getTitleBean().getTitleName().getValue());

        if (userAgentCheckerWrapper.isStackAndroidBrowser()) {
            fullscreenDelay.openImageWithDelay(lightBox, bean.getSrcFullScreen(), titleBody.getElement());
        } else {
            lightBox.openImage(bean.getSrcFullScreen(), titleBody.getElement());
        }
    }
}
