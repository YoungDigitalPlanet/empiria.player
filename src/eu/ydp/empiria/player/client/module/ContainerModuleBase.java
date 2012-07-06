package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;

public abstract class ContainerModuleBase extends ModuleBase implements IContainerModule {

	private ModuleSocket moduleSocket;

	@Override
	public void initModule(Element element, ModuleSocket ms, InteractionEventsListener mil, BodyGeneratorSocket bgs) {
		readAttributes(element);
		applyIdAndClassToView(getView());
		this.moduleSocket = ms;
	}

	protected ModuleSocket getModuleSocket() {
		return moduleSocket;
	}

	@Override
	public HasParent getParentModule() {
		return moduleSocket.getParent(this);
	}

}
