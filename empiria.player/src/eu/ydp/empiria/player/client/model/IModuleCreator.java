package eu.ydp.empiria.player.client.model;

import com.google.gwt.dom.client.Element;

import eu.ydp.empiria.player.client.module.IModuleEventsListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;

public interface IModuleCreator {

	public boolean isSupported(String name);
	
	public Element createModule(com.google.gwt.xml.client.Element element, ModuleSocket moduleSocket, IModuleEventsListener moduleEventsListener);
}
