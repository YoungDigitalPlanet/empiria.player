package eu.ydp.empiria.player.client.util.events.media;

import eu.ydp.empiria.player.client.util.events.EventHandler;



public interface MediaEventHandler extends EventHandler{//<CurrentPageScope>{
	public  void onMediaEvent(MediaEvent event);

}