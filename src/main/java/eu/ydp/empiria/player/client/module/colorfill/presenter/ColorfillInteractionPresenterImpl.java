package eu.ydp.empiria.player.client.module.colorfill.presenter;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillModelProxy;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.Map;

public class ColorfillInteractionPresenterImpl implements ColorfillInteractionPresenter {

    private final ColorfillInteractionView interactionView;
    private final ColorfillModelProxy modelProxy;
    private final ColorfillViewBuilder colorfillViewBuilder;
    private final ColorButtonsController colorButtonsController;
    private final UserToResponseAreaMapper areaMapper;

    private ColorfillInteractionBean bean;
    private boolean locked;

    @Inject
    public ColorfillInteractionPresenterImpl(ColorfillViewBuilder colorfillViewBuilder, ColorButtonsController colorButtonsController,
                                             @ModuleScoped ColorfillInteractionView interactionView, @ModuleScoped ColorfillModelProxy modelProxy,
                                             @ModuleScoped UserToResponseAreaMapper areaMapper) {
        this.colorfillViewBuilder = colorfillViewBuilder;
        this.colorButtonsController = colorButtonsController;
        this.interactionView = interactionView;
        this.modelProxy = modelProxy;
        this.areaMapper = areaMapper;
    }

    @Override
    public void bindView() {
        colorfillViewBuilder.buildView(bean, this);
    }

    @Override
    public void imageColorChanged(Area area) {
        if (!locked) {
            ColorModel currentSelectedButtonColor = colorButtonsController.getCurrentSelectedButtonColor();
            if (currentSelectedButtonColor != null) {
                interactionView.setColor(area, currentSelectedButtonColor);
                modelProxy.updateUserAnswers();
                areaMapper.updateMappings(area);
            }
        }
    }

    @Override
    public void buttonClicked(ColorModel color) {
        colorButtonsController.colorButtonClicked(color);
    }

    @Override
    public void reset() {
        interactionView.reset();
        areaMapper.reset();
    }

    @Override
    public void setModel(ColorfillInteractionModuleModel model) {
        // Unused - will be removed in the future
    }

    @Override
    public void setModuleSocket(ModuleSocket socket) {
    }

    @Override
    public void setBean(ColorfillInteractionBean bean) {
        this.bean = bean;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
        if (type == MarkAnswersType.CORRECT) {
            if (mode == MarkAnswersMode.MARK) {
                Iterable<Area> userClickPoints = areaMapper.mapResponseToUser(modelProxy.getUserCorrectAnswers());
                interactionView.markCorrectAnswers(Lists.newArrayList(userClickPoints));
            } else {
                interactionView.unmarkCorrectAnswers();
            }
        } else {
            if (mode == MarkAnswersMode.MARK) {
                Iterable<Area> userClickPoints = areaMapper.mapResponseToUser(modelProxy.getUserWrongAnswers());
                interactionView.markWrongAnswers(Lists.newArrayList(userClickPoints));
            } else {
                interactionView.unmarkWrongAnswers();
            }
        }
    }

    @Override
    public void showAnswers(ShowAnswersType mode) {
        if (mode == ShowAnswersType.USER) {
            interactionView.showUserAnswers();
            Map<Area, ColorModel> areasWithColors = modelProxy.getUserAnswers();
            interactionView.setColors(areasWithColors);
        } else if (mode == ShowAnswersType.CORRECT) {
            interactionView.showCorrectAnswers();
        }
    }

    @Override
    public Widget asWidget() {
        return interactionView.asWidget();
    }
}
