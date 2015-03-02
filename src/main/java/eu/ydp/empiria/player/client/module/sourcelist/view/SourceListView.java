package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public interface SourceListView extends IsWidget, LockUnlockDragDrop {
	void createAndBindUi();

	void createItem(SourcelistItemValue sourcelistItemValue);

	void hideItem(String itemId);

	void showItem(String itemId);

	SourcelistItemValue getItemValue(String itemId);

	void setSourceListPresenter(SourceListPresenter sourceListPresenter);

	void lockItemForDragDrop(String itemId);

	void unlockItemForDragDrop(String itemId);

	HasDimensions getMaxItemSize();
}
