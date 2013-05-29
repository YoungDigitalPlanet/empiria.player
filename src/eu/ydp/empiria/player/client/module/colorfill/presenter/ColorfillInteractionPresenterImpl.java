package eu.ydp.empiria.player.client.module.colorfill.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;

public class ColorfillInteractionPresenterImpl implements ColorfillInteractionPresenter {

	@Inject
	private ColorfillInteractionView interactionView;	
	
	@Override
	public void bindView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModel(ColorfillInteractionModuleModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBean(ColorfillInteractionBean bean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocked(boolean locked) {
		// TODO Auto-generated method stub

	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}

}
