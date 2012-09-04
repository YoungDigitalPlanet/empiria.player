package eu.ydp.empiria.player.client;

import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.util.SchedulerImpl;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.scheduler.Scheduler;
import eu.ydp.gwtutil.client.test.AbstractTestModule;

public class TestGuiceModule extends AbstractTestModule{

	public TestGuiceModule(Class<?>... classToOmit){
		super(classToOmit);
	}

	@Override
	public void configure() {
		bind(Scheduler.class).to(SchedulerImpl.class).in(Singleton.class);
		bind(EventsBus.class).to(PlayerEventsBus.class).in(Singleton.class);
	}

}
