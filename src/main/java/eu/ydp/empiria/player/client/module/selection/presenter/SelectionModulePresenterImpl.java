package eu.ydp.empiria.player.client.module.selection.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionViewBuilder;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionViewUpdater;
import eu.ydp.empiria.player.client.module.selection.model.GroupAnswersControllerModel;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionItemBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionSimpleChoiceBean;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class SelectionModulePresenterImpl implements SelectionModulePresenter {

    private final SelectionModuleModel model;
    private SelectionInteractionBean bean;
    private ModuleSocket moduleSocket;
    private final GroupAnswersControllerModel groupChoicesControllersModel;

    private final SelectionModuleView selectionModuleView;
    private final SelectionViewUpdater viewUpdater;
    private final SelectionViewBuilder viewBuilder;
    private final SelectionAnswersMarker answersMarker;

    @Inject
    public SelectionModulePresenterImpl(SelectionViewUpdater selectionViewUpdater, SelectionAnswersMarker answersMarker,
                                        @ModuleScoped SelectionModuleView selectionModuleView, @ModuleScoped SelectionModuleModel selectionModuleModel,
                                        @ModuleScoped SelectionViewBuilder selectionViewBuilder, @ModuleScoped GroupAnswersControllerModel groupChoicesControllers) {
        this.answersMarker = answersMarker;
        this.selectionModuleView = selectionModuleView;
        this.viewUpdater = selectionViewUpdater;
        this.model = selectionModuleModel;
        this.viewBuilder = selectionViewBuilder;
        groupChoicesControllersModel = groupChoicesControllers;
    }

    @Override
    public void bindView() {
        InlineBodyGeneratorSocket inlineBodyGeneratorSocket = moduleSocket.getInlineBodyGeneratorSocket();

        selectionModuleView.initialize(inlineBodyGeneratorSocket);

        viewBuilder.bindView(this, bean);

        fillSelectionGrid();
    }

    private void fillSelectionGrid() {
        List<SelectionItemBean> items = bean.getItems();
        List<SelectionSimpleChoiceBean> simpleChoices = bean.getSimpleChoices();

        viewBuilder.setGridSize(items.size(), simpleChoices.size());

        groupChoicesControllersModel.setGroupChoicesControllers(viewBuilder.fillGrid(items, simpleChoices));
    }

    @Override
    public void reset() {
        for (GroupAnswersController groupChoicesController : groupChoicesControllersModel.getGroupChoicesControllers()) {
            groupChoicesController.reset();
        }
    }

    @Override
    public void setModel(SelectionModuleModel model) {
        // SelectionModuleModel is now being injected in moduleScope
    }

    @Override
    public void setModuleSocket(ModuleSocket socket) {
        this.moduleSocket = socket;
    }

    @Override
    public void setBean(SelectionInteractionBean bean) {
        this.bean = bean;
    }

    @Override
    public void setLocked(boolean locked) {
        for (GroupAnswersController groupChoicesController : groupChoicesControllersModel.getGroupChoicesControllers()) {
            groupChoicesController.setLockedAllAnswers(locked);

            updateGroupAnswerView(groupChoicesController);
        }
    }

    @Override
    public void updateGroupAnswerView(GroupAnswersController groupChoicesController) {
        int itemNumber = groupChoicesControllersModel.indexOf(groupChoicesController);
        viewUpdater.updateView(selectionModuleView, groupChoicesController, itemNumber);
    }

    @Override
    public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
        answersMarker.markAnswers(type, mode);
        for (GroupAnswersController groupChoicesController : groupChoicesControllersModel.getGroupChoicesControllers()) {
            updateGroupAnswerView(groupChoicesController);
        }
    }

    @Override
    public void showAnswers(ShowAnswersType mode) {
        List<String> answersToSelect;
        if (ShowAnswersType.CORRECT.equals(mode)) {
            answersToSelect = model.getCorrectAnswers();
        } else {
            answersToSelect = model.getCurrentAnswers();
        }

        for (GroupAnswersController groupChoicesController : groupChoicesControllersModel.getGroupChoicesControllers()) {
            groupChoicesController.selectOnlyAnswersMatchingIds(answersToSelect);

            updateGroupAnswerView(groupChoicesController);
        }
    }

    @Override
    public Widget asWidget() {
        return selectionModuleView.asWidget();
    }
}
