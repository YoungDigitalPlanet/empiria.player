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
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.IdentifiableAnswersByTypeFinder;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

@RunWith(MockitoJUnitRunner.class)
public class SelectionAnswersMarkerTest {
	@Mock
	private IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder;

	@Mock
	private SelectionModuleModel selectionModuleModel;
	
	@Mock
	private GroupAnswersController groupController;
	
	private SelectionAnswersMarker answersMarker;
	
	private List<SelectionAnswerDto> selectedAnswers;
	
	private List<SelectionAnswerDto> notSelectedAnswers;
	
	@Before
	public void setup() {
		answersMarker = new SelectionAnswersMarker(identifiableAnswersByTypeFinder, selectionModuleModel);

		selectedAnswers = Lists.newArrayList(new SelectionAnswerDto());
		when(groupController.getSelectedAnswers())
			.thenReturn(selectedAnswers );
		
		notSelectedAnswers = Lists.newArrayList(new SelectionAnswerDto());
		when(groupController.getNotSelectedAnswers())
			.thenReturn(notSelectedAnswers);
	}
	
	@Test
	public void testUnmarkWrong() throws Exception {
		List<SelectionAnswerDto> buttonsToMark = Lists.newArrayList(new SelectionAnswerDto());
		when(identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(MarkAnswersType.WRONG, selectedAnswers, selectionModuleModel))
			.thenReturn(buttonsToMark);
		
		//then
		
		answersMarker.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.UNMARK, groupController);
		
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
		when(identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(MarkAnswersType.WRONG, selectedAnswers, selectionModuleModel))
			.thenReturn(buttonsToMark);
		
		//then 
		
		answersMarker.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK, groupController);

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
		when(identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(MarkAnswersType.CORRECT, selectedAnswers, selectionModuleModel))
			.thenReturn(buttonsToMark);
		
		//then 
		
		answersMarker.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK, groupController);

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
		when(identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(MarkAnswersType.CORRECT, selectedAnswers, selectionModuleModel))
			.thenReturn(buttonsToMark);
		
		//then 
		
		answersMarker.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK, groupController);

		for (SelectionAnswerDto selectionAnswerDto : notSelectedAnswers) {
			assertEquals(UserAnswerType.NONE, selectionAnswerDto.getSelectionAnswerType());
		}

		for (SelectionAnswerDto selectionAnswerDto : buttonsToMark) {
			assertEquals(UserAnswerType.CORRECT, selectionAnswerDto.getSelectionAnswerType());
		}
	}

}
