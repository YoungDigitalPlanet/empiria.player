package eu.ydp.empiria.player.client.util.events.internal.page;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;

public class PageEvent extends AbstractEvent<PageEventHandler, PageEventTypes> {

	public static EventTypes<PageEventHandler, PageEventTypes> types = new EventTypes<PageEventHandler, PageEventTypes>();
	private final Object value;

	public PageEvent(PageEventTypes type, Object value) {
		this(type, value, null);
	}

	public PageEvent(PageEventTypes type, Object value, Object source) {
		super(type, source);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	protected EventTypes<PageEventHandler, PageEventTypes> getTypes() {
		return types;
	}

	@Override
	public void dispatch(PageEventHandler handler) {
		handler.onPageEvent(this);
	}

	public static Type<PageEventHandler, PageEventTypes> getType(PageEventTypes type) {
		return types.getType(type);
	}

	public static Type<PageEventHandler, PageEventTypes>[] getTypes(PageEventTypes... typeList) {
		return typeList.length > 0 ? types.getTypes(typeList) : new Type[0];
	}

}
