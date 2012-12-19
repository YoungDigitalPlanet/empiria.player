package eu.ydp.empiria.player.client.lightbox2;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.util.events.fullscreen.FullScreenEvent;
import eu.ydp.empiria.player.client.util.events.fullscreen.FullScreenEventHandler;

public class LightBox2 {

	private final JavaScriptObject lightbox; //NOPMD use by jsni

	private final Set<FullScreenEventHandler> eventHandlers = new HashSet<FullScreenEventHandler>();

	/**
	 * Dodaje handlera nasluchujacego na zmiany trybu pelnoekranowego
	 *
	 * @param handler
	 */
	public void addFullScreenEventHandler(FullScreenEventHandler handler) {
		eventHandlers.add(handler);
	}

	private void fireEvent(FullScreenEvent event){
		for(FullScreenEventHandler handler: eventHandlers){
			handler.handleEvent(event);
		}
	}

	public LightBox2() {
		lightbox = enableLightBox2();
	}

	private native JavaScriptObject enableLightBox2()/*-{
		var lightbox, options;
		options = new $wnd.LightboxOptions();
		lightbox = new $wnd.Lightbox(options);
		var thiss = this;
		lightbox.addListener({handle : function(object){
			thiss.@eu.ydp.empiria.player.client.lightbox2.LightBox2::handleEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(object);
		}});
		return lightbox;
	}-*/;

	public native void addImage(String url, String title)/*-{
		this.@eu.ydp.empiria.player.client.lightbox2.LightBox2::lightbox.add(url, title);
	}-*/;

	public native void clear()/*-{
		this.@eu.ydp.empiria.player.client.lightbox2.LightBox2::lightbox.clear();
	}-*/;

	public native void start()/*-{
		this.@eu.ydp.empiria.player.client.lightbox2.LightBox2::lightbox.start();
	}-*/;

	protected void handleEvent(JavaScriptObject object){
		fireEvent((FullScreenNativeEvent)object);
	}

}
