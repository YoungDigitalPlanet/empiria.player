package eu.ydp.empiria.player.client.module.selection.controller;

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

import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionItemBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionSimpleChoiceBean;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

@RunWith(MockitoJUnitRunner.class)
public class SelectionModuleViewBuildingControllerJUnitTest {

	private SelectionModuleViewBuildingController viewBuildingController;
	
	@Mock
	private StyleNameConstants styleNameConstants;
	@Mock
	private SelectionModuleFactory selectionModuleFactory;
	@Mock
	private SelectionModuleView selectionModuleView;
	@Mock
	private SelectionModulePresenter selectionModulePresenter;
	@Mock
	private SelectionModuleModel model;
	@Mock
	private SelectionInteractionBean bean;
	
	@Before
	public void setUp() throws Exception {
		
		bean = new SelectionInteractionBean();
		bean.setMulti(true);
		
		viewBuildingController = new SelectionModuleViewBuildingController(
				styleNameConstants, 
				selectionModuleFactory,
				selectionModuleView,
				selectionModulePresenter,
				model,
				bean);
	}

	@After
	public void tearDown() throws Exception {
		Mockito.verifyNoMoreInteractions(
				styleNameConstants,
				selectionModuleFactory,
				selectionModuleView,
				selectionModulePresenter,
				model
				);
	}

	@Test
	public void testFillFirstColumnWithItems() {
		SelectionItemBean itemBean = new SelectionItemBean();
		itemBean.setValue("selection item bean value");
		List<SelectionItemBean> items = Lists.newArrayList(itemBean);
		
		//then
		viewBuildingController.fillFirstColumnWithItems(items);
		
		verify(selectionModuleView).setItemDisplayedName(itemBean.getValue(), items.indexOf(itemBean));
	}

	@Test
	public void testFillFirstRowWithChoices() {
		SelectionSimpleChoiceBean choiceBean = new SelectionSimpleChoiceBean();
		choiceBean.setValue("choice bean value");
		List<SelectionSimpleChoiceBean> simpleChoices = Lists.newArrayList(choiceBean);;
		
		//then
		viewBuildingController.fillFirstRowWithChoices(simpleChoices);
		
		verify(selectionModuleView).setChoiceOptionDisplayedName(choiceBean.getValue(), simpleChoices.indexOf(choiceBean));
	}

	@Test
	public void testFillGridWithButtons_multi() {
		bean.setMulti(true);
		testFillGridWithButtonsForGivenType();
	}
	
	@Test
	public void testFillGridWithButtons_single() {
		bean.setMulti(false);
		testFillGridWithButtonsForGivenType();
	}

	private void testFillGridWithButtonsForGivenType() {
		//prepare items
		SelectionItemBean itemBean = new SelectionItemBean();
		itemBean.setIdentifier("itemBeanIdentifier");
		itemBean.setMatchMax(4);
		
		List<SelectionItemBean> items = Lists.newArrayList(itemBean);
		
		//prepare choices
		SelectionSimpleChoiceBean choice1 = createChoiceBean("choice1Id");
		SelectionSimpleChoiceBean choice2 = createChoiceBean("choice2Id");
		List<SelectionSimpleChoiceBean> simpleChoices = Lists.newArrayList(choice1, choice2);
		
		GroupAnswersController groupController = mock(GroupAnswersController.class);
		when(selectionModuleFactory.createGroupAnswerController(bean.isMulti(), itemBean.getMatchMax(), model))
			.thenReturn(groupController);
		
		String multiStylePart = "selectionStylePart";
		if(bean.isMulti()){
			when(styleNameConstants.SELECTION_MULTI())
				.thenReturn(multiStylePart);
		}else{
			when(styleNameConstants.SELECTION())
				.thenReturn(multiStylePart);			
		}
		
		//first choice
		ChoiceButtonClickHandler clickHandler1 = mock(ChoiceButtonClickHandler.class);
		String answer1Id = itemBean.getIdentifier()+" "+choice1.getIdentifier();
		when(selectionModuleFactory.createChoiceButtonClickHandler(groupController, answer1Id, selectionModulePresenter))
			.thenReturn(clickHandler1 );
		
		SelectionAnswerDto answer1 = new SelectionAnswerDto(answer1Id);
		when(selectionModuleFactory.createSelectionAnswerDto(answer1Id))
			.thenReturn(answer1);

		//second choice
		ChoiceButtonClickHandler clickHandler2 = mock(ChoiceButtonClickHandler.class);
		String answer2Id = itemBean.getIdentifier()+" "+choice2.getIdentifier();
		when(selectionModuleFactory.createChoiceButtonClickHandler(groupController, answer2Id, selectionModulePresenter))
		.thenReturn(clickHandler2);
		
		SelectionAnswerDto answer2 = new SelectionAnswerDto(answer2Id);
		when(selectionModuleFactory.createSelectionAnswerDto(answer2Id))
		.thenReturn(answer2);
		
		//then
		List<GroupAnswersController> resultControllers = viewBuildingController.fillGridWithButtons(items, simpleChoices);
		
		
		verify(selectionModuleFactory).createGroupAnswerController(bean.isMulti(), itemBean.getMatchMax(), model);
		if(bean.isMulti()){
			verify(styleNameConstants).SELECTION_MULTI();
		}else{
			verify(styleNameConstants).SELECTION();
		}
		
		//first choice
		verify(selectionModuleView).createButtonForItemChoicePair(0, 0, multiStylePart);
		verify(selectionModuleFactory).createChoiceButtonClickHandler(groupController, answer1Id, selectionModulePresenter);
		verify(selectionModuleView).addClickHandlerToButton(0, 0, clickHandler1);
		verify(selectionModuleFactory).createSelectionAnswerDto(answer1Id);
		verify(groupController).addSelectionAnswer(answer1);
		
		
		//second choice
		verify(selectionModuleView).createButtonForItemChoicePair(0, 1, multiStylePart);
		verify(selectionModuleFactory).createChoiceButtonClickHandler(groupController, answer2Id, selectionModulePresenter);
		verify(selectionModuleView).addClickHandlerToButton(0, 1, clickHandler2);
		verify(selectionModuleFactory).createSelectionAnswerDto(answer2Id);
		verify(groupController).addSelectionAnswer(answer2);
		
		assertEquals(1, resultControllers.size());
		GroupAnswersController resultController = resultControllers.get(0);
		assertEquals(groupController, resultController);
	}

	private SelectionSimpleChoiceBean createChoiceBean(String identifier) {
		SelectionSimpleChoiceBean choice1 = new SelectionSimpleChoiceBean();
		choice1.setIdentifier(identifier);
		return choice1;
	}

}
