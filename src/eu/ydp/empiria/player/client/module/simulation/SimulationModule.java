package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;

public class SimulationModule extends SimpleModuleBase implements Factory<SimulationModule> {

	protected SimulationModuleView view;

	@Override
	public SimulationModule getNewInstance() {
		return new SimulationModule();
	}

	@Override
	public Widget getView() {
		if (view == null) {
			view = new SimulationModuleView();
		}
		return view;
	}

	@Override
	protected void initModule(Element element) {
		String src = element.getAttribute("src");
		final CreateJsLoader loader = new CreateJsLoader();

		loader.addCompleteHandler(new CompleteHandler() {

			@Override
			public void onComplete() {
				addChildView(loader.getContent().getCanvas());
			}
		});

		loader.load(src);
	}

	protected void addChildView(IsWidget child) {
		SimulationModuleView parentView = (SimulationModuleView) getView();

		if (parentView != null) {
			parentView.add(child);
		}
	}
}
