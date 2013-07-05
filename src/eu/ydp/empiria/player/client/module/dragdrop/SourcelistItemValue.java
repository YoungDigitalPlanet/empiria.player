package eu.ydp.empiria.player.client.module.dragdrop;

public class SourcelistItemValue {

	private final SourcelistItemType type;
	private final String value;
	private final String itemId;

	public SourcelistItemValue(SourcelistItemType type, String value,String itemId) {
		super();
		this.type = type;
		this.value = value;
		this.itemId = itemId;
	}

	public SourcelistItemType getType() {
		return type;
	}

	public String getContent() {
		return value;
	}

	public String getItemId() {
		return itemId;
	}




}
