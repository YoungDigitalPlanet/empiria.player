package eu.ydp.empiria.player.client.controller.events.internal;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;

public class InternalEvent{

	public InternalEvent(Element tgt, int t){
		target = tgt;
		type = t;
		clientX = 0;
		clientY = 0;
	}
	
	public InternalEvent(Element tgt, int t, int x, int y){
		target = tgt;
		type = t;
		clientX = x;
		clientY = y;
	}
	
	public InternalEvent(Event event){
		target = Element.as(event.getEventTarget());
		type = event.getTypeInt();
		clientX = event.getClientX();
		clientY = event.getClientY();
		originalEvent = event;
	}
	
	protected Element target;
	protected int type;
	protected int clientX;
	protected int clientY;
	protected Event originalEvent;
	
	public void stopPropagation(){
		if (originalEvent != null)
			originalEvent.stopPropagation();
	}

	public int getTypeInt(){
		return type;
	}

	public int getClientX(){
		return clientX;
	}

	public int getClientY(){
		return clientY;
	}

	public Element getEventTargetElement(){
		return target;
	}
}
