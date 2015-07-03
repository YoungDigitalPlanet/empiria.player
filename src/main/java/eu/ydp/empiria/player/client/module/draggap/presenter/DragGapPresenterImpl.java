package eu.ydp.empiria.player.client.module.draggap.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.module.sourcelist.view.DisableDefaultBehaviorCommand;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

import javax.annotation.PostConstruct;
import java.util.List;

public class DragGapPresenterImpl implements DragGapPresenter {

    private final AnswerEvaluationSupplier answerEvaluationSupplier;
    private final DragGapModuleModel model;
    private final DragGapView view;
    private final SourceListManagerAdapter sourceListManagerAdapter;
    @Inject
    private UserInteractionHandlerFactory interactionHandlerFactory;

    @Inject
    public DragGapPresenterImpl(@ModuleScoped DragGapView view, @ModuleScoped DragGapModuleModel model,
                                @PageScoped AnswerEvaluationSupplier answerEvaluationSupplier, @ModuleScoped SourceListManagerAdapter sourceListManagerAdapter) {
        this.view = view;
        this.model = model;
        this.answerEvaluationSupplier = answerEvaluationSupplier;
        this.sourceListManagerAdapter = sourceListManagerAdapter;
    }

    @PostConstruct
    public void postConstruct() {
        disableTextMark();
    }

    private void disableTextMark() {
        EventHandlerProxy userOverHandler = interactionHandlerFactory.createUserOverHandler(new DisableDefaultBehaviorCommand());
        userOverHandler.apply(view.asWidget());
    }

    @Override
    public void bindView() {
        view.updateStyle(UserAnswerType.DEFAULT);
    }

    @Override
    public void reset() {
        view.removeContent();
        model.reset();
        view.updateStyle(UserAnswerType.DEFAULT);
    }

    @Deprecated
    @Override
    public void setModel(DragGapModuleModel model) {
    }

    @Override
    public void setModuleSocket(ModuleSocket socket) {
    }

    @Override
    public void setBean(DragGapBean bean) {
    }

    @Override
    public void setLocked(boolean locked) {
        view.lock(locked);
        view.setDragDisabled(locked);
    }

    @Override
    public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
        if (mode == MarkAnswersMode.MARK) {
            markAnswers(type);
        } else if (mode == MarkAnswersMode.UNMARK) {
            view.updateStyle(UserAnswerType.DEFAULT);
        }
    }

    private void markAnswers(MarkAnswersType type) {
        List<Boolean> evaluatedAnswers = answerEvaluationSupplier.evaluateAnswer(model.getResponse());
        if (evaluatedAnswers.isEmpty()) {
            view.updateStyle(UserAnswerType.NONE);
        } else {
            Boolean isAnswerCorrect = evaluatedAnswers.get(0);
            if (type == MarkAnswersType.CORRECT && isAnswerCorrect) {
                view.updateStyle(UserAnswerType.CORRECT);
            } else if (type == MarkAnswersType.WRONG && !isAnswerCorrect) {
                view.updateStyle(UserAnswerType.WRONG);
            }
        }
    }

    @Override
    public void showAnswers(ShowAnswersType mode) {
        List<String> answers;
        if (mode == ShowAnswersType.CORRECT) {
            answers = model.getCorrectAnswers();
        } else if (mode == ShowAnswersType.USER) {
            answers = model.getCurrentAnswers();
        } else {
            return;
        }

        if (answers.size() > 0) {
            String answerToSet = answers.get(0);
            setContentOfItemOnView(answerToSet);
        } else {
            view.removeContent();
        }
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public void setContent(String itemId) {
        setContentOfItemOnView(itemId);
        model.addAnswer(itemId);
    }

    private void setContentOfItemOnView(String itemId) {
        SourcelistItemValue item = sourceListManagerAdapter.getItemById(itemId);
        view.setItemContent(item);
    }

    @Override
    public void removeContent() {
        view.removeContent();
        model.clearAnswers();
    }

    @Override
    public void setGapDimensions(HasDimensions size) {
        view.setWidth(size.getWidth());
        view.setHeight(size.getHeight());
    }
}
