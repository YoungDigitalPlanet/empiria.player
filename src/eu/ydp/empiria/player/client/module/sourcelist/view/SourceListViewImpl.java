package eu.ydp.empiria.player.client.module.sourcelist.view;

import static eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes.DRAG_END;
import static eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes.DRAG_START;
import static eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes.FIND_VALUE_IN_SOURCELIST;
import static eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes.VALUE_FOUND_IN_SOURCELIST;
import static eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes.VALUE_NOT_FOUND_IN_SOURCELIST;

import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SimpleSourceListItemBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventHandler;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public class SourceListViewImpl extends Composite implements SourceListView, DragDropEventHandler {

	private static SourceListViewImplUiBinder uiBinder = GWT.create(SourceListViewImplUiBinder.class);

	interface SourceListViewImplUiBinder extends UiBinder<Widget, SourceListViewImpl> {
	}

	@Inject
	private EventsBus eventsBus;

	@Inject
	private PageScopeFactory pageScopeFactory;

	@Inject
	private DragDropHelper dragDropHelper;

	@Inject
	private OverlayTypesParser overlayTypesParser;

	@Inject
	private StyleNameConstants styleNames;

	@UiField
	FlowPanel items;

	private SourceListBean bean;

	private IModule parentModule;

	private final BiMap<SourceListViewItem, String> itemsCollection = HashBiMap.create();
	private final BiMap<SourceListViewItem, String> hiddenItems = HashBiMap.create();

	@Override
	public void setBean(SourceListBean bean) {
		this.bean = bean;
	}

	@Override
	public void setIModule(IModule module) {
		this.parentModule = module;
	}

	private String getUniqId() {
		return "#" + Math.random() + "#" + System.currentTimeMillis() + "#" + DOM.createUniqueId();
	}

	private SourceListViewItem getItem(DragDataObject dragDataObject) {
		SourceListViewItem item = new SourceListViewItem(dragDropHelper, dragDataObject, parentModule, styleNames);
		item.setSourceListView(this);
		item.createAndBindUi();
		return item;
	}

	private DragDataObject createDragDataObject() {
		return (NativeDragDataObject) overlayTypesParser.get();
	}

	@Override
	public void createAndBindUi() {
		initWidget(uiBinder.createAndBindUi(this));
		List<SimpleSourceListItemBean> simpleSourceListItemBeans = bean.getSimpleSourceListItemBeans();
		for (final SimpleSourceListItemBean simpleSourceListItemBean : simpleSourceListItemBeans) {
			DragDataObject obj = createDragDataObject();
			obj.setValue(simpleSourceListItemBean.getValue());
			obj.setSourceId(getUniqId());
			SourceListViewItem item = getItem(obj);
			items.add(item);
			itemsCollection.put(item, obj.getValue());
		}

		eventsBus.addHandlerToSource(DragDropEvent.getType(DRAG_END), parentModule, this, pageScopeFactory.getCurrentPageScope());
		eventsBus.addHandlerToSource(DragDropEvent.getType(FIND_VALUE_IN_SOURCELIST), parentModule, this, pageScopeFactory.getCurrentPageScope());

	}

	private void disableItems(boolean disabled) {
		for (SourceListViewItem item : itemsCollection.keySet()) {
			item.setDisableDrag(disabled);
		}
	}

	public void onMaybeDragCanceled() {
		disableItems(false);
	}

	public void onItemDragStarted(DragDataObject dragDataObject, DragStartEvent startEvent, SourceListViewItem item) {
		disableItems(true);
		DragDropEvent event = new DragDropEvent(DRAG_START, this);
		event.setDragDataObject(dragDataObject);
		event.setIModule(parentModule);
		eventsBus.fireEventFromSource(event, parentModule, pageScopeFactory.getCurrentPageScope());
	}

	private void fireValueEvent(DragDropEventTypes type, DragDataObject data) {
		DragDropEvent event = new DragDropEvent(type, parentModule);
		event.setIModule(parentModule);
		event.setDragDataObject(data);
		eventsBus.fireEvent(event, pageScopeFactory.getCurrentPageScope());
	}

	private void checkSourceList(DragDataObject dragDataObject) {
		disableItems(false);
		if (hiddenItems.containsValue(dragDataObject.getPreviousValue())) {
			BiMap<String, SourceListViewItem> inverse = hiddenItems.inverse();
			SourceListViewItem sourceListViewItem = inverse.get(dragDataObject.getPreviousValue());
			sourceListViewItem.show();
			inverse.remove(dragDataObject.getPreviousValue());
		}
		if (itemsCollection.containsValue(dragDataObject.getValue())) {
			SourceListViewItem sourceListViewItem = itemsCollection.inverse().get(dragDataObject.getValue());
			sourceListViewItem.hide();
			hiddenItems.put(sourceListViewItem, dragDataObject.getValue());
		}
	}

	private void searchItem(DragDataObject dragDataObject) {
		if (hiddenItems.containsValue(dragDataObject.getValue())) {
			fireValueEvent(VALUE_NOT_FOUND_IN_SOURCELIST, dragDataObject);
		} else if (itemsCollection.containsValue(dragDataObject.getValue())) {
			fireValueEvent(VALUE_FOUND_IN_SOURCELIST, dragDataObject);
		} else {
			fireValueEvent(VALUE_NOT_FOUND_IN_SOURCELIST, dragDataObject);
		}
	}

	@Override
	public void onDragEvent(DragDropEvent event) {
		if (event.getType() == DRAG_END) {
			checkSourceList(event.getDragDataObject());
		} else if (event.getType() == FIND_VALUE_IN_SOURCELIST) {
			searchItem(event.getDragDataObject());
		}

	}

}
