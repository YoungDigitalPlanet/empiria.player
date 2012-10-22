package eu.ydp.empiria.player.client.util.events.multiplepair;

import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class PairConnectEvent extends AbstractEvent<PairConnectEventHandler, PairConnectEventTypes> {
	public static EventTypes<PairConnectEventHandler, PairConnectEventTypes> types = new EventTypes<PairConnectEventHandler, PairConnectEventTypes>();
	private String sourceItem;
	private String targetItem;

	public PairConnectEvent(PairConnectEventTypes type, String source, String target) {
		super(type, null);
		this.sourceItem = source;
		this.targetItem = target;
	}

	public String getSourceItem() {
		return sourceItem;
	}

	public String getTargetItem() {
		return targetItem;
	}
	
	public String getItemsPair() {
		return getSourceItem() + " " + getTargetItem();
	}

	public PairConnectEvent(PairConnectEventTypes type) {
		super(type, null);
	}

	@Override
	protected EventTypes<PairConnectEventHandler, PairConnectEventTypes> getTypes() {
		return types;
	}

	@Override
	public void dispatch(PairConnectEventHandler handler) {
		handler.onConnectionEvent(this);
	}

	public static Type<PairConnectEventHandler, PairConnectEventTypes> getType(PairConnectEventTypes type) {
		return types.getType(type);
	}

}
