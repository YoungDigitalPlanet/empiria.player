package eu.ydp.empiria.player.client.util.scheduler;

import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class SchedulerImpl implements Scheduler {
	private final com.google.gwt.core.client.Scheduler schedulerObject = com.google.gwt.core.client.Scheduler.get();

	@Override
	public void scheduleDeferred(ScheduledCommand cmd) {
		schedulerObject.scheduleDeferred(cmd);
	}

	@Override
	public void scheduleEntry(RepeatingCommand cmd) {
		schedulerObject.scheduleEntry(cmd);
	}

	@Override
	public void scheduleEntry(ScheduledCommand cmd) {
		schedulerObject.scheduleEntry(cmd);
	}

	@Override
	public void scheduleFinally(RepeatingCommand cmd) {
		schedulerObject.scheduleFinally(cmd);
	}

	@Override
	public void scheduleFinally(ScheduledCommand cmd) {
		schedulerObject.scheduleFinally(cmd);
	}

	@Override
	public void scheduleFixedDelay(RepeatingCommand cmd, int delayMs) {
		schedulerObject.scheduleFixedDelay(cmd, delayMs);
	}

	@Override
	public void scheduleFixedPeriod(RepeatingCommand cmd, int delayMs) {
		schedulerObject.scheduleFixedDelay(cmd, delayMs);
	}

	@Override
	public void scheduleIncremental(RepeatingCommand cmd) {
		schedulerObject.scheduleIncremental(cmd);
	}

}
