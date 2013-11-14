package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceImpl;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceView;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceViewImpl;
import eu.ydp.empiria.player.client.module.connection.DistanceCalculator;
import eu.ydp.empiria.player.client.module.connection.LineSegmentChecker;
import eu.ydp.empiria.player.client.module.connection.RectangleChecker;
import eu.ydp.empiria.player.client.module.connection.SurfaceCleaner;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItemPairFinder;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenterImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModuleViewImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionViewVertical;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public class ConnectionGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		install(new GinFactoryModuleBuilder().implement(ConnectionItem.class, ConnectionItemImpl.class)
				.implement(ConnectionSurface.class, ConnectionSurfaceImpl.class).implement(MultiplePairModuleView.class, ConnectionModuleViewImpl.class)
				.implement(ConnectionView.class, ConnectionViewVertical.class).implement(ConnectionSurfaceView.class, ConnectionSurfaceViewImpl.class)
				.build(ConnectionModuleFactory.class));

		install(new GinFactoryModuleBuilder().build(ConnectionItemsFactory.class));
		bind(ConnectionModulePresenter.class).to(ConnectionModulePresenterImpl.class);
		bind(ConnectionView.class).to(ConnectionViewVertical.class);
		bind(MultiplePairModuleView.class).to(ConnectionModuleViewImpl.class);
		bind(ConnectionModuleStructure.class);
		bind(RectangleChecker.class).in(Singleton.class);
		bind(LineSegmentChecker.class).in(Singleton.class);
		bind(DistanceCalculator.class).in(Singleton.class);
		bind(SurfaceCleaner.class).in(Singleton.class);
		bind(ConnectionItemPairFinder.class).in(Singleton.class);
	}

}
