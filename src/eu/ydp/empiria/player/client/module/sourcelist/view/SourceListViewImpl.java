package eu.ydp.empiria.player.client.module.sourcelist.view;

import static eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes.DRAG_END;
import static eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes.DRAG_START;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventHandler;
import eu.ydp.gwtutil.client.debug.logger.Debug;

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

	@UiField
	FlowPanel items;

	private SourceListBean bean;

	private IModule parentModule;

	private final Set<SourceListViewItem> itemsCollection = new HashSet<SourceListViewItem>();

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
		SourceListViewItem item = new SourceListViewItem(dragDropHelper, dragDataObject, parentModule);
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
			Debug.log(obj.toJSON());
			SourceListViewItem item = getItem(obj);
			items.add(item);
			itemsCollection.add(item);
		}

		eventsBus.addHandler(DragDropEvent.getType(DRAG_END), this, pageScopeFactory.getCurrentPageScope());
	}

	private void disableItems(boolean disabled) {
		for (SourceListViewItem item : itemsCollection) {
			item.setDisableDrag(disabled);
		}
	}

	public void onMaybeDragCanceled(){
		disableItems(false);
	}

	public void onItemDragStarted(DragDataObject dragDataObject, DragStartEvent startEvent, SourceListViewItem item) {
		disableItems(true);
		DragDropEvent event = new DragDropEvent(DRAG_START, this);
		event.setDragDataObject(dragDataObject);
		event.setIModule(parentModule);
		eventsBus.fireEventFromSource(event, parentModule, pageScopeFactory.getCurrentPageScope());
	}

	@Override
	public void onDragEvent(DragDropEvent event) {
		if (event.getType() == DRAG_END) {
			disableItems(false);
			
		//	Debug.log(event.getDragDataObject().toJSON());
		//	Debug.log(event.getDragDataObject().getSourceId());
		// FIXME gdzie obsluzyc wyjeceie lelementu mamy sourceid ?
		}

	}

}
