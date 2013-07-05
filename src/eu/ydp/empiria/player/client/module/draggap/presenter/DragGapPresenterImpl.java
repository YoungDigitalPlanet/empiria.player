package eu.ydp.empiria.player.client.module.draggap.presenter;

import java.util.List;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapDropHandler;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapStartDragHandler;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public class DragGapPresenterImpl implements DragGapPresenter {

	@Inject	@ModuleScoped private DragGapModuleModel model;
	@Inject	@PageScoped	private AnswerEvaluationSupplier answerEvaluationSupplier;
	@Inject	private DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor;
	private final DragGapView view;
	private final DroppableObject<?> droppable;
	private final DropZoneGuardian dropZoneGuardian;
	private DragGapDropHandler dragGapDropHandler;

	@Inject
	public DragGapPresenterImpl(DragDropHelper dragDropHelper, StyleNameConstants styleNameConstants, DragGapView view) {
		this.view = view;
		droppable = dragDropHelper.enableDropForWidget(view.getDropZoneWidget());
		dropZoneGuardian = new DropZoneGuardian(droppable, droppable.getDroppableWidget(), styleNameConstants);
		addDomHandlerOnObjectDrop();
	}

	private void addDomHandlerOnObjectDrop() {
		droppable.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				extractObjectFromEventAndCallHandler(event);
			}
		});
	}

	private void extractObjectFromEventAndCallHandler(DropEvent event) {
		Optional<DragDataObject> objectFromEvent = dragDataObjectFromEventExtractor.extractDroppedObjectFromEvent(event);
		if (objectFromEvent.isPresent()) {
			dragGapDropHandler.onDrop(objectFromEvent.get());
		}
	}

	@Override
	public void setDragEndHandler(DragEndHandler dragEndHandler) {
		view.setDragEndHandler(dragEndHandler);
	}

	@Override
	public void bindView() {
		view.updateStyle(UserAnswerType.DEFAULT);
	}

	@Override
	public void reset() {
		view.removeContent();
		model.reset();
		view.updateStyle(UserAnswerType.DEFAULT);
	}

	@Deprecated
	@Override
	public void setModel(DragGapModuleModel model) {
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
	}

	@Override
	public void setBean(DragGapBean bean) {
	}

	@Override
	public void setLocked(boolean locked) {
		view.lock(locked);
		view.setDragDisabled(locked);
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		if (mode == MarkAnswersMode.MARK) {
			markAnswers(type);
		} else if (mode == MarkAnswersMode.UNMARK) {
			view.updateStyle(UserAnswerType.DEFAULT);
		}
	}

	private void markAnswers(MarkAnswersType type) {
		List<Boolean> evaluatedAnswers = answerEvaluationSupplier.evaluateAnswer(model.getResponse());
		if (evaluatedAnswers.isEmpty()) {
			view.updateStyle(UserAnswerType.NONE);
		} else {
			Boolean isAnswerCorrect = evaluatedAnswers.get(0);
			if (type == MarkAnswersType.CORRECT && isAnswerCorrect) {
				view.updateStyle(UserAnswerType.CORRECT);
			} else if (type == MarkAnswersType.WRONG && !isAnswerCorrect) {
				view.updateStyle(UserAnswerType.WRONG);
			}
		}
	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		List<String> answers;
		if (mode == ShowAnswersType.CORRECT) {
			answers = model.getCorrectAnswers();
		} else if (mode == ShowAnswersType.USER) {
			answers = model.getCurrentAnswers();
		} else {
			return;
		}

		if (answers.size() > 0) {
			String answerToSet = answers.get(0);
			view.setContent(answerToSet);
		} else {
			view.removeContent();
		}
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void setContent(String itemContent) {
		view.setContent(itemContent);
		model.addAnswer(itemContent);
	}

	@Override
	public void removeContent() {
		view.removeContent();
		model.reset();
	}

	@Override
	public void lockDropZone() {
		dropZoneGuardian.lockDropZone();
	}

	@Override
	public void unlockDropZone() {
		dropZoneGuardian.unlockDropZone();
	}

	@Override
	public void setDragStartHandler(DragGapStartDragHandler dragGapStartDragHandler) {
		view.setDragStartHandler(dragGapStartDragHandler);
	}

	@Override
	public void setDropHandler(DragGapDropHandler dragGapDropHandler) {
		this.dragGapDropHandler = dragGapDropHandler;
	}
}
