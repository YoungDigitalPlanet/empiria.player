package eu.ydp.empiria.player.client.module.sourcelist;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class SourceListModule extends SimpleModuleBase implements Factory<SourceListModule> {

	@Inject
	private ModuleFactory moduleFactory;



	@Override
	public SourceListModule getNewInstance() {
		return moduleFactory.getSourceListModule();
	}



	@Override
	public Widget getView() {
		return new FlowPanel();
	}



	@Override
	protected void initModule(Element element) {
		//TODO
	}
}
