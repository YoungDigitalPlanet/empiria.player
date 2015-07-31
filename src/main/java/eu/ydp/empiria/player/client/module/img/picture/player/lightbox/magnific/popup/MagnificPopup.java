package eu.ydp.empiria.player.client.module.img.picture.player.lightbox.magnific.popup;

import com.google.gwt.dom.client.Element;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;

public class MagnificPopup implements LightBox {

    @Override
    public void openImage(String imageSrc, Element title) {
        openImageNative(imageSrc, title);
    }

    private native void openImageNative(String imageSrc, Element title) /*-{
        $wnd.$.magnificPopup.open({
            items: {
                src: imageSrc
            },
            type: 'image',

            image: {
                titleSrc: function () {
                    return title
                }
            },
            closeOnContentClick: true
        });
    }-*/;
}
