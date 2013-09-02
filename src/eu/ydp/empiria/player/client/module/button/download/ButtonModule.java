package eu.ydp.empiria.player.client.module.button.download;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.button.download.presenter.ButtonModulePresenter;
import eu.ydp.empiria.player.client.module.button.download.structure.ButtonBean;
import eu.ydp.empiria.player.client.module.button.download.structure.ButtonModuleStructure;
import eu.ydp.gwtutil.client.service.json.IJSONService;

public class ButtonModule extends SimpleModuleBase implements ISimpleModule {

	@Inject private ButtonModuleStructure buttonModuleStructure;
	@Inject private IJSONService ijsonService;
	@Inject private ButtonModulePresenter buttonModulePresenter;


	@Override
	public Widget getView() {
		return buttonModulePresenter.asWidget();
	}

	@Override
	protected void initModule(Element element) {
		buttonModuleStructure.createFromXml(element.toString(), ijsonService.createArray());
		ButtonBean bean = buttonModuleStructure.getBean();
		buttonModulePresenter.setBean(bean);
		buttonModulePresenter.init();
	}

}
