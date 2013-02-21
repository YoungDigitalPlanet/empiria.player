package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class SimulationModuleViewImpl extends Composite implements SimulationModuleView {

	private static SimulationModuleViewUiBinder uiBinder = GWT.create(SimulationModuleViewUiBinder.class);

	interface SimulationModuleViewUiBinder extends UiBinder<Widget, SimulationModuleViewImpl> {
	}

	@UiField
	protected Panel mainPanel;

	public SimulationModuleViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public void add(IsWidget child) {
		mainPanel.add(child);
	}

}
