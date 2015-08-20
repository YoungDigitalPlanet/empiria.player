package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PictureAltProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PictureTitleProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerView;

public class PicturePlayerPresenter {

    private PicturePlayerView view;
    private PicturePlayerFullscreenController fullscreenController;
    private PicturePlayerBean bean;
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    private PictureTitleProvider pictureTitleProvider;
    private PictureAltProvider pictureAltProvider;

    private boolean template = false;

    @Inject
    public PicturePlayerPresenter(PicturePlayerView view, PicturePlayerFullscreenController fullscreenController, PictureTitleProvider pictureTitleProvider, PictureAltProvider pictureAltProvider) {
        this.view = view;
        this.fullscreenController = fullscreenController;
        this.pictureTitleProvider = pictureTitleProvider;
        this.pictureAltProvider = pictureAltProvider;
    }

    public void init(PicturePlayerBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        this.bean = bean;
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
        view.setPresenter(this);
        view.setImage(pictureAltProvider.getPictureAltString(bean), bean.getSrc());
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
