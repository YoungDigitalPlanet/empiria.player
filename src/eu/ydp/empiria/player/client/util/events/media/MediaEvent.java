package eu.ydp.empiria.player.client.util.events.media;

import java.util.HashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.Event;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class MediaEvent extends  Event<MediaEventHandler> {
	public static Map<MediaEventTypes, Type<MediaEventHandler>> types = new HashMap<MediaEventTypes, Event.Type<MediaEventHandler>>();

	MediaEventTypes type = null;

	private double value;
	public MediaEvent(MediaEventTypes type) {
		this(type, null, 0);
	}
	public MediaEvent(MediaEventTypes type, MediaWrapper<?> source) {
		this(type, source, 0);
	}

	public MediaEvent(MediaEventTypes type, MediaWrapper<?> source,double value) {
		this.type = type;
		this.value = value;
		setSource(source);
	}

	public MediaWrapper<?> getMediaWrapper(){
		return (MediaWrapper<?>) super.getSource();
	}

	public double getValue() {
		return value;
	}

	public MediaEventTypes getType(){
		return type;
	}

	private static void checkIsPresent(MediaEventTypes type){
		if(!types.containsKey(type)){
			types.put(type, new Type<MediaEventHandler>(type,new CurrentPageScope()));
		}
	}
	@Override
	public Event.Type<MediaEventHandler> getAssociatedType() {
		checkIsPresent(type);
		return types.get(type);
	}

	@Override
	public void dispatch(MediaEventHandler handler) {
		handler.onMediaEvent(this);
	}

	public static Type<MediaEventHandler> getType(MediaEventTypes type) {
		checkIsPresent(type);
		return types.get(type);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MediaEvent [type=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}


}
