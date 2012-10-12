package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;

public abstract class ContainerModuleBase extends ModuleBase implements IContainerModule {

	@Override
	public void initModule(Element element, ModuleSocket ms, InteractionEventsListener mil, BodyGeneratorSocket bgs) {
		initModule(ms);
		readAttributes(element);
		applyIdAndClassToView(getView());
	}
	
	@Override
	public List<IModule> getChildrenModules() {
		return getModuleSocket().getChildren(this);
	}

}
