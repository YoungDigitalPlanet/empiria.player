package eu.ydp.empiria.player.client.module.labelling;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;

public class LabellingModule extends AbstractActivityContainerModuleBase {

	@Inject LabellingBuilder builder;
	
	private SimplePanel container;
	
	@Override
	public void initModule(Element element, ModuleSocket ms, InteractionEventsListener mil, BodyGeneratorSocket bgs) {
		super.initModule(element, ms, mil, bgs);
		createBody(element, bgs);
	}

	private void createBody(Element element, BodyGeneratorSocket bgs) {
		container = new SimplePanel();
		builder.buildAndAttach(container, element, bgs);
	}
	
	@Override
	public HasWidgets getContainer() {
		return container;
	}

	@Override
	public Widget getView() {
		return container;
	}


}
