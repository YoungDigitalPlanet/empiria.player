package eu.ydp.empiria.player.client.module.img.picture.player.lightbox.magnific.popup;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;

public class MagnificPopup implements LightBox {

    @Override
    public void openImage(String imageSrc, Widget title) {

        openImageNative(imageSrc, title.getElement());
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
