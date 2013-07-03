package eu.ydp.empiria.player.client.module.dragdrop;

public class SourcelistItemValue {

	private final SourcelistItemType type;
	private final String value;
	
	public SourcelistItemValue(SourcelistItemType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public SourcelistItemType getType() {
		return type;
	}

	public String getContent() {
		return value;
	}
	
	
	
	
}
