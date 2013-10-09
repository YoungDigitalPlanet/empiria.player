package eu.ydp.empiria.player.client.module.progressbonus.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ProgressBonusPresenter {

	@Inject
	@ModuleScoped
	private ProgressBonusView view;

	public void showImage(ShowImageDTO dto) {
		view.showImage(dto);
	}
}
