package eu.ydp.empiria.player.client.controller.multiview;

import com.google.inject.Inject;
import eu.ydp.gwtutil.client.scheduler.Scheduler;

import java.util.Set;

import static com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class PanelDetacher {

	@Inject
	private Scheduler scheduler;
	@Inject
	private PanelCache panelsCache;

	public void detach(Set<Integer> pagesToDetach) {
		for (final Integer pageIndex : pagesToDetach) {
			scheduleDeferredRemoveFromParent(pageIndex);
		}
	}

	private void scheduleDeferredRemoveFromParent(final int page) {
		scheduler.scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				panelsCache.getOrCreateAndPut(page).getValue().removeFromParent();
			}
		});
	}
}
