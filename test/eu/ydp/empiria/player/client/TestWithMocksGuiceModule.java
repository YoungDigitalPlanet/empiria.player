package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.withSettings;

import com.google.inject.assistedinject.FactoryModuleBuilder;

import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutorsStopper;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SingleMediaPlayback;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnectorListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.CenterPositionFinder;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.RangeCreator;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.ViewportHelper;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.WidgetSizeHelper;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.StickiePresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.translation.SurfaceDimensionsDelegate;
import eu.ydp.empiria.player.client.module.connection.presenter.translation.SurfaceDimensionsFinder;
import eu.ydp.empiria.player.client.module.connection.presenter.translation.SurfacePointTranslator;
import eu.ydp.empiria.player.client.module.connection.presenter.translation.SurfacePositionFinder;
import eu.ydp.empiria.player.client.module.media.external.FullscreenVideoMediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.ExternalFullscreenVideoImpl;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.gwtutil.client.date.DateService;
import eu.ydp.gwtutil.client.scheduler.Scheduler;
import eu.ydp.gwtutil.client.scheduler.SchedulerMockImpl;
import eu.ydp.gwtutil.client.timer.Timer;
import eu.ydp.gwtutil.client.timer.TimerAccessibleMock;
import eu.ydp.gwtutil.test.AbstractMockingTestModule;
import eu.ydp.gwtutil.test.mock.ReturnsJavaBeanAnswers;

public class TestWithMocksGuiceModule extends AbstractMockingTestModule {

	public TestWithMocksGuiceModule(){
		super();
	}
	
	public TestWithMocksGuiceModule(Class<?>... ignoreClassList) {
		super(ignoreClassList);
	}
	
	public TestWithMocksGuiceModule(Class<?>[] classToOmit, Class<?>[] classToSpy){
		super(classToOmit, classToSpy);
	}
	
	@Override
	public void configure() {
		bindToSingletonOrMockInstance(StyleNameConstants.class);
		bindToSingletonOrMockInstance(ExternalFullscreenVideoConnector.class);
		bindToSingletonOrMockInstance(EventsBus.class, PlayerEventsBus.class);
		bindToSingletonOrMockInstance(MediaConnector.class);
		bindToSingletonOrMockInstance(UniqueIdGenerator.class);
		bindToSingletonOrMockInstance(ExternalMediaEngine.class);
		bindToSingletonOrMockInstance(MediaConnectorListener.class, ExternalMediaEngine.class);
		bindToSingletonOrMockInstance(SingleMediaPlayback.class);
		bindToSingletonOrMockInstance(DateService.class);
		bindToSingletonOrMockInstance(MediaExecutorsStopper.class);
		
		bindToClassOrMockProvider(IStickieProperties.class, withSettings().defaultAnswer(new ReturnsJavaBeanAnswers()));
		bindToClassOrMockProvider(IStickieView.class);
		bindToClassOrMockProvider(StickiesProcessorExtension.class);
		bindToClassOrMockProvider(IPlayerContainersAccessor.class);
		bindToClassOrMockProvider(CenterPositionFinder.class);
		bindToClassOrMockProvider(RangeCreator.class);
		bindToClassOrMockProvider(WidgetSizeHelper.class);
		bindToClassOrMockProvider(ViewportHelper.class);
		bindToClassOrMockProvider(FullscreenVideoExecutor.class);
		bindToClassOrMockProvider(FullscreenVideoMediaWrapper.class);
		bindToClassOrMockProvider(ExternalFullscreenVideoImpl.class);
		bindToClassOrMockProvider(Scheduler.class, SchedulerMockImpl.class);
		bindToClassOrMockProvider(Timer.class, TimerAccessibleMock.class);
		bindToClassOrMockProvider(SurfacePointTranslator.class);
		bindToClassOrMockProvider(SurfaceDimensionsFinder.class);
		bindToClassOrMockProvider(SurfacePositionFinder.class);
		bindToClassOrMockProvider(SurfaceDimensionsDelegate.class);		
		
		install(new FactoryModuleBuilder()
			.implement(IStickieView.class, StickieView.class)
			.implement(IStickiePresenter.class, StickiePresenter.class)
			.build(StickieFactory.class));	}

}
