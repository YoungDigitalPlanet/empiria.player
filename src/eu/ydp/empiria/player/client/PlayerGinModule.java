package eu.ydp.empiria.player.client;


import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.controller.data.StyleDataSourceManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageView;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.scheduler.Scheduler;
import eu.ydp.empiria.player.client.util.scheduler.SchedulerImpl;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.empiria.player.client.view.player.PageViewCache;
import eu.ydp.empiria.player.client.view.player.PlayerContentView;
import eu.ydp.empiria.player.client.view.player.PlayerViewSocket;

public class PlayerGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(StyleSocket.class).to(StyleDataSourceManager.class).in(
				Singleton.class);
		bind(StyleDataSourceManager.class).in(Singleton.class);
		bind(PlayerViewSocket.class).to(PlayerContentView.class).in(
				Singleton.class);
		bind(PlayerContentView.class).in(Singleton.class);

		// this is unnecessary, but left for clarity - if GIN can't find a
		// binding for a class, it falls back to calling GWT.create() on that
		// class
		bind(DataSourceManager.class);
		bind(EventsBus.class).to(PlayerEventsBus.class).in(Singleton.class);
		bind(DefaultMediaProcessorExtension.class).in(Singleton.class);
		bind(MultiPageView.class).in(Singleton.class);
		bind(PageViewCache.class).in(Singleton.class);
		bind(PageControllerCache.class).in(Singleton.class);
		bind(StyleNameConstants.class).in(Singleton.class);
		bind(MainFlowProcessor.class).in(Singleton.class);
		bind(Scheduler.class).to(SchedulerImpl.class).in(Singleton.class);
		bind(Page.class).in(Singleton.class);
	}

}
