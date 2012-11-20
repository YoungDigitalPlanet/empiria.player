package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.withSettings;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension;
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
		bindToClassOrMockProvider(IStickieProperties.class, withSettings().defaultAnswer(new ReturnsJavaBeanAnswers()));
		bindToClassOrMockProvider(IStickieView.class);
		bindToClassOrMockProvider(StickiesProcessorExtension.class);
		bindToClassOrMockProvider(IPlayerContainersAccessor.class);
	}

}
