package eu.ydp.empiria.player.client.module.pageinpage;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ModuleProviderFactory;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class PageInPageModule extends SimpleModuleBase implements Factory<PageInPageModule> {

	private Panel pagePanel;

	@Inject
	protected StyleNameConstants styleNames;

	@Inject
	protected ModuleProviderFactory providerFactory;
	@Override
	public void initModule(Element element) { //NOPMD
	}

	@Override
	public Widget getView() {
		if (pagePanel == null) {
			pagePanel = providerFactory.getMultiPageController().get().getView();
			pagePanel.setStyleName(styleNames.QP_PAGE_IN_PAGE());
		}
		return pagePanel;
	}

	@Override
	public PageInPageModule getNewInstance() {
		return providerFactory.getPageInPageModule().get();
	}

}
