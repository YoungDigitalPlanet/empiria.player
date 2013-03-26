package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.withSettings;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.CenterPositionFinder;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.RangeCreator;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.ViewportHelper;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.WidgetSizeHelper;
import eu.ydp.empiria.player.client.module.media.external.FullscreenVideoMediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.ExternalFullscreenVideoImpl;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
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
		bindToSingletonOrMockInstance(EventsBus.class);
		bindToSingletonOrMockInstance(StyleNameConstants.class);
		bindToSingletonOrMockInstance(ExternalFullscreenVideoConnector.class);
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
	}

}
