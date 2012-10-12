package eu.ydp.empiria.player.client.util.events.animation;

import com.google.gwt.event.shared.EventHandler;

public interface TransitionEndHandler extends EventHandler{
	public void onTransitionEnd(TransitionEndEvent event);
}
