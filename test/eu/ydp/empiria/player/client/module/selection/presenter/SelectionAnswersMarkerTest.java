package eu.ydp.empiria.player.client.module.selection.presenter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.selection.model.GroupAnswersControllerModel;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

@RunWith(MockitoJUnitRunner.class)
public class SelectionAnswersMarkerTest {
	@Mock
	private GroupAnswersControllerModel answersControllerModel;

	private SelectionAnswersMarker answersMarker;

	private List<SelectionAnswerDto> selectedAnswers;

	private List<SelectionAnswerDto> notSelectedAnswers;

	@Before
	public void setup() {
		answersMarker = new SelectionAnswersMarker(answersControllerModel);

		selectedAnswers = Lists.newArrayList(new SelectionAnswerDto());
		when(answersControllerModel.getSelectedAnswers()).thenReturn(selectedAnswers);

		notSelectedAnswers = Lists.newArrayList(new SelectionAnswerDto());
		when(answersControllerModel.getNotSelectedAnswers()).thenReturn(notSelectedAnswers);
	}

	@Test
	public void testUnmarkWrong() throws Exception {
		List<SelectionAnswerDto> buttonsToMark = Lists.newArrayList(new SelectionAnswerDto());
		when(answersControllerModel.getButtonsToMarkForType(MarkAnswersType.WRONG)).thenReturn(buttonsToMark);

		// then

		answersMarker.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.UNMARK);

		for (SelectionAnswerDto selectionAnswerDto : notSelectedAnswers) {
			assertEquals(UserAnswerType.DEFAULT, selectionAnswerDto.getSelectionAnswerType());
		}

		for (SelectionAnswerDto selectionAnswerDto : buttonsToMark) {
			assertEquals(UserAnswerType.DEFAULT, selectionAnswerDto.getSelectionAnswerType());
		}
	}

	@Test
	public void testMarkWrong() throws Exception {
		List<SelectionAnswerDto> buttonsToMark = Lists.newArrayList(new SelectionAnswerDto());
		when(answersControllerModel.getButtonsToMarkForType(MarkAnswersType.WRONG)).thenReturn(buttonsToMark);

		// then

		answersMarker.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);

		for (SelectionAnswerDto selectionAnswerDto : notSelectedAnswers) {
			assertEquals(UserAnswerType.NONE, selectionAnswerDto.getSelectionAnswerType());
		}

		for (SelectionAnswerDto selectionAnswerDto : buttonsToMark) {
			assertEquals(UserAnswerType.WRONG, selectionAnswerDto.getSelectionAnswerType());
		}
	}

	@Test
	public void testUnmarkCorrect() throws Exception {
		List<SelectionAnswerDto> buttonsToMark = Lists.newArrayList(new SelectionAnswerDto());
		when(answersControllerModel.getButtonsToMarkForType(MarkAnswersType.CORRECT)).thenReturn(buttonsToMark);

		// then

		answersMarker.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK);

		for (SelectionAnswerDto selectionAnswerDto : notSelectedAnswers) {
			assertEquals(UserAnswerType.DEFAULT, selectionAnswerDto.getSelectionAnswerType());
		}

		for (SelectionAnswerDto selectionAnswerDto : buttonsToMark) {
			assertEquals(UserAnswerType.DEFAULT, selectionAnswerDto.getSelectionAnswerType());
		}
	}

	@Test
	public void testMarkCorrect() throws Exception {
		List<SelectionAnswerDto> buttonsToMark = Lists.newArrayList(new SelectionAnswerDto());
		when(answersControllerModel.getButtonsToMarkForType(MarkAnswersType.CORRECT)).thenReturn(buttonsToMark);

		// then

		answersMarker.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

		for (SelectionAnswerDto selectionAnswerDto : notSelectedAnswers) {
			assertEquals(UserAnswerType.NONE, selectionAnswerDto.getSelectionAnswerType());
		}

		for (SelectionAnswerDto selectionAnswerDto : buttonsToMark) {
			assertEquals(UserAnswerType.CORRECT, selectionAnswerDto.getSelectionAnswerType());
		}
	}
}
