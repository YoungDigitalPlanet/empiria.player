package eu.ydp.empiria.player.client.util.scheduler;

import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public interface Scheduler {
	  public abstract void scheduleDeferred(ScheduledCommand cmd);

	  /**
	   * An "entry" command will be executed before GWT-generated code is invoked by
	   * the browser's event loop. The {@link RepeatingCommand} will be called once
	   * per entry from the event loop until <code>false</code> is returned. This
	   * type of command is appropriate for instrumentation or code that needs to
	   * know when "something happens."
	   * <p>
	   * If an entry command schedules another entry command, the second command
	   * will be executed before control flow continues to the GWT-generated code.
	   */
	  public abstract void scheduleEntry(RepeatingCommand cmd);

	  /**
	   * An "entry" command will be executed before GWT-generated code is invoked by
	   * the browser's event loop. This type of command is appropriate for code that
	   * needs to know when "something happens."
	   * <p>
	   * If an entry command schedules another entry command, the second command
	   * will be executed before control flow continues to the GWT-generated code.
	   */
	  public abstract void scheduleEntry(ScheduledCommand cmd);

	  /**
	   * A "finally" command will be executed before GWT-generated code returns
	   * control to the browser's event loop. The {@link RepeatingCommand#execute()}
	   * method will be called once per exit to the event loop until
	   * <code>false</code> is returned. This type of command is appropriate for
	   * instrumentation or cleanup code.
	   * <p>
	   * If a finally command schedules another finally command, the second command
	   * will be executed before control flow returns to the browser.
	   */
	  public abstract void scheduleFinally(RepeatingCommand cmd);

	  /**
	   * A "finally" command will be executed before GWT-generated code returns
	   * control to the browser's event loop. This type of command is used to
	   * aggregate small amounts of work before performing a non-recurring,
	   * heavyweight operation.
	   * <p>
	   * If a finally command schedules another finally command, the second command
	   * will be executed before control flow returns to the browser.
	   * <p>
	   * Consider the following:
	   *
	   * <pre>
	   * try {
	   *   nativeEventCallback(); // Calls scheduleFinally one or more times
	   * } finally {
	   *   executeFinallyCommands();
	   * }
	   * </pre>
	   *
	   * @see com.google.gwt.dom.client.StyleInjector
	   */
	  public abstract void scheduleFinally(ScheduledCommand cmd);

	  /**
	   * Schedules a repeating command that is scheduled with a constant delay. That
	   * is, the next invocation of the command will be scheduled for
	   * <code>delayMs</code> milliseconds after the last invocation completes.
	   * <p>
	   * For example, assume that a command takes 30ms to run and a 100ms delay is
	   * provided. The second invocation of the command will occur at 130ms after
	   * the first invocation starts.
	   *
	   * @param cmd the command to execute
	   * @param delayMs the amount of time to wait after one invocation ends before
	   *          the next invocation
	   */
	  public abstract void scheduleFixedDelay(RepeatingCommand cmd, int delayMs);

	  /**
	   * Schedules a repeating command that is scheduled with a constant
	   * periodicity. That is, the command will be invoked every
	   * <code>delayMs</code> milliseconds, regardless of how long the previous
	   * invocation took to complete.
	   *
	   * @param cmd the command to execute
	   * @param delayMs the period with which the command is executed
	   */
	  public abstract void scheduleFixedPeriod(RepeatingCommand cmd, int delayMs);

	  /**
	   * Schedules a repeating command that performs incremental work. This type of
	   * command is encouraged for long-running processes that perform computation
	   * or that manipulate the DOM. The commands in this queue are invoked many
	   * times in rapid succession and are then deferred to allow the browser to
	   * process its event queue.
	   *
	   * @param cmd the command to execute
	   */
	  public abstract void scheduleIncremental(RepeatingCommand cmd);
}
