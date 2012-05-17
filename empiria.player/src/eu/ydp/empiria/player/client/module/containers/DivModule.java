package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.ui.FlowPanel;

public class DivModule extends ContainerModuleBase<DivModule> {

	public DivModule(){
		super();
		panel = new FlowPanel();
		panel.setStyleName("qp-div");
	}

	@Override
	public DivModule getNewInstance() {
		return new DivModule();
	}

}
