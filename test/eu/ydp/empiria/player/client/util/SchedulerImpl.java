package eu.ydp.empiria.player.client.util;

import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

import eu.ydp.empiria.player.client.util.scheduler.Scheduler;

public class SchedulerImpl implements Scheduler {

	@Override
	public void scheduleDeferred(ScheduledCommand cmd) {
		cmd.execute();

	}

	@Override
	public void scheduleEntry(RepeatingCommand cmd) {
		cmd.execute();

	}

	@Override
	public void scheduleEntry(ScheduledCommand cmd) {
		cmd.execute();

	}

	@Override
	public void scheduleFinally(RepeatingCommand cmd) {
		cmd.execute();

	}

	@Override
	public void scheduleFinally(ScheduledCommand cmd) {
		cmd.execute();

	}

	@Override
	public void scheduleFixedDelay(RepeatingCommand cmd, int delayMs) {
		cmd.execute();

	}

	@Override
	public void scheduleFixedPeriod(RepeatingCommand cmd, int delayMs) {
		cmd.execute();

	}

	@Override
	public void scheduleIncremental(RepeatingCommand cmd) {
		cmd.execute();

	}

}
