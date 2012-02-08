package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public interface ISingleViewWithBodyModule extends ISingleViewModule {

	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil, BodyGeneratorSocket bgs);

}
