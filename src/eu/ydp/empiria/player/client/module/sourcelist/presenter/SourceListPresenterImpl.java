package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SimpleSourceListItemBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public class SourceListPresenterImpl implements SourceListPresenter {

	@Inject private SourceListView view;
	@Inject private SourcelistManager sourcelistManager;
	@Inject private OverlayTypesParser overlayTypesParser;

	private SourceListBean bean;
	private String moduleId;

	@Override
	public void setBean(SourceListBean bean) {
		this.bean = bean;
	}

	@Override
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void createAndBindUi() {
		view.createAndBindUi();
		List<SimpleSourceListItemBean> simpleSourceListItemBeans = bean.getSimpleSourceListItemBeans();
		for (final SimpleSourceListItemBean simpleSourceListItemBean : simpleSourceListItemBeans) {
			view.createItem(simpleSourceListItemBean.getAlt(), simpleSourceListItemBean.getValue());
		}
		view.setSourceListPresenter(this);
	}

	@Override
	public DragDataObject getDragDataObject(String itemId) {
		DragDataObject dataObject = (NativeDragDataObject) overlayTypesParser.get();
		dataObject.setItemId(itemId);
		dataObject.setSourceId(moduleId);
		return dataObject;
	}

	@Override
	public String getItemValue(String itemId) {
		return view.getItemValue(itemId);
	}

	@Override
	public void useItem(String itemId) {
		view.hideItem(itemId);
	}

	@Override
	public void restockItem(String itemId) {
		view.showItem(itemId);
	}

	@Override
	public void onDragEvent(DragDropEventTypes eventType, String itemId) {
		switch (eventType) {
		case DRAG_START:
			sourcelistManager.dragStart(itemId);
			break;
		case DRAG_END:
			sourcelistManager.dragEndSourcelist(itemId, moduleId);
			break;
		case DRAG_CANCELL:
			sourcelistManager.dragCanceled();
		default:
			break;
		}
	}

	private List<String> getAllItemsId(){
		return Lists.transform(bean.getSimpleSourceListItemBeans(), new Function<SimpleSourceListItemBean, String>() {
			@Override
			public String apply(SimpleSourceListItemBean bean) {
				return bean.getAlt();
			}
		});
	}

	@Override
	public void useAndRestockItems(List<String> itemsIds) {
		restockAllItems();
		useItems(itemsIds);
	}

	private void useItems(List<String> itemsIds) {
		for(String itemId: itemsIds){
			useItem(itemId);
		}
	}

	private void restockAllItems() {
		List<String> allItemsId = getAllItemsId();
		for(String itemId:allItemsId){
			restockItem(itemId);
		}
	}

	@Override
	public void onDropEvent(DragDropEventTypes eventType, String itemId) {
		// TODO Auto-generated method stub
		
	}

}
