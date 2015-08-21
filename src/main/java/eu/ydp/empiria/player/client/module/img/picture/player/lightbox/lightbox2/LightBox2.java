package eu.ydp.empiria.player.client.module.img.picture.player.lightbox.lightbox2;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;

@Singleton
public class LightBox2 implements LightBox {

    private final JavaScriptObject lightbox;

    public LightBox2() {
        lightbox = enableLightBox2();
    }

    private native JavaScriptObject enableLightBox2()/*-{
        var lightbox, options;
        options = new $wnd.LightboxOptions();
        lightbox = new $wnd.Lightbox(options);

        return lightbox;
    }-*/;

    @Override
    public void openImage(String url, Widget title) {
        openImageNative(url, title.getElement(), lightbox);
    }

    private native void openImageNative(String url, Element title, JavaScriptObject lightbox) /*-{
        lightbox.clear();
        lightbox.add(url, title);
        lightbox.start();

    }-*/;

}
