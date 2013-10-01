package eu.ydp.empiria.player.client.module.progressbonus.presenter;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ProgressBonusPresenter {

	@ModuleScoped
	ProgressBonusView view;

	public void showImage(ShowImageDTO dto) {
		view.showImage(dto);
	}
}
