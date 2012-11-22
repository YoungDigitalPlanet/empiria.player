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
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DraggableObject;
import eu.ydp.gwtutil.client.debug.logger.Debug;


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

	private final IModule parentModule;

	public SourceListViewItem(DragDropHelper dragDropHelper, DragDataObject dragDataObject,IModule parentModule) {
		super();
		this.dragDropHelper = dragDropHelper;
		this.dragDataObject = dragDataObject;
		this.parentModule = parentModule;
	}



	public void createAndBindUi() {
		initWidget(uiBinder.createAndBindUi(this));
		Label label = new Label(dragDataObject.getValue());
		FlowPanel item2 = new FlowPanel();
		item2.add(label);
		item2.getElement().getStyle().setWidth(100, Unit.PX);
		item2.getElement().getStyle().setHeight(50, Unit.PX);
		item2.getElement().getStyle().setMargin(10, Unit.PX);
		item2.getElement().getStyle().setBackgroundColor("blue");
		draggable = dragDropHelper.enableDragForWidget(item2,parentModule);
		item.add(draggable.getDraggableWidget());

		draggable.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
//				Element clone = DOM.clone(getElement(),true);
//				Document.get().getBody().appendChild(clone);
//				HTMLPanel cloncontainer = HTMLPanel.wrap(clone);
//				FlowPanel panel = new FlowPanel();
//				panel.add(cloncontainer);
//				Element element = panel.getElement();
//				element.getStyle().setZIndex(100);
//				element.getStyle().setPosition(Position.ABSOLUTE);
//				element.getStyle().setLeft(-50, Unit.PX);
//				RootPanel.get().add(panel);
				event.getDataTransfer().setDragImage(getElement(), 0, 0);
			//	event.setData("text", dragDataObject.getValue());
				Debug.log("on drag start draggable");
				event.setData("json", dragDataObject.toJSON());
				// FIXME gdzie obslugiwac zaradzaniem widkokiem przenoszonego widgetu moze tu tylko ustawiwnie wielkosci dla parent?
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

}
