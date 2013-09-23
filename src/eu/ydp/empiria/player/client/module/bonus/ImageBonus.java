package eu.ydp.empiria.player.client.module.bonus;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.geom.Size;

public class ImageBonus implements BonusWithAsset {

	@Inject @ModuleScoped
	private BonusPopupPresenter presenter;
	@Inject
	private EmpiriaPaths empiriaPaths;
	
	private String url;
	private Size size;
	
	@Override
	public void setAsset(String url, Size size){
		this.url = empiriaPaths.getCommonsFilePath(url);
		this.size = size;
	}
	
	@Override
	public void execute() {
		presenter.showImage(url, size);
	}

}
