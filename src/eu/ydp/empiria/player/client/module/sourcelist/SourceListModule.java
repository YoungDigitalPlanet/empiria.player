package eu.ydp.empiria.player.client.module.sourcelist;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;

public class SourceListModule extends SimpleModuleBase implements Factory<SourceListModule> {

	@Inject
	private SourceListModuleStructure moduleStructure;

	@Inject
	private ModuleFactory moduleFactory;

	@Inject
	private SourceListPresenter presenter;

	@Override
	public SourceListModule getNewInstance() {
		return  moduleFactory.getSourceListModule();
	}

	@Override
	public Widget getView() {
		return presenter.asWidget();
	}

	@Override
	protected void initModule(Element element) {
		moduleStructure.createFromXml(element.toString());
		SourceListBean bean = moduleStructure.getBean();
		presenter.setBean(bean);
		presenter.setIModule(this);
		presenter.createAndBindUi();
	}
}
