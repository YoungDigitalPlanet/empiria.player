package eu.ydp.empiria.player.client.module.slideshow;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowPlayerPresenter;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.service.json.IJSONService;

public class SlideshowPlayerModule extends SimpleModuleBase {

	private final SlideshowController controller;
	private final SlideshowModuleStructure moduleStructure;
	private final IJSONService ijsonService;
	private final SlideshowPlayerPresenter presenter;

	@Inject
	public SlideshowPlayerModule(@ModuleScoped SlideshowController slideshowController, @ModuleScoped SlideshowPlayerPresenter presenter,
			SlideshowModuleStructure moduleStructure,
			IJSONService ijsonService) {
		this.controller = slideshowController;
		this.presenter = presenter;
		this.moduleStructure = moduleStructure;
		this.ijsonService = ijsonService;
	}

	@Override
	public Widget getView() {
		return presenter.getView();
	}

	@Override
	protected void initModule(Element element) {
		moduleStructure.createFromXml(element.toString(), ijsonService.createArray());
		SlideshowBean bean = moduleStructure.getBean().getSlideshowBean();

		presenter.init(bean);
		controller.init(bean.getSlideBeans());
	}
}
