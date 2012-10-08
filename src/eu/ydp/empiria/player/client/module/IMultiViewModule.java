package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;

public interface IMultiViewModule extends IModule {

	public void initModule(ModuleSocket moduleSocket, InteractionEventsListener interactionEventsListener);
	
	public void addElement(Element element);
	
	public void installViews(List<HasWidgets> placeholders);
	
}
