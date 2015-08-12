package eu.ydp.empiria.player.client.module.selection.controller;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.*;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.structure.*;
import eu.ydp.empiria.player.client.module.selection.view.*;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.*;

public class SelectionViewBuilder {

    private SelectionModuleView selectionModuleView;
    private SelectionModuleFactory selectionModuleFactory;
    private SelectionModulePresenter selectionModulePresenter;
    private SelectionInteractionBean bean;
    private StyleNameConstants styleNameConstants;
    private SelectionElementPositionGenerator elementPositionGenerator;
    private SelectionModuleModel selectionModuleModel;

    @Inject
    public SelectionViewBuilder(SelectionModuleFactory selectionModuleFactory, StyleNameConstants styleNameConstants,
            SelectionElementPositionGenerator elementPositionGenerator, @ModuleScoped SelectionModuleView selectionModuleView,
            @ModuleScoped SelectionModuleModel selectionModuleModel) {
        this.styleNameConstants = styleNameConstants;
        this.selectionModuleView = selectionModuleView;
        this.elementPositionGenerator = elementPositionGenerator;
        this.selectionModuleFactory = selectionModuleFactory;
        this.selectionModuleModel = selectionModuleModel;
    }

    public void bindView(SelectionModulePresenter selectionModulePresenter, SelectionInteractionBean bean) {
        this.selectionModulePresenter = selectionModulePresenter;
        this.bean = bean;
    }

    public void fillFirstColumnWithItems(List<SelectionItemBean> items) {
        for (int rowNumber = 0; rowNumber < items.size(); rowNumber++) {
            SelectionItemBean selectionItemBean = items.get(rowNumber);
            XMLContent xmlContent = selectionItemBean.getXmlContent();
            SelectionGridElementPosition position = elementPositionGenerator.getItemLabelElementPosition(rowNumber);
            selectionModuleView.setItemDisplayedName(xmlContent, position);
        }
    }

    public void fillFirstRowWithChoices(List<SelectionSimpleChoiceBean> simpleChoices) {
        for (int columnNumber = 0; columnNumber < simpleChoices.size(); columnNumber++) {
            SelectionSimpleChoiceBean selectionSimpleChoiceBean = simpleChoices.get(columnNumber);
            XMLContent xmlContent = selectionSimpleChoiceBean.getXmlContent();
            SelectionGridElementPosition position = elementPositionGenerator.getChoiceLabelElementPosition(columnNumber);
            selectionModuleView.setChoiceOptionDisplayedName(xmlContent, position);
        }
    }

    public List<GroupAnswersController> fillGrid(List<SelectionItemBean> items, List<SelectionSimpleChoiceBean> simpleChoices) {
        fillFirstRowWithChoices(simpleChoices);
        fillFirstColumnWithItems(items);

        List<GroupAnswersController> groupAnswersControllers = new ArrayList<GroupAnswersController>();
        for (int rowNumber = 0; rowNumber < items.size(); rowNumber++) {
            SelectionItemBean itemBean = items.get(rowNumber);
            GroupAnswersController groupAnswersController = fillRowWithGroupedItems(rowNumber, itemBean, simpleChoices);
            groupAnswersControllers.add(groupAnswersController);
        }

        return groupAnswersControllers;
    }

    private GroupAnswersController fillRowWithGroupedItems(int rowNumber, SelectionItemBean itemBean, List<SelectionSimpleChoiceBean> simpleChoices) {
        boolean multi = bean.isMulti();
        int matchMax = itemBean.getMatchMax();

        GroupAnswersController groupAnswerController = selectionModuleFactory.getGroupAnswerController(multi, matchMax, selectionModuleModel);
        String moduleStyleName = getModuleStyleName(bean.isMulti());
        String itemIdentifier = itemBean.getIdentifier();

        for (int columnNumber = 0; columnNumber < simpleChoices.size(); columnNumber++) {
            SelectionSimpleChoiceBean selectionSimpleChoiceBean = simpleChoices.get(columnNumber);
            String buttonIdentifier = buildPairedIdentifier(itemIdentifier, selectionSimpleChoiceBean.getIdentifier());

            SelectionGridElementPosition position = elementPositionGenerator.getButtonElementPositionFor(rowNumber, columnNumber);

            selectionModuleView.createButtonForItemChoicePair(position, moduleStyleName);

            ChoiceButtonClickHandler clickHandler = selectionModuleFactory.getChoiceButtonClickHandler(groupAnswerController, buttonIdentifier,
                                                                                                       selectionModulePresenter);
            selectionModuleView.addClickHandlerToButton(position, clickHandler);

            SelectionAnswerDto selectionAnswerDto = selectionModuleFactory.getSelectionAnswerDto(buttonIdentifier);
            groupAnswerController.addSelectionAnswer(selectionAnswerDto);
        }

        return groupAnswerController;
    }

    public String getModuleStyleName(boolean isMulti) {
        if (isMulti) {
            return styleNameConstants.SELECTION_MULTI();
        } else {
            return styleNameConstants.SELECTION();
        }
    }

    private String buildPairedIdentifier(String itemIdentifier, String choiceIdentifier) {
        return itemIdentifier + " " + choiceIdentifier;
    }

    public void setGridSize(int amountOfItems, int amountOfChoices) {
        selectionModuleView.setGridSize(amountOfItems, amountOfChoices);
    }
}
