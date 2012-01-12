package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public interface ISingleViewModule extends IModule {
	
	public Widget getView();
	
}
