package eu.ydp.empiria.player.client.module.bonus;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackBonusClient;

public class BonusModule extends SimpleModuleBase implements PowerFeedbackBonusClient {

	@Override
	protected void initModule(Element element) {

	}

	@Override
	public Widget getView() {
		return null;
	}

	@Override
	public void resetPowerFeedback() {
	}

	@Override
	public void terminatePowerFeedback() {
	}

	@Override
	public void processUserInteraction() {
	}

	private boolean isPageAllOkFirstTime() {
		return false;
	}

	private void setPageAllOkCompleted() {

	}

}
