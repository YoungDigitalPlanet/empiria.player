package eu.ydp.empiria.player.client.module.colorfill;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.colorfill.presenter.UserToResponseAreaMapper;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

public class ColorfillModelUpdater {
	
	
	@Inject @ModuleScoped
	private UserToResponseAreaMapper areaMapper;

	public void updateUserAnswers(Area userArea) {
		areaMapper.updateMappings(userArea);
	}
}
