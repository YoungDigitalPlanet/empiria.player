package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertiesCollectorTestHelper.ModuleInfo;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IUniqueModule;

public class FeedbackProcessingIntegrationJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
	
	
	private static final String MODULE_1 = "+MODULE_1";

	private static final String WRONG_MP3 = "wrong.mp3";

	private static final String ALLOK_MP3 = "allok.mp3";

	private static final String GOOD_MP3 = "good.mp3";

	private IModule sender;
	
	private Map<String, Outcome> variables;
	
	@Before
	@Override
	public void setUp() {
		setUp(new Class<?>[]{FeedbackRegistry.class, FeedbackActionCollector.class, 
								SoundActionProcessor.class, FeedbackConditionMatcher.class, 
								FeedbackPropertiesCollector.class}, new ProcessingModule());
	}
	
	@Test
	public void shouldProcessOkFeedback(){		
		ModuleInfo info = ModuleInfo.create(MODULE_1).setLastOk(true).setDone(1).setTodo(3).setErrors(0);		
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(1)));
		FeedbackAction action = actions.get(0);
		assertUrlAction(action, ActionType.NARRATION, GOOD_MP3);
	}
	
	@Test
	public void shouldIgnoreFeedbackBecauseIsOnUnselect(){		
		ModuleInfo info = ModuleInfo.create("-"+MODULE_1).setLastOk(true).setDone(1).setTodo(3).setErrors(0);		
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(0)));
	}
	
	@Test
	public void shouldProcessWrongFeedback(){		
		ModuleInfo info = ModuleInfo.create(MODULE_1).setLastOk(false).setDone(1).setTodo(3).setErrors(0);
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(1)));
		FeedbackAction action = actions.get(0);
		assertUrlAction(action, ActionType.NARRATION, WRONG_MP3);
	}
	
	@Test
	public void shouldProcessAllOkFeedback(){		
		ModuleInfo info = ModuleInfo.create(MODULE_1).setLastOk(true).setDone(3).setTodo(3).setErrors(0);
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(2)));
		String[] expectedUrls = new String[]{GOOD_MP3, ALLOK_MP3};
		int index = 0;
		
		for(String expectedUrl: expectedUrls){
			assertUrlAction(actions.get(index++), ActionType.NARRATION, expectedUrl);
		}
	}
	
	@Test
	public void shouldProcessOkFeedbackWhen_allAreDoneWithOneError(){		
		ModuleInfo info = ModuleInfo.create(MODULE_1).setLastOk(true).setDone(3).setTodo(3).setErrors(1);
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(1)));
		assertUrlAction(actions.get(0), ActionType.NARRATION, GOOD_MP3);
	}
	
	@Test
	public void shouldProcessWrongFeedbackWhen_allAreDoneWithOneError(){		
		ModuleInfo info = ModuleInfo.create(MODULE_1).setLastOk(false).setDone(3).setTodo(3).setErrors(1);
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(1)));
		assertUrlAction(actions.get(0), ActionType.NARRATION, WRONG_MP3);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<FeedbackAction> processUserAction(ModuleInfo info){
		sender = createSender(info);
		
		ModuleFeedbackProcessor processor = injector.getInstance(ModuleFeedbackProcessor.class);
		FeedbackRegistry feedbackRegistry = injector.getInstance(FeedbackRegistry.class);
		
		when(feedbackRegistry.hasFeedbacks())
			.thenReturn(true);
		
		processor.processFeedbacks(variables, (IUniqueModule) sender);
		
		ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
		verify(processor.soundProcessor, times(1)).processActions(argument.capture());
		
		return argument.getValue();
	}
	
	private void assertUrlAction(FeedbackAction actualAction, ActionType expectedType, String expectedUrl){
		assertThat(actualAction, is(instanceOf(ShowUrlAction.class)));
		ShowUrlAction showUrlAction = (ShowUrlAction) actualAction;
		assertThat(showUrlAction.getType(), is(equalTo(expectedType.getName())));
		assertThat(showUrlAction.getHref(), is(equalTo(expectedUrl)));
	}
	
	
	private IModule createSender(ModuleInfo info){
		FeedbackPropertiesCollectorTestHelper helper = new FeedbackPropertiesCollectorTestHelper();
		variables = helper.createOutcomeVariables(info);
		return helper.createUniqueModuleMock(null, info.getId(), variables);
	}
	
	private class ProcessingModule implements Module{

		private FeedbackRegistry feedbackRegistry = null;
		
		@Override
		public void configure(Binder binder) {
			//aaaa
		}
		
		@Provides
		public FeedbackRegistry getFeedbackRegistry(){
			if(feedbackRegistry == null){
				FeedbackRegistry registry = mock(FeedbackRegistry.class);
				List<Feedback> feedbackList =  new FeedbackCreator(GOOD_MP3, WRONG_MP3, ALLOK_MP3).createFeedbackList();
				
				when(registry.isModuleRegistered(sender)).thenReturn(true);
				when(registry.getModuleFeedbacks(sender)).thenReturn(feedbackList);
				this.feedbackRegistry = registry;
			}
			
			return this.feedbackRegistry;
		}
		
		@Provides
		public SoundActionProcessor getSoundActionProcessor(){
			SoundActionProcessor processor = new SoundActionProcessor();
			injector.injectMembers(processor);
			return spy(processor);
		}	
		
	}
	
}
