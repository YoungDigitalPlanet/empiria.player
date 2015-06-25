package eu.ydp.empiria.player.client.controller.feedback;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.player.FeedbackSoundPlayer;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.gin.factory.FeedbackModuleFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import org.junit.Before;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ProcessingFeedbackActionTestBase extends AbstractTestBaseWithoutAutoInjectorInit {

	protected FeedbackActionCollector collector;

	protected ModuleFeedbackProcessor processor;

	protected SoundActionProcessorMock soundProcessor;

	protected MathJaxNative mathJaxNative;

	protected IModule source;

	@Before
	@Override
	public void setUp() {
		setUp(new Class<?>[] { FeedbackActionCollector.class, SoundActionProcessor.class, FeedbackSoundPlayer.class }, new FeedbackActionCollectorModule());
		soundProcessor = spy(injector.getInstance(SoundActionProcessorMock.class));
	}

	protected void initializeWithActions(List<FeedbackAction> actions) {
		new Initializer().initWithActions(actions);
	}

	protected class FeedbackActionCollectorModule implements Module {

		@Override
		public void configure(Binder binder) {
			//
		}

		@Provides
		SoundActionProcessor getSoundActionProcessor() {
			return soundProcessor;
		}

		@Provides
		public FeedbackActionCollector getCollector() {
			return collector;
		}

		@Provides
		public FeedbackSoundPlayer getSoundPlayer() {
			return mock(FeedbackSoundPlayer.class);
		}

		@Provides
		public MathJaxNative getMathJaxNative() {
			return mathJaxNative;
		}

	}

	protected class Initializer {

		public void initWithActions(List<FeedbackAction> actions) {
			createCollector(actions);
			createProcessor();
			createMathJaxNative();
		}

		private void createMathJaxNative() {
			mathJaxNative = mock(MathJaxNative.class);
		}

		private void createCollector(List<FeedbackAction> actions) {
			source = mock(IModule.class);
			collector = new FeedbackActionCollector();
			collector.setSource(source);
			collector.appendActionsToSource(actions, source);
		}

		private void createProcessor() {
			InlineBodyGeneratorSocket inlineBodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
			FeedbackModuleFactory feedbackModuleFactory = injector.getInstance(FeedbackModuleFactory.class);
			processor = feedbackModuleFactory.getModuleFeedbackProcessor(inlineBodyGeneratorSocket);
		}

	}
}
