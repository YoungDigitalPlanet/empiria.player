package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.draggap.DragGapStyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DraggableObject;
import eu.ydp.empiria.player.client.util.events.internal.dragdrop.DragDropEventTypes;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class SourceListViewItem extends Composite implements LockUnlockDragDrop {

    private static SourceListViewItemUiBinder uiBinder = GWT.create(SourceListViewItemUiBinder.class);

    interface SourceListViewItemUiBinder extends UiBinder<Widget, SourceListViewItem> {
    }

    protected
    @UiField
    FlowPanel item;
    private
    @Inject
    DragGapStyleNameConstants styleNames;
    private
    @Inject
    DragDropHelper dragDropHelper;
    private
    @Inject
    TouchController touchController;
    private
    @Inject
    Provider<SourceListViewItemWidget> sourceListViewItemWidgetProvider;
    private
    @Inject
    UserInteractionHandlerFactory interactionHandlerFactory;
    private
    @Inject
    ScormScrollPanel scormScrollPanel;
    private SourceListViewImpl sourceListView;
    private DraggableObject<SourceListViewItemWidget> draggable;
    private SourceListViewItemWidget container;

    private SourcelistItemValue itemContent;

    private final Command disableTextMark = new DisableDefaultBehaviorCommand();

    public void setSourceListView(SourceListViewImpl sourceListView) {
        this.sourceListView = sourceListView;
    }

    public void setDisableDrag(boolean disableDrag) {
        draggable.setDisableDrag(disableDrag);
    }

    public void show() {
        container.show();
    }

    public void hide() {
        container.hide();
    }

    public void createAndBindUi(SourcelistItemValue itemValue, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        this.itemContent = itemValue;
        initWidget(uiBinder.createAndBindUi(this));
        container = getDraggableWidget(itemValue, inlineBodyGeneratorSocket);
        draggable = dragDropHelper.enableDragForWidget(container);
        item.add(draggable.getDraggableWidget());
        addDragHandlers();
    }

    private SourceListViewItemWidget getDraggableWidget(SourcelistItemValue itemValue, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        SourceListViewItemWidget itemWidget = sourceListViewItemWidgetProvider.get();
        itemWidget.initView(itemValue.getType(), itemValue.getContent(), styleNames.QP_DRAG_ITEM(), inlineBodyGeneratorSocket);
        EventHandlerProxy userOverHandler = interactionHandlerFactory.createUserOverHandler(disableTextMark);
        userOverHandler.apply(itemWidget);
        return itemWidget;
    }

    private void addDragHandlers() {
        addDragStartHandler();
        addDragEndHandler();
    }

    private void addDragStartHandler() {
        draggable.addDragStartHandler(new DragStartHandler() {
            @Override
            public void onDragStart(DragStartEvent event) {
                scormScrollPanel.lockScroll();
                touchController.setTouchReservation(true);
                getElement().addClassName(styleNames.QP_DRAGGED_DRAG());
                event.getDataTransfer().setDragImage(getElement(), 0, 0);
                sourceListView.onDragEvent(DragDropEventTypes.DRAG_START, SourceListViewItem.this, event);
            }
        });
    }

    private void addDragEndHandler() {
        draggable.addDragEndHandler(new DragEndHandler() {
            @Override
            public void onDragEnd(DragEndEvent event) {
                scormScrollPanel.unlockScroll();
                getElement().removeClassName(styleNames.QP_DRAGGED_DRAG());
                sourceListView.onDragEvent(DragDropEventTypes.DRAG_END, SourceListViewItem.this, event);
            }
        });
    }

    public SourcelistItemValue getItemContent() {
        return itemContent;
    }

    @Override
    public void lockForDragDrop() {
        draggable.setDisableDrag(true);

    }

    @Override
    public void unlockForDragDrop() {
        draggable.setDisableDrag(false);
    }

    public int getWidth() {
        return container.getWidth();
    }

    public int getHeight() {
        return container.getHeight();
    }
}
