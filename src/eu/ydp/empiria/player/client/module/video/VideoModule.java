package eu.ydp.empiria.player.client.module.video;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoModule extends SimpleModuleBase implements ISimpleModule {

	@Inject
	@ModuleScoped
	private VideoPresenter presenter;

	@Override
	public Widget getView() {
		return presenter.getView();
	}

	@Override
	protected void initModule(Element element) {
		presenter.start();
	}

}
