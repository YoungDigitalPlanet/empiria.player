package eu.ydp.empiria.player.client.module.img.picture.player.lightbox.lightbox2;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.*;

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
	public void openImage(String url, String title) {
		openImageNative(url, title, lightbox);
	}

	private native void openImageNative(String url, String title, JavaScriptObject lightbox) /*-{
		lightbox.clear();
		lightbox.add(url, title);
		lightbox.start();
	}-*/;

}
