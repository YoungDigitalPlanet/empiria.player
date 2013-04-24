package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class HtmlContainerModule extends SimpleContainerModuleBase<HtmlContainerModule> {

	protected String tag;
	
	public HtmlContainerModule(String tag){
		this.tag = tag;
		panel = new HTMLPanel(tag, "");
		panel.setStyleName("qp-"+tag);
	}	
		
	@Override
	public HasWidgets getContainer() {
		return panel;
	}

	@Override
	public Widget getView() {
		return panel;
	}

	@Override
	public HtmlContainerModule getNewInstance() {
		return new HtmlContainerModule(tag);
	}

}
