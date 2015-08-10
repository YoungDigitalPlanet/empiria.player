package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.predicates.ComplexTextPredicate;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SimpleSourceListItemBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.empiria.player.client.util.events.internal.dragdrop.DragDropEventTypes;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import eu.ydp.gwtutil.client.util.geom.Size;

import java.util.List;

public class SourceListPresenterImpl implements SourceListPresenter {

    @Inject
    private SourceListView view;
    @Inject
    @PageScoped
    private SourcelistManager sourcelistManager;
    @Inject
    private OverlayTypesParser overlayTypesParser;
    @Inject
    private ComplexTextPredicate complexTextChecker;

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
    public void createAndBindUi(InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        view.setSourceListPresenter(this);
        view.createAndBindUi();
        List<SimpleSourceListItemBean> simpleSourceListItemBeans = bean.getSimpleSourceListItemBeans();
        for (final SimpleSourceListItemBean simpleSourceListItemBean : simpleSourceListItemBeans) {
            SourcelistItemValue itemValue = simpleSourceListItemBean.getItemValue(complexTextChecker);
            view.createItem(itemValue, inlineBodyGeneratorSocket);
        }
    }

    @Override
    public DragDataObject getDragDataObject(String itemId) {
        DragDataObject dataObject = overlayTypesParser.<NativeDragDataObject>get();
        dataObject.setItemId(itemId);
        dataObject.setSourceId(moduleId);
        return dataObject;
    }

    @Override
    public SourcelistItemValue getItemValue(String itemId) {
        return view.getItemValue(itemId);
    }

    @Override
    public void useItem(String itemId) {
        if (bean.isMoveElements()) {
            view.hideItem(itemId);
        }
    }

    @Override
    public void restockItem(String itemId) {
        view.showItem(itemId);
    }

    @Override
    public void onDragEvent(DragDropEventTypes eventType, String itemId) {
        switch (eventType) {
            case DRAG_START:
                sourcelistManager.dragStart(moduleId);
                break;
            case DRAG_END:
                sourcelistManager.dragFinished();
            default:
                break;
        }
    }

    private List<String> getAllItemsId() {
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
        for (String itemId : itemsIds) {
            useItem(itemId);
        }
    }

    private void restockAllItems() {
        List<String> allItemsId = getAllItemsId();
        for (String itemId : allItemsId) {
            restockItem(itemId);
        }
    }

    @Override
    public void onDropEvent(String itemId, String sourceModuleId) {
        sourcelistManager.dragEndSourcelist(itemId, sourceModuleId);
    }

    @Override
    public void lockSourceList() {
        view.lockForDragDrop();
        for (String itemId : getAllItemsId()) {
            view.lockItemForDragDrop(itemId);
        }

    }

    @Override
    public void unlockSourceList() {
        view.unlockForDragDrop();
        for (String itemId : getAllItemsId()) {
            view.unlockItemForDragDrop(itemId);
        }
    }

    @Override
    public HasDimensions getMaxItemSize() {
        HasDimensions maxItemSize = view.getMaxItemSize();
        int width = Math.max(maxItemSize.getWidth(), bean.getImagesWidth());
        int height = Math.max(maxItemSize.getHeight(), bean.getImagesHeight());
        return new Size(width, height);
    }

}
