package eu.ydp.empiria.player.client;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutorsStopper;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SingleMediaPlayback;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnectorListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.*;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.CenterPositionFinder;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.RangeCreator;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.ViewportHelper;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.WidgetSizeHelper;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.StickiePresenter;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.module.ModuleScopedLazyProvider;
import eu.ydp.empiria.player.client.module.connection.presenter.translation.SurfaceRectangleFinder;
import eu.ydp.empiria.player.client.module.drawing.command.ClearAllDrawCommand;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandFactory;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxButtonCreator;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxView;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.ToolFactory;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasView;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingChildView;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;
import eu.ydp.empiria.player.client.module.media.external.FullscreenVideoMediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.ExternalFullscreenVideoImpl;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.bus.PlayerEventsBus;
import eu.ydp.gwtutil.client.date.DateService;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;
import eu.ydp.gwtutil.client.scheduler.Scheduler;
import eu.ydp.gwtutil.client.scheduler.SchedulerMockImpl;
import eu.ydp.gwtutil.client.timer.Timer;
import eu.ydp.gwtutil.client.timer.TimerAccessibleMock;
import eu.ydp.gwtutil.test.AbstractMockingTestModule;
import eu.ydp.gwtutil.test.mock.ReturnsJavaBeanAnswers;

import static org.mockito.Mockito.withSettings;

public class TestWithMocksGuiceModule extends AbstractMockingTestModule {

	public TestWithMocksGuiceModule() {
		super();
	}

	public TestWithMocksGuiceModule(Class<?>... ignoreClassList) {
		super(ignoreClassList);
	}

	public TestWithMocksGuiceModule(Class<?>[] classToOmit, Class<?>[] classToSpy) {
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
		bindToSingletonOrMockInstance(LabellingView.class);
		bindToSingletonOrMockInstance(LabellingChildView.class);
		bindToSingletonOrMockInstance(LabellingModuleJAXBParserFactory.class);
		bindToSingletonOrMockInstance(PageScopeFactory.class);
		bindToSingletonOrMockInstance(StyleSocket.class);
		bindToSingletonOrMockInstance(ToolFactory.class);
		bindToSingletonOrMockInstance(DrawCommandFactory.class);
		bindToSingletonOrMockInstance(ModuleScopeStack.class);
		bindToSingletonOrMockInstance(ClearAllDrawCommand.class);
		bindToSingletonOrMockInstance(ModuleScopedLazyProvider.class);
		bindToSingletonOrMockInstance(Logger.class);

		bindToSingletonInModuleScoped(ToolboxView.class);
		bindToSingletonInModuleScoped(ToolboxButtonCreator.class);
		bindToSingletonInModuleScoped(CanvasPresenter.class);
		bindToSingletonInModuleScoped(DrawingBean.class);
		bindToSingletonInModuleScoped(DrawCanvas.class);
		bindToSingletonInModuleScoped(CanvasView.class);

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
		bindToClassOrMockProvider(SurfaceRectangleFinder.class);

		install(new FactoryModuleBuilder().implement(IStickieView.class, StickieView.class).implement(IStickiePresenter.class, StickiePresenter.class)
				.build(StickieFactory.class));
	}

}
