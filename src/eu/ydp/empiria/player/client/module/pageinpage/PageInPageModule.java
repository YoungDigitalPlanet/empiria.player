package eu.ydp.empiria.player.client.module.pageinpage;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class PageInPageModule implements ISimpleModule, Factory<PageInPageModule> {

	private Panel pagePanel;

	@Override
	public void initModule(Element element, ModuleSocket ms,
			ModuleInteractionListener mil) {
	}

	@Override
	public Widget getView() {
		if(pagePanel == null){
			pagePanel = new FlowPanel();
			pagePanel.setStyleName("qp-page-in-page");
		}
		return pagePanel;
	}

	@Override
	public PageInPageModule getNewInstance() {
		return new PageInPageModule();
	}

}
