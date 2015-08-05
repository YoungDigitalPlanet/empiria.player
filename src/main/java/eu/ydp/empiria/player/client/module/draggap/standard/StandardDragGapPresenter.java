package eu.ydp.empiria.player.client.module.draggap.standard;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapBasePresenter;
import eu.ydp.empiria.player.client.module.draggap.standard.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class StandardDragGapPresenter extends DragGapBasePresenter<DragGapBean> {

    @Inject
    public StandardDragGapPresenter(@ModuleScoped DragGapView view, @ModuleScoped DragGapModuleModel model, @PageScoped AnswerEvaluationSupplier answerEvaluationSupplier, @ModuleScoped SourceListManagerAdapter sourceListManagerAdapter) {
        super(view, model, answerEvaluationSupplier, sourceListManagerAdapter);
    }
}
