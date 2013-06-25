package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DragDropEventBase;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.gin.factory.TouchReservationFactory;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public class SourceListViewImpl extends Composite implements SourceListView {

	protected static SourceListViewImplUiBinder uiBinder = GWT.create(SourceListViewImplUiBinder.class);

	interface SourceListViewImplUiBinder extends UiBinder<Widget, SourceListViewImpl> {
	}

	@Inject private TouchReservationFactory touchReservationFactory;
	@Inject private Provider<SourceListViewItem> sourceListViewItemProvider;
	@UiField FlowPanel items;

	private final BiMap<String,SourceListViewItem> itemIdToItemCollection = HashBiMap.create();
	private SourceListPresenter sourceListPresenter;

	private SourceListViewItem getItem(String itemContent) {
		SourceListViewItem item = sourceListViewItemProvider.get();
		item.setSourceListView(this);
		item.createAndBindUi(itemContent);
		return item;
	}

	@Override
	public void createAndBindUi() {
		initWidget(uiBinder.createAndBindUi(this));
		touchReservationFactory.addTouchReservationHandler(items);
	}

	public void disableItems(boolean disabled) {
		for (SourceListViewItem item : itemIdToItemCollection.values()) {
			item.setDisableDrag(disabled);
		}
	}

	public void onDragEvent(DragDropEventTypes dropEventType, SourceListViewItem item, DragDropEventBase<?> dragEvent) {
		String itemId = itemIdToItemCollection.inverse().get(item);
		if(dropEventType == DragDropEventTypes.DRAG_START){
			setDataOnNativeEvent(dragEvent, itemId);
		}
		sourceListPresenter.onDragEvent(dropEventType, itemId);
	}

	private void setDataOnNativeEvent(DragDropEventBase<?> dragEvent, String itemId) {
		DragDataObject dataObject = sourceListPresenter.getDragDataObject(itemId);
		dragEvent.setData("json", dataObject.toJSON());
	}

	@Override
	public String getItemValue(String itemId) {
		return itemIdToItemCollection.get(itemId).getItemContent();
	}

	@Override
	public void createItem(String itemId, String itemContent) {
		SourceListViewItem item = getItem(itemContent);
		items.add(item);
		itemIdToItemCollection.put(itemId, item);
	}

	@Override
	public void hideItem(String itemId) {
		itemIdToItemCollection.get(itemId).hide();
	}

	@Override
	public void showItem(String itemId) {
		itemIdToItemCollection.get(itemId).show();
	}

	@Override
	public void setSourceListPresenter(SourceListPresenter sourceListPresenter) {
		this.sourceListPresenter = sourceListPresenter;
	}

}
