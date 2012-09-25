package eu.ydp.empiria.player.client.util.events.slideshow;

import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class SlideshowPlayerEvent extends AbstractEvent<SlideshowPlayerEventHandler, SlideshowPlayerEventType> {
	
	private static EventTypes<SlideshowPlayerEventHandler, SlideshowPlayerEventType> types = new EventTypes<SlideshowPlayerEventHandler, SlideshowPlayerEventType>();
	
	public SlideshowPlayerEvent(SlideshowPlayerEventType type){
		this(type, null);
	}
	
	public SlideshowPlayerEvent(SlideshowPlayerEventType type, Object source) {
		super(type, source);
	}
	
	public static Type<SlideshowPlayerEventHandler, SlideshowPlayerEventType> getType(SlideshowPlayerEventType type){
		return types.getType(type);
	}

	@Override
	protected EventTypes<SlideshowPlayerEventHandler, SlideshowPlayerEventType> getTypes() {
		return types;
	}

	@Override
	public void dispatch(SlideshowPlayerEventHandler handler) {
		handler.onSlideshowPlayerEvent(this);
	}
	
}
