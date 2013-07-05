package eu.ydp.empiria.player.client.module.draggap.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.event.dom.client.DragEndHandler;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapStartDragHandler;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;

@RunWith(MockitoJUnitRunner.class)
public class DragGapPresenterImplJUnitTest extends AbstractTestBase {

	private DragGapPresenterImpl presenter;
	@Mock
	private DragDropHelper dragDropHelper;
	@Mock
	private StyleNameConstants styleNameConstants;
	@Mock
	private DragGapView view;
	@Mock
	private DragGapModuleModel model;
	@Mock
	private ModuleSocket socket;
	@Mock
	private AnswerEvaluationSupplier answerEvaluationSupplier;
	@Mock
	private DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor;

	private DragGapBean bean;

	List<Boolean> evaluatedAnswers;

	@Before
	public void setUp() {
		presenter = new DragGapPresenterImpl(dragDropHelper, styleNameConstants, view, model, answerEvaluationSupplier, dragDataObjectFromEventExtractor);
	}

	@Test
	public void shouldSetDragStartHandler() {
		// given
		DragGapStartDragHandler handler = mock(DragGapStartDragHandler.class);

		// when
		presenter.setDragStartHandler(handler);

		// then
		verify(view).setDragStartHandler(handler);
	}

	@Test
	public void shouldSetDragEndHandler() {
		// given
		DragEndHandler dragEndHandler = mock(DragEndHandler.class);

		// when
		presenter.setDragEndHandler(dragEndHandler);

		// then
		verify(view).setDragEndHandler(dragEndHandler);
	}

	@Test
	public void shouldBindView() {
		// when
		presenter.bindView();

		// then
		verify(view).updateStyle(UserAnswerType.DEFAULT);
	}

	@Test
	public void shouldReset() {
		// when
		presenter.reset();

		// then
		InOrder inOrder = Mockito.inOrder(view, model);
		inOrder.verify(view).removeContent();
		inOrder.verify(model).reset();
		inOrder.verify(view).updateStyle(UserAnswerType.DEFAULT);
	}

	@Test
	public void shouldSetContent() {
		// given
		String itemContent = "content";

		// when
		presenter.setContent(itemContent);

		// then
		InOrder inOrder = Mockito.inOrder(view, model);
		inOrder.verify(view).setContent(itemContent);
		inOrder.verify(model).addAnswer(itemContent);
	}

	@Test
	public void shouldRemoveContent() {
		// when
		presenter.removeContent();

		// then
		InOrder inOrder = Mockito.inOrder(view, model);
		inOrder.verify(view).removeContent();
		inOrder.verify(model).reset();
	}

	@Test
	public void shouldMarkAnswers_Unmark() {
		// when
		presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK);

		// then
		verify(view).updateStyle(UserAnswerType.DEFAULT);
	}

	@Test
	public void shouldMarkAnswers_MarkCorrectAndEmptyAnswers() {
		// given
		mockAnswers(true);

		// when
		presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

		// then
		verify(view).updateStyle(UserAnswerType.NONE);
	}

	@Test
	public void shouldMarkAnswers_MarkCorrectAndCorrectAnswer() {
		// given
		mockAnswers(false);
		when(evaluatedAnswers.get(0)).thenReturn(true);

		// when
		presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

		// then
		verify(view).updateStyle(UserAnswerType.CORRECT);
	}

	@Test
	public void shouldMarkAnswers_MarkCorrectAndIncorrectAnswer() {
		// given
		mockAnswers(false);
		when(evaluatedAnswers.get(0)).thenReturn(false);

		// when
		presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

		// then
		verifyNoMoreInteractions(view);
	}

	@Test
	public void shouldMarkAnswers_MarkWrongAndCorrectAnswer() {
		// given
		mockAnswers(false);
		when(evaluatedAnswers.get(0)).thenReturn(true);

		// when
		presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);

		// then
		verifyNoMoreInteractions(view);
	}

	@Test
	public void shouldMarkAnswers_MarkWrongAndIncorrectAnswer() {
		// given
		mockAnswers(false);
		when(evaluatedAnswers.get(0)).thenReturn(false);

		// when
		presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);

		// then
		verify(view).updateStyle(UserAnswerType.WRONG);
	}

	private void mockAnswers(boolean answersAreEmpty) {
		Response response = mock(Response.class);
		when(model.getResponse()).thenReturn(response);
		evaluatedAnswers = mock(List.class);
		when(evaluatedAnswers.isEmpty()).thenReturn(answersAreEmpty);
		when(answerEvaluationSupplier.evaluateAnswer(response)).thenReturn(evaluatedAnswers);
	}

	@Test
	public void shouldShowAnswers_CorrectAndEmptyAnswers() {
		// given
		List<String> answers = mock(List.class);
		when(answers.size()).thenReturn(0);
		when(model.getCorrectAnswers()).thenReturn(answers);

		// when
		presenter.showAnswers(ShowAnswersType.CORRECT);

		// then
		verify(view).removeContent();
	}

	@Test
	public void shouldShowAnswers_CorrectAndAnswerExists() {
		// given
		List<String> answers = mock(List.class);
		when(answers.size()).thenReturn(1);
		String answerToSet = "answer";
		when(answers.get(0)).thenReturn(answerToSet);
		when(model.getCorrectAnswers()).thenReturn(answers);

		// when
		presenter.showAnswers(ShowAnswersType.CORRECT);

		// then
		verify(view).setContent(answerToSet);
	}

	@Test
	public void shouldShowAnswers_UserAndEmptyAnswers() {
		// given
		List<String> answers = mock(List.class);
		when(answers.size()).thenReturn(0);
		when(model.getCurrentAnswers()).thenReturn(answers);

		// when
		presenter.showAnswers(ShowAnswersType.USER);

		// then
		verify(view).removeContent();
	}

	@Test
	public void shouldShowAnswers_UserAndAnswerExists() {
		// given
		List<String> answers = mock(List.class);
		when(answers.size()).thenReturn(1);
		String answerToSet = "answer";
		when(answers.get(0)).thenReturn(answerToSet);
		when(model.getCurrentAnswers()).thenReturn(answers);

		// when
		presenter.showAnswers(ShowAnswersType.USER);

		// then
		verify(view).setContent(answerToSet);
	}

}
