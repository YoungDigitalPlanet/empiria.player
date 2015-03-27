package eu.ydp.empiria.player.client.module.listener;

import com.google.gwt.dom.client.Element;

public interface ITouchEventsListener {

	public void onTouchStart(Element target, int pageX, int pageY);

	public void onTouchMove(Element target, int pageX, int pageY);

	public void onTouchEnd(Element target);
}
