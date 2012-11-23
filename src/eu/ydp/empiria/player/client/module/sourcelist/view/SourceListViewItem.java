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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DraggableObject;

public class SourceListViewItem extends Composite {

	private static SourceListViewItemUiBinder uiBinder = GWT.create(SourceListViewItemUiBinder.class);

	interface SourceListViewItemUiBinder extends UiBinder<Widget, SourceListViewItem> {
	}

	@UiField
	protected FlowPanel item;

	DragDropHelper dragDropHelper;

	private final DragDataObject dragDataObject;

	private SourceListViewImpl sourceListView;
	DraggableObject<FlowPanel> draggable;

	FlowPanel container;
	private final IModule parentModule;

	private final StyleNameConstants styleNames;

	protected HandlerRegistration addTouchHandler;
	protected boolean dragRelease = false;

	public SourceListViewItem(DragDropHelper dragDropHelper, DragDataObject dragDataObject, IModule parentModule, StyleNameConstants styleNames) {
		super();
		this.dragDropHelper = dragDropHelper;
		this.dragDataObject = dragDataObject;
		this.parentModule = parentModule;
		this.styleNames = styleNames;
	}

	private void setSize() {
		setWidth(item.getOffsetWidth() + "px");
		setHeight(item.getOffsetHeight() + "px");
		container.setHeight(item.getOffsetHeight() + "px");
		container.setWidth(item.getOffsetWidth() + "px");
	}

	public void createAndBindUi() {
		initWidget(uiBinder.createAndBindUi(this));
		Label label = new Label(dragDataObject.getValue());
		container = new FlowPanel();
		container.addStyleName(styleNames.QP_DRAG_ITEM());
		container.add(label);
		draggable = dragDropHelper.enableDragForWidget(container, parentModule);
		item.add(draggable.getDraggableWidget());
		draggable.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				setSize();
				dragRelease = false;
				getElement().addClassName(styleNames.QP_DRAGGED_DRAG());
				event.getDataTransfer().setDragImage(getElement(), 0, 0);
				event.setData("json", dragDataObject.toJSON());
				sourceListView.onItemDragStarted(dragDataObject, event, SourceListViewItem.this);

			}
		});
		draggable.addDragEndHandler(new DragEndHandler() {
			@Override
			public void onDragEnd(DragEndEvent event) {
				getElement().removeClassName(styleNames.QP_DRAGGED_DRAG());
				sourceListView.onMaybeDragCanceled();
			}
		});
	}

	public void setSourceListView(SourceListViewImpl sourceListView) {
		this.sourceListView = sourceListView;
	}

	public void setDisableDrag(boolean disableDrag) {
		draggable.setDisableDrag(disableDrag);
	}

	public void show() {
		container.setVisible(true);
	}

	public void hide() {
		container.setVisible(false);
	}
}
