package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public interface ISingleViewSimpleModule extends ISingleViewModule {

	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil);

}
