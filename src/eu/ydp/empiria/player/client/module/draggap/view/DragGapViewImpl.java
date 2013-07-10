package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.common.base.Optional;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.ui.drop.FlowPanelWithDropZone;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DraggableObject;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

public class DragGapViewImpl implements DragGapView {

	private static DragGapViewUiBinder uiBinder = GWT.create(DragGapViewUiBinder.class);

	interface DragGapViewUiBinder extends UiBinder<Widget, DragGapViewImpl> {
	}

	@UiField FlowPanelWithDropZone container;
	FlowPanel itemWrapper;
	private @Inject TouchController touchController;
	private final DragDropHelper dragDropHelper;
	private final StyleNameConstants styleNameConstants;
	private final DragGapStylesProvider dragGapStylesProvider;

	private Widget contentWidget;
	private Optional<DraggableObject<FlowPanel>> optionalDraggable = Optional.absent();
	private Optional<DragGapStartDragHandler> dragStartHandlerOptional = Optional.absent();
	private DragEndHandler dragEndHandler;

	@Inject
	public DragGapViewImpl(DragDropHelper dragDropHelper, StyleNameConstants styleNameConstants, DragGapStylesProvider dragGapStylesProvider) {
		this.dragDropHelper = dragDropHelper;
		this.styleNameConstants = styleNameConstants;
		this.dragGapStylesProvider = dragGapStylesProvider;

		uiBinder.createAndBindUi(this);
		container.setStyleName(styleNameConstants.QP_DRAG_GAP_DEFAULT());
	}

	@Override
	public Widget asWidget() {
		return container;
	}

	@Override
	public void setContent(String content) {
		removeContent();
		DraggableObject<FlowPanel> draggableObject = createDraggableObjectWithContent(content);
		addDragHandlersToItem(draggableObject);
	}

	private DraggableObject<FlowPanel> createDraggableObjectWithContent(String content) {
		contentWidget = new HTMLPanel(content);
		itemWrapper = new FlowPanel();
		itemWrapper.add(contentWidget);

		DraggableObject<FlowPanel> draggableObject = dragDropHelper.enableDragForWidget(itemWrapper);
		Widget draggableWidget = draggableObject.getDraggableWidget();

		container.add(draggableWidget);
		optionalDraggable = Optional.of(draggableObject);
		return draggableObject;
	}

	private void addDragHandlersToItem(DraggableObject<FlowPanel> draggableObject) {
		addDragStartHandlerToItem(draggableObject);
		addDragEndHandlerToItem(draggableObject);
	}

	private void addDragEndHandlerToItem(DraggableObject<FlowPanel> draggableObject) {
		draggableObject.addDragEndHandler(new DragEndHandler() {

			@Override
			public void onDragEnd(DragEndEvent event) {
				removeDraggableStyleFromItem();
				dragEndHandler.onDragEnd(event);
			}
		});
	}

	private void addDragStartHandlerToItem(DraggableObject<FlowPanel> draggableObject) {
		draggableObject.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				touchController.setTouchReservation(true);
				event.getDataTransfer().setDragImage(itemWrapper.getElement(), 0, 0);
				dragStartHandlerOptional.get().onDragStart(event);
			}
		});
	}

	@Override
	public void setDragEndHandler(final DragEndHandler dragEndHandler) {
		this.dragEndHandler = dragEndHandler;
	}

	private void removeDraggableStyleFromItem() {
		if (itemWrapper != null) {
			itemWrapper.getElement().removeClassName(styleNameConstants.QP_DRAGGED_DRAG());
		}
	}

	@Override
	public void removeContent() {
		container.clear();
		optionalDraggable = Optional.absent();
	}

	@Override
	public void updateStyle(UserAnswerType answerType) {
		String gapStyleName = dragGapStylesProvider.getCorrectGapStyleName(answerType);
		container.setStyleName(gapStyleName);
	}

	@Override
	public void lock(boolean lock) {
		if (lock) {
			container.addStyleName(styleNameConstants.QP_DRAG_GAP_LOCKED());
		} else {
			container.removeStyleName(styleNameConstants.QP_DRAG_GAP_LOCKED());
		}
	}

	@Override
	public void setDragDisabled(boolean disabled) {
		if (optionalDraggable.isPresent()) {
			DraggableObject<?> draggableObject = optionalDraggable.get();
			draggableObject.setDisableDrag(disabled);
		}
	}

	@Override
	public void setDropHandler(DragGapDropHandler dragGapDropHandler) {
	}

	@Override
	public void setDragStartHandler(DragGapStartDragHandler dragGapStartDragHandler) {
		dragStartHandlerOptional = Optional.fromNullable(dragGapStartDragHandler);
	}

	@Override
	public DroppableWidget<FlowPanel> getDropZoneWidget() {
		return container;
	}
}
