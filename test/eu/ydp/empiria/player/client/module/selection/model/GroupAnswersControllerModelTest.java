package eu.ydp.empiria.player.client.module.selection.model;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.IdentifiableAnswersByTypeFinder;

@RunWith(MockitoJUnitRunner.class)
public class GroupAnswersControllerModelTest {

	@Mock
	private IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder;
	@Mock
	private SelectionModuleModel model;

	private GroupAnswersControllerModel groupAnswersControllerModel;

	private List<GroupAnswersController> groupControllers;
	private GroupAnswersController groupController1;
	private GroupAnswersController groupController2;

	@Before
	public void setUp() {
		groupAnswersControllerModel = new GroupAnswersControllerModel(identifiableAnswersByTypeFinder, model);
		groupController1 = mock(GroupAnswersController.class);
		groupController2 = mock(GroupAnswersController.class);

		groupControllers = Lists.newArrayList(groupController1, groupController2);

		groupAnswersControllerModel.setGroupChoicesControllers(groupControllers);
	}

	@Test
	public void testGetSetGroupChoicesControllers() throws Exception {
		// then
		assertEquals(groupControllers, groupAnswersControllerModel.getGroupChoicesControllers());
	}

	@Test
	public void testIndexOf() throws Exception {
		// then
		assertEquals(0, groupAnswersControllerModel.indexOf(groupController1));
		assertEquals(1, groupAnswersControllerModel.indexOf(groupController2));
	}

	@Test
	public void testGetSelectedAnswers() throws Exception {
		// given
		SelectionAnswerDto selectedAnswer1 = new SelectionAnswerDto();
		List<SelectionAnswerDto> selectedAnswers1 = Lists.newArrayList(selectedAnswer1);

		SelectionAnswerDto selectedAnswer2 = new SelectionAnswerDto();
		List<SelectionAnswerDto> selectedAnswers2 = Lists.newArrayList(selectedAnswer2);

		// when
		when(groupController1.getSelectedAnswers()).thenReturn(selectedAnswers1);

		when(groupController2.getSelectedAnswers()).thenReturn(selectedAnswers2);

		List<SelectionAnswerDto> allSelectedAnswers = groupAnswersControllerModel.getSelectedAnswers();

		// then
		assertEquals(2, allSelectedAnswers.size());
		assertEquals(selectedAnswer1, allSelectedAnswers.get(0));
		assertEquals(selectedAnswer2, allSelectedAnswers.get(1));
	}

	@Test
	public void testGetNotSelectedAnswers() throws Exception {
		// given
		SelectionAnswerDto notSelectedAnswer1 = new SelectionAnswerDto();
		List<SelectionAnswerDto> notSelectedAnswers1 = Lists.newArrayList(notSelectedAnswer1);

		SelectionAnswerDto notSelectedAnswer2 = new SelectionAnswerDto();
		List<SelectionAnswerDto> notSelectedAnswers2 = Lists.newArrayList(notSelectedAnswer2);

		// when
		when(groupController1.getNotSelectedAnswers()).thenReturn(notSelectedAnswers1);

		when(groupController2.getNotSelectedAnswers()).thenReturn(notSelectedAnswers2);

		List<SelectionAnswerDto> notSelectedAnswers = groupAnswersControllerModel.getNotSelectedAnswers();

		// then
		assertEquals(2, notSelectedAnswers.size());
		assertEquals(notSelectedAnswer1, notSelectedAnswers.get(0));
		assertEquals(notSelectedAnswer2, notSelectedAnswers.get(1));
	}

	@Test
	public void testGetButtonsToMarkForType() throws Exception {
		// given
		SelectionAnswerDto selectedAnswer1 = new SelectionAnswerDto();
		List<SelectionAnswerDto> selectedAnswers1 = Lists.newArrayList(selectedAnswer1);

		MarkAnswersType type = MarkAnswersType.CORRECT;

		// when
		when(groupController1.getSelectedAnswers()).thenReturn(selectedAnswers1);

		groupAnswersControllerModel.getButtonsToMarkForType(type);

		// then
		verify(identifiableAnswersByTypeFinder).findAnswersObjectsOfGivenType(type, groupAnswersControllerModel.getSelectedAnswers(), model);
	}
}