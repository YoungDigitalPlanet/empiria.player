package eu.ydp.empiria.player.client.module.dragdrop;

public class DataObject {
	private final String itemId;
	private final String sourceModuleId;

	public DataObject(String itemId, String sourceModuleId) {
		this.itemId = itemId;
		this.sourceModuleId = sourceModuleId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getSourceModuleId() {
		return sourceModuleId;
	}

}
