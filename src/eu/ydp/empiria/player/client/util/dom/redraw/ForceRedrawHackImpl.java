package eu.ydp.empiria.player.client.util.dom.redraw;

import javax.annotation.PostConstruct;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ForceRedrawHackImpl implements ForceRedrawHack {

	private final FlowPanel widgetToAdd = new FlowPanel();

	@PostConstruct
	public void postConstruct() {
		widgetToAdd.setVisible(false);
		RootPanel.get().add(widgetToAdd);
	}

	@Override
	public void redraw(){
		widgetToAdd.setVisible(!widgetToAdd.isVisible());
	}
}
