package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;

public interface SourceListView extends IsWidget, LockUnlockDragDrop{
	void createAndBindUi();
	void createItem(String itemId, String value);
	void hideItem(String itemId);
	void showItem(String itemId);
	String getItemValue(String itemId);
	void setSourceListPresenter(SourceListPresenter sourceListPresenter);
	void lockItemForDragDrop(String itemId);
	void unlockItemForDragDrop(String itemId);
}
