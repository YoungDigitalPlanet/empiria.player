package eu.ydp.empiria.player.client.module.bonus;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackMediator;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class BonusModule extends SimpleModuleBase implements PowerFeedbackBonusClient {

	@Inject
	private OutcomeAccessor outcomeAccessor;
	@Inject
	@ModuleScoped
	private PowerFeedbackMediator mediator;
	@Inject
	@ModuleScoped
	private BonusProvider bonusProvider;
	@Inject
	@ModuleScoped
	private BonusPopupPresenter bonusPopupPresenter;
	private boolean pageAllOkCompleted = false;

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
		return !pageAllOkCompleted && outcomeAccessor.isPageAllOk();
	}

	private void setPageAllOkCompleted() {
		pageAllOkCompleted = true;
	}

}
