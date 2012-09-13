package eu.ydp.empiria.player.client.module.pageinpage;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class PageInPageModule extends SimpleModuleBase implements Factory<PageInPageModule> {

	private Panel pagePanel;
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();

	@Override
	public void initModule(Element element) { //NOPMD
	}

	@Override
	public Widget getView() {
		if (pagePanel == null) {
			pagePanel = PlayerGinjector.INSTANCE.getMultiPage().getView();
			pagePanel.setStyleName(styleNames.QP_PAGE_IN_PAGE());
		}
		return pagePanel;
	}

	@Override
	public PageInPageModule getNewInstance() {
		return new PageInPageModule();
	}

}
