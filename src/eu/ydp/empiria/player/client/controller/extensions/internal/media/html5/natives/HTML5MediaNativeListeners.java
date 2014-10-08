package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives;

import com.google.gwt.dom.client.Element;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;

public class HTML5MediaNativeListeners {

	private HTML5OnEvent html5OnEvent;

	public void setCallbackListener(HTML5OnEvent html5OnEvent) {
		this.html5OnEvent = html5OnEvent;
	}

	public native void addListener(Element audioElement, String eventType)/*-{
																			var instance = this;
																			audioElement.addEventListener(eventType, function(){
																			instance.@eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5MediaNativeListeners::html5EndedEvent(Ljava/lang/String;)(eventType);
																			}
																			);
																			}-*/;

	private void html5EndedEvent(String eventType) {
		HTML5MediaEventsType html5MediaEvent = HTML5MediaEventsType.valueOf(eventType);
		html5OnEvent.html5OnEvent(html5MediaEvent);
	}
}
