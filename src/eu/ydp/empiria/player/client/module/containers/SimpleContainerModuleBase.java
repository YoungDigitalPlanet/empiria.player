package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ModuleSocket;

public abstract class SimpleContainerModuleBase<T> extends ActivityContainerModuleBase implements Factory<T>{

	protected Panel panel;

	public SimpleContainerModuleBase(){
		panel = new FlowPanel();
	}

	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener mil, BodyGeneratorSocket bodyGeneratorSocket) {
		super.initModule(element, moduleSocket, mil, bodyGeneratorSocket);

		readAttributes(element);
		applyIdAndClassToView(getView());

		bodyGeneratorSocket.generateBody(element, getContainer());
	}

	@Override
	public Widget getView() {
		return panel;
	}

	@Override
	public HasWidgets getContainer() {
		return panel;
	}

}
