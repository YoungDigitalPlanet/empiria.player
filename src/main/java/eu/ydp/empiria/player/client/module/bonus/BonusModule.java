package eu.ydp.empiria.player.client.module.bonus;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.processor.FeedbackActionConditions;
import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackMediator;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class BonusModule extends SimpleModuleBase implements PowerFeedbackBonusClient {

    @Inject
    private FeedbackActionConditions actionConditions;
    @Inject
    private OutcomeAccessor outcomeAccessor;

    @Inject
    @PageScoped
    private PowerFeedbackMediator mediator;
    @Inject
    @ModuleScoped
    private BonusProvider bonusProvider;
    @Inject
    private BonusPopupPresenter bonusPopupPresenter;

    private boolean pageAllOkCompleted = false;
    private int mistakesCount = 0;

    @Override
    protected void initModule(Element element) {
        mediator.registerBonus(this);
    }

    @Override
    public Widget getView() {
        return null;
    }

    @Override
    public void resetPowerFeedback() {
        pageAllOkCompleted = false;
        mistakesCount = outcomeAccessor.getCurrentPageMistakes();
    }

    @Override
    public void terminatePowerFeedback() {
        bonusPopupPresenter.closeClicked();
    }

    @Override
    public void processUserInteraction() {
        if (isPageAllOkFirstTime()) {
            Bonus bonus = bonusProvider.next();
            bonus.execute();
            setPageAllOkCompleted();
        }
    }

    private boolean isPageAllOkFirstTime() {
        return !pageAllOkCompleted && actionConditions.isPageAllOkWithoutPreviousErrors() && mistakesMade();
    }

    private boolean mistakesMade() {
        return outcomeAccessor.getCurrentPageMistakes() == mistakesCount;
    }

    private void setPageAllOkCompleted() {
        pageAllOkCompleted = true;
    }

}
