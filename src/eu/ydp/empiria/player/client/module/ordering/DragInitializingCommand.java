package eu.ydp.empiria.player.client.module.ordering;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DragInitializingCommand implements ScheduledCommand {

	@Inject
	@ModuleScoped
	private DragController dragController;
	@Inject
	@ModuleScoped
	private OrderInteractionPresenter presenter;

	@Override
	public void execute() {
		dragController.init(presenter.getOrientation());
	}
}