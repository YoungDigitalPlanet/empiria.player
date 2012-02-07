package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.IContainerModule;

public class ItemBodyModule extends ContainerModuleBase {

	public ItemBodyModule(){
		super();
		panel.setStyleName("qp-item-body");
	}

}
