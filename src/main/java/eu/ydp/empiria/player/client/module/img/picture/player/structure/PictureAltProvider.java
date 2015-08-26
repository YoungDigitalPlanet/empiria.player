package eu.ydp.empiria.player.client.module.img.picture.player.structure;


import com.google.inject.Inject;


public class PictureAltProvider {

    private PictureTitleProvider pictureTitleProvider;

    @Inject
    public PictureAltProvider(PictureTitleProvider pictureTitleProvider) {
        this.pictureTitleProvider = pictureTitleProvider;
    }

    public String getPictureAltString(PicturePlayerBean bean) {
        if (bean.hasAlt()) {
            return bean.getAlt();
        } else {
            return pictureTitleProvider.getPictureTitleString(bean);
        }
    }
}
