package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.common.base.Optional;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DraggableObject;

public class DragGapViewImpl implements DragGapView {

	private static DragGapViewUiBinder uiBinder = GWT.create(DragGapViewUiBinder.class);

	interface DragGapViewUiBinder extends UiBinder<Widget, DragGapViewImpl> {
	}

	@UiField
	FlowPanel container;

	private final DragDropHelper dragDropHelper;
	private final StyleNameConstants styleNameConstants;
	private final DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor;

	private Widget contentWidget;
	private Optional<DraggableObject<Widget>> optionalDraggable = Optional.absent();
	private Optional<DragGapDropHandler> dragGapDropHandlerOptional = Optional.absent();
	private Optional<DragGapStartDragHandler> dragStartHandlerOptional = Optional.absent();


	@Inject
	public DragGapViewImpl(DragDropHelper dragDropHelper, StyleNameConstants styleNameConstants, DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor) {
		this.dragDropHelper = dragDropHelper;
		this.styleNameConstants = styleNameConstants;
		this.dragDataObjectFromEventExtractor = dragDataObjectFromEventExtractor;

		uiBinder.createAndBindUi(this);
		addDomHandlerOnObjectDrop();
		addDomHandlerOnDragStart();
		container.setStyleName(styleNameConstants.QP_DRAG_GAP_DEFAULT());
	}

	@Override
	public Widget asWidget() {
		return container;
	}

	@Override
	public void setContent(String content) {
		contentWidget = new HTMLPanel(content);
		container.add(contentWidget);
		DraggableObject<Widget> draggableObject = dragDropHelper.enableDragForWidget(contentWidget);
		optionalDraggable = Optional.of(draggableObject);
	}

	@Override
	public void removeContent() {
		container.remove(contentWidget);
		optionalDraggable = Optional.absent();
	}

	@Override
	public void updateStyle(UserAnswerType answerType) {
		switch (answerType) {
		case CORRECT:
			container.setStyleName(styleNameConstants.QP_DRAG_GAP_CORRECT());
			break;
		case WRONG:
			container.setStyleName(styleNameConstants.QP_DRAG_GAP_WRONG());
			break;
		case DEFAULT:
			container.setStyleName(styleNameConstants.QP_DRAG_GAP_DEFAULT());
			break;
		case NONE:
			container.setStyleName(styleNameConstants.QP_DRAG_GAP_NONE());
			break;
		default:
			break;
		}
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
			DraggableObject<Widget> draggableObject = optionalDraggable.get();
			draggableObject.setDisableDrag(disabled);
		}
	}

	@Override
	public void setDropHandler(DragGapDropHandler dragGapDropHandler) {
		this.dragGapDropHandlerOptional = Optional.fromNullable(dragGapDropHandler);
	}

	private void addDomHandlerOnObjectDrop() {
		container.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				if (dragGapDropHandlerOptional.isPresent()) {
					extractObjectFromEventAndCallHandler(event);
				}
			}

		}, DropEvent.getType());
	}

	private void extractObjectFromEventAndCallHandler(DropEvent event) {
		Optional<DragDataObject> objectFromEvent = dragDataObjectFromEventExtractor.extractDroppedObjectFromEvent(event);
		if (objectFromEvent.isPresent()) {
			DragGapDropHandler dragGapDropHandler = dragGapDropHandlerOptional.get();
			dragGapDropHandler.onDrop(objectFromEvent.get());
		}
	}

	@Override
	public void setDragStartHandler(DragGapStartDragHandler dragGapStartDragHandler) {
		dragStartHandlerOptional = Optional.fromNullable(dragGapStartDragHandler);
	}

	private void addDomHandlerOnDragStart() {
		container.addDomHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				if(dragStartHandlerOptional.isPresent()){
					dragStartHandlerOptional.get().onDragStart();
				}
			}
		}, DragStartEvent.getType());
	}
}
