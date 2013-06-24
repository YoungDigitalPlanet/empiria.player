package eu.ydp.empiria.player.client.module.dragdrop;

public interface SourcelistManager {

	void registerModule(SourcelistClient module);

	void registerSourcelist(Sourcelist sourcelist);

	void dragStart(String sourceModuleId);

	void dragEnd(String itemId, String sourceModuleId, String targetModuleId);

	void dragEndSourcelist(String itemId, String sourceModuleId);

	void dragCanceled();

	DataObject getValue(String itemId, String targetModuleId);

	void onUserValueChanged();
}
