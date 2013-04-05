package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.preloader.Preloader;

public class SimulationPreloader implements IsWidget {

	@Inject
	private Preloader preloader;

	public void hidePreloaderAndRemoveFromParent(){
		preloader.hide();
		preloader.asWidget().removeFromParent();
	}

	public void show(int preloaderWidth, int preloaderHeight){
		preloader.setPreloaderSize(preloaderWidth, preloaderHeight);
		preloader.show();
	}

	@Override
	public Widget asWidget() {
		return preloader.asWidget();
	}
}
