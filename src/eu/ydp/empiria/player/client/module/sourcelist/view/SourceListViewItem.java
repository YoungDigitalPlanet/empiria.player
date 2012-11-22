package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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

	public SourceListViewItem(DragDropHelper dragDropHelper, DragDataObject dragDataObject, IModule parentModule, StyleNameConstants styleNames) {
		super();
		this.dragDropHelper = dragDropHelper;
		this.dragDataObject = dragDataObject;
		this.parentModule = parentModule;
		this.styleNames = styleNames;
	}

	public void createAndBindUi() {
		initWidget(uiBinder.createAndBindUi(this));
		Label label = new Label(dragDataObject.getValue());
		container = new FlowPanel();
		container.addStyleName(styleNames.QP_DRAG_ITEM());
		container.add(label);
		container.getElement().getStyle().setWidth(100, Unit.PX);
		container.getElement().getStyle().setHeight(50, Unit.PX);
		container.getElement().getStyle().setMargin(10, Unit.PX);
		container.getElement().getStyle().setBackgroundColor("blue");
		draggable = dragDropHelper.enableDragForWidget(container, parentModule);
		item.add(draggable.getDraggableWidget());

		draggable.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.getDataTransfer().setDragImage(getElement(), 0, 0);
				event.setData("json", dragDataObject.toJSON());
				// FIXME gdzie obslugiwac zaradzaniem widkokiem przenoszonego
				// widgetu moze tu tylko ustawiwnie wielkosci dla parent?
				sourceListView.onItemDragStarted(dragDataObject, event, SourceListViewItem.this);
			}
		});
		draggable.addDragEndHandler(new DragEndHandler() {

			@Override
			public void onDragEnd(DragEndEvent event) {
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
