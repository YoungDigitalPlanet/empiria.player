package eu.ydp.empiria.player.client.module.selection.controller;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.SelectionStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionItemBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionSimpleChoiceBean;
import eu.ydp.empiria.player.client.module.selection.view.SelectionElementPositionGenerator;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("PMD")
public class SelectionViewBuilderJUnitTest {

    private SelectionViewBuilder viewBuilder;

    @Mock
    private SelectionStyleNameConstants styleNameConstants;
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
    @Mock
    private SelectionElementPositionGenerator positionGenerator;

    @Before
    public void setUp() throws Exception {

        bean = new SelectionInteractionBean();
        bean.setMulti(true);

        viewBuilder = new SelectionViewBuilder(selectionModuleFactory, styleNameConstants, positionGenerator, selectionModuleView);
        viewBuilder.bindView(selectionModulePresenter, bean);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.verifyNoMoreInteractions(styleNameConstants, selectionModuleFactory, selectionModuleView, selectionModulePresenter, model);
    }

    @Test
    public void testSetGridSize() {
        int amountOfItems = 5;
        int amountOfChoices = 7;
        viewBuilder.setGridSize(amountOfItems, amountOfChoices);
        // then
        verify(selectionModuleView).setGridSize(amountOfItems, amountOfChoices);
    }

    @Test
    public void testFillFirstColumnWithItems() {
        SelectionItemBean itemBean = new SelectionItemBean();
        XMLContent xmlContent = mock(XMLContent.class);

        itemBean.setXmlContent(xmlContent);
        List<SelectionItemBean> items = Lists.newArrayList(itemBean);

        SelectionGridElementPosition updatedElementPositon = new SelectionGridElementPosition(0, 0);
        when(positionGenerator.getItemLabelElementPosition(0)).thenReturn(updatedElementPositon);

        // then
        viewBuilder.fillFirstColumnWithItems(items);

        SelectionGridElementPosition position = new SelectionGridElementPosition(0, items.indexOf(itemBean));
        verify(selectionModuleView).setItemDisplayedName(itemBean.getXmlContent(), position);
    }

    @Test
    public void testFillFirstRowWithChoices() {
        SelectionSimpleChoiceBean choiceBean = new SelectionSimpleChoiceBean();
        XMLContent xmlContent = mock(XMLContent.class);

        choiceBean.setXmlContent(xmlContent);
        List<SelectionSimpleChoiceBean> simpleChoices = Lists.newArrayList(choiceBean);
        ;

        SelectionGridElementPosition updatedElementPositon = new SelectionGridElementPosition(0, 0);
        when(positionGenerator.getChoiceLabelElementPosition(0)).thenReturn(updatedElementPositon);

        // then
        viewBuilder.fillFirstRowWithChoices(simpleChoices);

        SelectionGridElementPosition position = new SelectionGridElementPosition(simpleChoices.indexOf(choiceBean), 0);
        verify(selectionModuleView).setChoiceOptionDisplayedName(choiceBean.getXmlContent(), position);
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
        // prepare items
        SelectionItemBean itemBean = new SelectionItemBean();
        itemBean.setIdentifier("itemBeanIdentifier");
        itemBean.setMatchMax(4);

        List<SelectionItemBean> items = Lists.newArrayList(itemBean);

        // prepare choices
        SelectionSimpleChoiceBean choice1 = createChoiceBean("choice1Id");
        SelectionSimpleChoiceBean choice2 = createChoiceBean("choice2Id");
        List<SelectionSimpleChoiceBean> simpleChoices = Lists.newArrayList(choice1, choice2);

        GroupAnswersController groupController = mock(GroupAnswersController.class);
        when(selectionModuleFactory.createGroupAnswerController(bean.isMulti(), itemBean.getMatchMax())).thenReturn(groupController);

        String multiStylePart = "selectionStylePart";
        if (bean.isMulti()) {
            when(styleNameConstants.SELECTION_MULTI()).thenReturn(multiStylePart);
        } else {
            when(styleNameConstants.SELECTION()).thenReturn(multiStylePart);
        }

        // first choice
        ChoiceButtonClickHandler clickHandler1 = mock(ChoiceButtonClickHandler.class);
        String answer1Id = itemBean.getIdentifier() + " " + choice1.getIdentifier();
        when(selectionModuleFactory.createChoiceButtonClickHandler(groupController, answer1Id, selectionModulePresenter)).thenReturn(clickHandler1);

        SelectionAnswerDto answer1 = new SelectionAnswerDto(answer1Id);
        when(selectionModuleFactory.createSelectionAnswerDto(answer1Id)).thenReturn(answer1);

        SelectionGridElementPosition firstUpdatedPositon = new SelectionGridElementPosition(0, 0);
        when(positionGenerator.getButtonElementPositionFor(0, 0)).thenReturn(firstUpdatedPositon);
        when(positionGenerator.getChoiceLabelElementPosition(0)).thenReturn(firstUpdatedPositon);
        when(positionGenerator.getItemLabelElementPosition(0)).thenReturn(firstUpdatedPositon);

        // second choice
        ChoiceButtonClickHandler clickHandler2 = mock(ChoiceButtonClickHandler.class);
        String answer2Id = itemBean.getIdentifier() + " " + choice2.getIdentifier();
        when(selectionModuleFactory.createChoiceButtonClickHandler(groupController, answer2Id, selectionModulePresenter)).thenReturn(clickHandler2);

        SelectionAnswerDto answer2 = new SelectionAnswerDto(answer2Id);
        when(selectionModuleFactory.createSelectionAnswerDto(answer2Id)).thenReturn(answer2);

        SelectionGridElementPosition secondUpdatedPositon = new SelectionGridElementPosition(1, 0);
        when(positionGenerator.getButtonElementPositionFor(0, 1)).thenReturn(secondUpdatedPositon);
        when(positionGenerator.getChoiceLabelElementPosition(1)).thenReturn(secondUpdatedPositon);
        when(positionGenerator.getItemLabelElementPosition(1)).thenReturn(secondUpdatedPositon);

        // then
        List<GroupAnswersController> resultControllers = viewBuilder.fillGrid(items, simpleChoices);

        verify(selectionModuleFactory).createGroupAnswerController(bean.isMulti(), itemBean.getMatchMax());
        if (bean.isMulti()) {
            verify(styleNameConstants).SELECTION_MULTI();
        } else {
            verify(styleNameConstants).SELECTION();
        }

        // first choice
        SelectionGridElementPosition firstPosition = new SelectionGridElementPosition(0, 0);
        verify(selectionModuleView).createButtonForItemChoicePair(firstPosition, multiStylePart);
        verify(selectionModuleFactory).createChoiceButtonClickHandler(groupController, answer1Id, selectionModulePresenter);
        verify(selectionModuleView).addClickHandlerToButton(firstPosition, clickHandler1);
        verify(selectionModuleFactory).createSelectionAnswerDto(answer1Id);
        verify(selectionModuleView).setItemDisplayedName(null, firstPosition);
        verify(selectionModuleView).setChoiceOptionDisplayedName(null, firstPosition);

        verify(groupController).addSelectionAnswer(answer1);

        // second choice
        SelectionGridElementPosition secondPosition = new SelectionGridElementPosition(1, 0);
        verify(selectionModuleView).createButtonForItemChoicePair(secondPosition, multiStylePart);
        verify(selectionModuleFactory).createChoiceButtonClickHandler(groupController, answer2Id, selectionModulePresenter);
        verify(selectionModuleView).addClickHandlerToButton(secondPosition, clickHandler2);
        verify(selectionModuleFactory).createSelectionAnswerDto(answer2Id);
        verify(selectionModuleView).setChoiceOptionDisplayedName(null, secondPosition);

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
