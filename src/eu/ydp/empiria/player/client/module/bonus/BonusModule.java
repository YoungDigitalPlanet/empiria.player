package eu.ydp.empiria.player.client.module.bonus;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.FeedbackActionConditions;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackMediator;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class BonusModule extends SimpleModuleBase implements PowerFeedbackBonusClient {

	@Inject
	private FeedbackActionConditions actionConditions;
	@Inject
	@PageScoped
	private PowerFeedbackMediator mediator;
	@Inject
	@ModuleScoped
	private BonusProvider bonusProvider;
	@Inject
	private BonusPopupPresenter bonusPopupPresenter;

	private boolean pageAllOkCompleted = false;
	private boolean hadCurrentPageError = false;

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
		hadCurrentPageError = false;
	}

	@Override
	public void terminatePowerFeedback() {
		bonusPopupPresenter.closeClicked();
	}

	@Override
	public void processUserInteraction() {
		hadCurrentPageError = hasCurrentPageErrorInHistory();
		if (isPageAllOkFirstTime()) {
			Bonus bonus = bonusProvider.next();
			bonus.execute();
			setPageAllOkCompleted();
		}
	}

	private boolean hasCurrentPageErrorInHistory() {
		return hadCurrentPageError || actionConditions.hasCurrentPageErrors();
	}

	private boolean isPageAllOkFirstTime() {
		return !pageAllOkCompleted && actionConditions.isPageAllOkWithoutPreviousErrors() && !hadCurrentPageError;
	}

	private void setPageAllOkCompleted() {
		pageAllOkCompleted = true;
	}

}
