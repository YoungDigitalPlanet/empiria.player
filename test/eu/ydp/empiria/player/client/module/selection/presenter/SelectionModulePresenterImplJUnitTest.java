package eu.ydp.empiria.player.client.module.selection.presenter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.IdentifiableAnswersByTypeFinder;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionModuleViewBuildingController;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionModuleViewUpdatingController;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionItemBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionSimpleChoiceBean;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("PMD")
public class SelectionModulePresenterImplJUnitTest {

	private SelectionModulePresenterImpl presenter;
	
	private ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
	
	@Mock
	private SelectionModuleView selectionModuleView;
	@Mock
	private SelectionModuleFactory selectionModuleFactory;
	@Mock
	private IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder;
	@Mock
	private SelectionModuleViewUpdatingController selectionModuleViewUpdatingController;
	@Mock
	private SelectionModuleModel model;
	@Mock
	private ModuleSocket moduleSocket;
	@Mock
	private SelectionModuleViewBuildingController viewBuildingController; 

	private SelectionInteractionBean bean;

	
	@Before
	public void setUp() throws Exception {
		presenter = new SelectionModulePresenterImpl(
				selectionModuleView,
				selectionModuleFactory,
				identifiableAnswersByTypeFinder,
				selectionModuleViewUpdatingController);
		
		bean = new SelectionInteractionBean();
		presenter.setBean(bean);
		presenter.setModel(model);
		presenter.setModuleSocket(moduleSocket);
	}

	@After
	public void tearDown() throws Exception {
		Mockito.verifyNoMoreInteractions(
				selectionModuleView,
				selectionModuleFactory,
				identifiableAnswersByTypeFinder,
				selectionModuleViewUpdatingController,
				model,
				moduleSocket
				);
	}

	@Test
	public void testBindView()throws Exception {
		InlineBodyGeneratorSocket inlineBodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
		List<SelectionItemBean> items = Lists.newArrayList(new SelectionItemBean());
		List<SelectionSimpleChoiceBean> choices = Lists.newArrayList(new SelectionSimpleChoiceBean());
		bean.setItems(items);
		bean.setSimpleChoices(choices);
		List<GroupAnswersController> groupChoicesControllers = Lists.newArrayList(mock(GroupAnswersController.class));
		
		when(moduleSocket.getInlineBodyGeneratorSocket())
			.thenReturn(inlineBodyGeneratorSocket );
		
		when(selectionModuleFactory.createViewBuildingController(selectionModuleView, presenter, model, bean))
			.thenReturn(viewBuildingController);
		
		when(viewBuildingController.fillGridWithButtons(items, choices))
			.thenReturn(groupChoicesControllers);
		
		//then
		presenter.bindView();
		
		verify(moduleSocket).getInlineBodyGeneratorSocket();
		verify(selectionModuleView).initialize(items.size(), choices.size(), inlineBodyGeneratorSocket);
		verify(selectionModuleFactory).createViewBuildingController(selectionModuleView, presenter, model, bean);
		verify(viewBuildingController).fillFirstRowWithChoices(choices);
		verify(viewBuildingController).fillFirstColumnWithItems(items);
		verify(viewBuildingController).fillGridWithButtons(items, choices);
		assertEquals(groupChoicesControllers, getGroupControllers());
		
		Mockito.verifyNoMoreInteractions(inlineBodyGeneratorSocket);
	}

	@SuppressWarnings("unchecked")
	private List<GroupAnswersController> getGroupControllers(){
		try {
			return (List<GroupAnswersController>) reflectionsUtils.getValueFromFiledInObject("groupChoicesControllers", presenter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void setGroupController(List<GroupAnswersController> groupControllers){
		try {
			reflectionsUtils.setValueInObjectOnField("groupChoicesControllers", presenter, groupControllers);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testReset() {
		GroupAnswersController groupController1 = mock(GroupAnswersController.class);
		GroupAnswersController groupController2 = mock(GroupAnswersController.class);
		
		List<GroupAnswersController> groupControllers = Lists.newArrayList(groupController1, groupController2);
		setGroupController(groupControllers);
		
		//then
		presenter.reset();
		
		verify(groupController1).reset();
		verify(groupController2).reset();
		
		Mockito.verifyNoMoreInteractions(groupController1, groupController2);
	}

	@Test
	public void testSetLocked() {
		GroupAnswersController groupController1 = mock(GroupAnswersController.class);
		GroupAnswersController groupController2 = mock(GroupAnswersController.class);
		
		List<GroupAnswersController> groupControllers = Lists.newArrayList(groupController1, groupController2);
		setGroupController(groupControllers);
		
		//then
		presenter.setLocked(true);
		
		verify(groupController1).setLockedAllAnswers(true);
		verify(groupController2).setLockedAllAnswers(true);
		verify(selectionModuleViewUpdatingController).updateView(selectionModuleView, groupControllers);
		
		Mockito.verifyNoMoreInteractions(groupController1, groupController2);
	}

	@Test
	public void testMarkAnswers_unmark() throws Exception {
		GroupAnswersController groupController = mock(GroupAnswersController.class);
		
		List<GroupAnswersController> groupControllers = Lists.newArrayList(groupController);
		setGroupController(groupControllers);
		
		List<SelectionAnswerDto> selectedAnswers = Lists.newArrayList(new SelectionAnswerDto());
		when(groupController.getSelectedAnswers())
			.thenReturn(selectedAnswers );
		
		List<SelectionAnswerDto> notSelectedAnswers = Lists.newArrayList(new SelectionAnswerDto());
		when(groupController.getNotSelectedAnswers())
			.thenReturn(notSelectedAnswers);

		when(identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(MarkAnswersType.CORRECT, selectedAnswers, model))
			.thenReturn(selectedAnswers);
		
		//then
		presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK);

		
		verify(groupController).getSelectedAnswers();
		verify(groupController).getNotSelectedAnswers();
		verify(identifiableAnswersByTypeFinder).findAnswersObjectsOfGivenType(MarkAnswersType.CORRECT, selectedAnswers, model);
		verify(selectionModuleViewUpdatingController).updateView(selectionModuleView, getGroupControllers());
		
		for (SelectionAnswerDto selectionAnswerDto : notSelectedAnswers) {
			assertEquals(UserAnswerType.DEFAULT, selectionAnswerDto.getSelectionAnswerType());
		}
		
		for (SelectionAnswerDto selectionAnswerDto : selectedAnswers) {
			assertEquals(UserAnswerType.DEFAULT, selectionAnswerDto.getSelectionAnswerType());
		}
	}
	
	@Test
	public void testMarkAnswers_markCorrectAnswers() {
		testMarkAnswers(MarkAnswersType.CORRECT, UserAnswerType.CORRECT);
	}
	
	@Test
	public void testMarkAnswers_markWrongAnswers() {
		testMarkAnswers(MarkAnswersType.WRONG, UserAnswerType.WRONG);
	}

	private void testMarkAnswers(MarkAnswersType markAnswerType, UserAnswerType userAnswerType) {
		GroupAnswersController groupController = mock(GroupAnswersController.class);
		
		List<GroupAnswersController> groupControllers = Lists.newArrayList(groupController);
		setGroupController(groupControllers);
		
		List<SelectionAnswerDto> selectedAnswers = Lists.newArrayList(new SelectionAnswerDto());
		when(groupController.getSelectedAnswers())
			.thenReturn(selectedAnswers );
		
		List<SelectionAnswerDto> notSelectedAnswers = Lists.newArrayList(new SelectionAnswerDto());
		when(groupController.getNotSelectedAnswers())
			.thenReturn(notSelectedAnswers);
		
		when(identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(markAnswerType, selectedAnswers, model))
			.thenReturn(selectedAnswers);
		
		//then
		presenter.markAnswers(markAnswerType, MarkAnswersMode.MARK);

		
		verify(groupController).getSelectedAnswers();
		verify(groupController).getNotSelectedAnswers();
		verify(identifiableAnswersByTypeFinder).findAnswersObjectsOfGivenType(markAnswerType, selectedAnswers, model);
		verify(selectionModuleViewUpdatingController).updateView(selectionModuleView, getGroupControllers());
		
		for (SelectionAnswerDto selectionAnswerDto : notSelectedAnswers) {
			assertEquals(UserAnswerType.NONE, selectionAnswerDto.getSelectionAnswerType());
		}
		
		for (SelectionAnswerDto selectionAnswerDto : selectedAnswers) {
			assertEquals(userAnswerType, selectionAnswerDto.getSelectionAnswerType());
		}
	}

	@Test
	public void testShowAnswers_showCorrectAnswers() {
		GroupAnswersController groupController1 = mock(GroupAnswersController.class);
		GroupAnswersController groupController2 = mock(GroupAnswersController.class);
		
		List<GroupAnswersController> groupControllers = Lists.newArrayList(groupController1, groupController2);
		setGroupController(groupControllers);
		
		
		List<String> correctAnswers = Lists.newArrayList("correctAnswer");
		when(model.getCorrectAnswers())
			.thenReturn(correctAnswers);
		
		//then
		presenter.showAnswers(ShowAnswersType.CORRECT);
		
		verify(model).getCorrectAnswers();
		verify(groupController1).selectOnlyAnswersMatchingIds(correctAnswers);
		verify(groupController2).selectOnlyAnswersMatchingIds(correctAnswers);
		verify(selectionModuleViewUpdatingController).updateView(selectionModuleView, groupControllers);
		
		Mockito.verifyNoMoreInteractions(groupController1, groupController2);
	}
	
	@Test
	public void testShowAnswers_showUserAnswers() {
		GroupAnswersController groupController1 = mock(GroupAnswersController.class);
		GroupAnswersController groupController2 = mock(GroupAnswersController.class);
		
		List<GroupAnswersController> groupControllers = Lists.newArrayList(groupController1, groupController2);
		setGroupController(groupControllers);
		
		
		List<String> userAnswers = Lists.newArrayList("userAnswer");
		when(model.getCurrentAnswers())
			.thenReturn(userAnswers);
		
		//then
		presenter.showAnswers(ShowAnswersType.USER);
		
		verify(model).getCurrentAnswers();
		verify(groupController1).selectOnlyAnswersMatchingIds(userAnswers);
		verify(groupController2).selectOnlyAnswersMatchingIds(userAnswers);
		verify(selectionModuleViewUpdatingController).updateView(selectionModuleView, groupControllers);
		
		Mockito.verifyNoMoreInteractions(groupController1, groupController2);
	}

	@Test
	public void testAsWidget() {
		Widget result = presenter.asWidget();
		
		verify(selectionModuleView).asWidget();
	}

}
