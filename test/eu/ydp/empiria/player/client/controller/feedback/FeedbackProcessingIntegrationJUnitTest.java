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

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.Lists;
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
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.gwtutil.client.operator.MatchOperator;

public class FeedbackProcessingIntegrationJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
	
	
	private static final String WRONG_MP3 = "wrong.mp3";

	private static final String ALLOK_MP3 = "allok.mp3";

	private static final String GOOD_MP3 = "good.mp3";

	private IModule sender;
	
	private Map<String, Outcome> variables;
	
	@Override
	public void setUp() {
		setUp(new Class<?>[]{FeedbackRegistry.class, FeedbackActionCollector.class, 
								SoundActionProcessor.class, FeedbackConditionMatcher.class, 
								FeedbackPropertiesCollector.class}, new ProcessingModule());
	}
	
	@Test
	public void shouldProcessOkFeedback(){		
		ModuleInfo info = ModuleInfo.create("MODULE_1").setLastOk(true).setDone(1).setTodo(3).setErrors(0);		
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(1)));
		FeedbackAction action = actions.get(0);
		assertUrlAction(action, ActionType.NARRATION, GOOD_MP3);
	}
	
	@Test
	public void shouldProcessWrongFeedback(){		
		ModuleInfo info = ModuleInfo.create("MODULE_1").setLastOk(false).setDone(1).setTodo(3).setErrors(0);
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(1)));
		FeedbackAction action = actions.get(0);
		assertUrlAction(action, ActionType.NARRATION, WRONG_MP3);
	}
	
	@Test
	public void shouldProcessAllOkFeedback(){		
		ModuleInfo info = ModuleInfo.create("MODULE_1").setLastOk(true).setDone(3).setTodo(3).setErrors(0);
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
		ModuleInfo info = ModuleInfo.create("MODULE_1").setLastOk(true).setDone(3).setTodo(3).setErrors(1);
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(1)));
		assertUrlAction(actions.get(0), ActionType.NARRATION, GOOD_MP3);
	}
	
	@Test
	public void shouldProcessWrongFeedbackWhen_allAreDoneWithOneError(){		
		ModuleInfo info = ModuleInfo.create("MODULE_1").setLastOk(false).setDone(3).setTodo(3).setErrors(1);
		List<FeedbackAction> actions = processUserAction(info);
		
		assertThat(actions.size(), is(equalTo(1)));
		assertUrlAction(actions.get(0), ActionType.NARRATION, WRONG_MP3);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<FeedbackAction> processUserAction(ModuleInfo info){
		sender = createSender(info);
		
		ModuleFeedbackProcessor processor = injector.getInstance(ModuleFeedbackProcessor.class);
		processor.process(sender, variables);
		
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

		@Override
		public void configure(Binder binder) {
				
		}
		
		@Provides
		public FeedbackRegistry getFeedbackRegistry(){
			FeedbackRegistry registry = mock(FeedbackRegistry.class);
			List<Feedback> feedbackList = new FeedbackCreator().createFeedbackList();
			
			when(registry.isModuleRegistered(sender)).thenReturn(true);
			when(registry.getModuleFeedbacks(sender)).thenReturn(feedbackList);
			
			return registry;
		}
		
		@Provides
		public SoundActionProcessor getSoundActionProcessor(){
			SoundActionProcessor processor = new SoundActionProcessor();
			injector.injectMembers(processor);
			return spy(processor);
		}	
		
	}
	
	private class FeedbackCreator{

		private List<Feedback> createFeedbackList(){
			List<Feedback> feedbackList = Lists.newArrayList();
			
			feedbackList.add(createSoundFeedback(FeedbackPropertyName.OK, GOOD_MP3));
			feedbackList.add(createSoundFeedback(FeedbackPropertyName.WRONG, WRONG_MP3));
			feedbackList.add(createSoundFeedback(FeedbackPropertyName.ALL_OK, ALLOK_MP3));
			
			return feedbackList;
		}
		
		private Feedback createSoundFeedback(FeedbackPropertyName name, String url){
			Feedback feedback = mock(Feedback.class);
			List<FeedbackAction> actionList = ActionListBuilder.create().
												addUrlAction(ActionType.NARRATION, url).
												getList();
			FeedbackCondition condition = getCondition(name);
			
			when(feedback.getActions()).thenReturn(actionList);
			when(feedback.getCondition()).thenReturn(condition);
			
			return feedback;
		}
		
		private FeedbackCondition getCondition(FeedbackPropertyName name){
			AndConditionBean andCondition = new AndConditionBean();
			PropertyConditionBean condition = new PropertyConditionBean();
			List<PropertyConditionBean> conditions = Lists.newArrayList();
			
			condition.setOperator(MatchOperator.EQUAL.getName());
			condition.setProperty(name.getName());
			conditions.add(condition);
			
			andCondition.setPropertyConditions(conditions);
			
			return andCondition;
		}
	}
	
}
