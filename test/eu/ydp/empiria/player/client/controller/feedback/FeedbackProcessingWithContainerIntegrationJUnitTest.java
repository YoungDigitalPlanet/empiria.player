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

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

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
import eu.ydp.empiria.player.client.module.IModule;

public class FeedbackProcessingWithContainerIntegrationJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
	
	private static final String MODULE_2 = "+MODULE_2";

	private static final String MODULE_1 = "+MODULE_1";

	private static final String CONTAINER_ALL_OK_MP3 = "containerAllOk.mp3";

	private static final String CONTAINER_WRONG_MP3 = "containerWrong.mp3";

	private static final String CONTAINER_OK_MP3 = "containerOk.mp3";

	private static final String WRONG_MP3 = "wrong.mp3";

	private static final String ALLOK_MP3 = "allok.mp3";

	private static final String GOOD_MP3 = "good.mp3";

	private IModule sender;
	
	private IModule container;
	
	@Captor
	private ArgumentCaptor<List<FeedbackAction>> captor;
	
	@Before
	@Override
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		setUp(new Class<?>[]{FeedbackRegistry.class, FeedbackActionCollector.class, 
								SoundActionProcessor.class, FeedbackConditionMatcher.class, 
								FeedbackPropertiesCollector.class}, new ProcessingModule());
	}
	
	@Test
	public void shouldProcessOkFeedback(){		
		ModuleInfo[] infos = new ModuleInfo[]{
									ModuleInfo.create(MODULE_1).setLastOk(true).setDone(1).setTodo(3).setErrors(0),
									ModuleInfo.create(MODULE_2).setLastOk(false).setDone(2).setTodo(6).setErrors(0)
								};
		
		List<List<FeedbackAction>> capturedActions = processUserAction(infos);
		String[] expectedUrls = new String[]{GOOD_MP3, CONTAINER_OK_MP3};
		assertAllUrlActions(capturedActions, expectedUrls);
	}
	
	@Test
	public void shouldIgnoreUnselectFeedback(){		
		ModuleInfo[] infos = new ModuleInfo[]{
				ModuleInfo.create("-"+MODULE_1).setLastOk(true).setDone(1).setTodo(3).setErrors(0),
				ModuleInfo.create("-"+MODULE_2).setLastOk(false).setDone(2).setTodo(6).setErrors(0)
		};
		
		List<List<FeedbackAction>> capturedActions = processUserAction(infos);
		String[] expectedUrls = new String[]{};
		assertAllUrlActions(capturedActions, expectedUrls);
	}
	
	@Test
	public void shouldProcessWrongFeedback(){		
		ModuleInfo[] infos = new ModuleInfo[]{
									ModuleInfo.create(MODULE_1).setLastOk(false).setDone(1).setTodo(3).setErrors(0),
									ModuleInfo.create(MODULE_2).setLastOk(false).setDone(2).setTodo(6).setErrors(0)
								};
		
		List<List<FeedbackAction>> capturedActions = processUserAction(infos);
		String[] expectedUrls = new String[]{WRONG_MP3, CONTAINER_WRONG_MP3};
		assertAllUrlActions(capturedActions, expectedUrls);
	}
	
	@Test
	public void shouldProcessAllOkFeedback(){		
		ModuleInfo[] infos = new ModuleInfo[]{
									ModuleInfo.create(MODULE_1).setLastOk(true).setDone(3).setTodo(3).setErrors(0),
									ModuleInfo.create(MODULE_2).setLastOk(false).setDone(6).setTodo(6).setErrors(0)
								};
		
		List<List<FeedbackAction>> capturedActions = processUserAction(infos);
		String[] expectedUrls = new String[]{GOOD_MP3, ALLOK_MP3, CONTAINER_OK_MP3, CONTAINER_ALL_OK_MP3};
		assertAllUrlActions(capturedActions, expectedUrls);
	}
	
	@Test
	public void shouldProcessOkFeedbackWhen_allAreDoneWithOneError(){
		ModuleInfo[] infos = new ModuleInfo[]{
				ModuleInfo.create(MODULE_1).setLastOk(true).setDone(3).setTodo(3).setErrors(0),
				ModuleInfo.create(MODULE_2).setLastOk(false).setDone(6).setTodo(6).setErrors(1)
			};
		
		List<List<FeedbackAction>> capturedActions = processUserAction(infos);
		String[] expectedUrls = new String[]{GOOD_MP3, ALLOK_MP3, CONTAINER_OK_MP3};
		assertAllUrlActions(capturedActions, expectedUrls);
	}
	
	@Test
	public void shouldProcessWrongFeedbackWhen_allAreDoneWithOneError(){
		ModuleInfo[] infos = new ModuleInfo[]{
				ModuleInfo.create(MODULE_1).setLastOk(false).setDone(3).setTodo(3).setErrors(0),
				ModuleInfo.create(MODULE_2).setLastOk(false).setDone(6).setTodo(6).setErrors(1)
			};
		
		List<List<FeedbackAction>> capturedActions = processUserAction(infos);
		String[] expectedUrls = new String[]{WRONG_MP3, ALLOK_MP3, CONTAINER_WRONG_MP3};
		assertAllUrlActions(capturedActions, expectedUrls);
	}

	private List<List<FeedbackAction>> processUserAction(ModuleInfo[] infos) {
		FeedbackPropertiesCollectorTestHelper helper = new FeedbackPropertiesCollectorTestHelper();
		
		helper.createHierarchy(infos);
		sender = helper.getSender();
		container = helper.getContainer();
		
		ModuleFeedbackProcessor processor = injector.getInstance(ModuleFeedbackProcessor.class);
		processor.process(sender, helper.getVariables());
		
		verify(processor.soundProcessor, times(2)).processActions(captor.capture());
		
		return captor.getAllValues();
	}
	
	private void assertAllUrlActions(List<List<FeedbackAction>> allActions, String[] expectedUrls){
		int index = 0;
		int totalSize = 0;
		
		assertThat(allActions.size(), is(equalTo(2)));
		
		for(List<FeedbackAction> actions: allActions){
			totalSize += actions.size();
			for(FeedbackAction action: actions){
				assertUrlAction(action, ActionType.NARRATION, expectedUrls[index++]);
			}
		}
		
		assertThat(totalSize, is(equalTo(expectedUrls.length)));
	}
	
	private void assertUrlAction(FeedbackAction actualAction, ActionType expectedType, String expectedUrl){
		assertThat(actualAction, is(instanceOf(ShowUrlAction.class)));
		ShowUrlAction showUrlAction = (ShowUrlAction) actualAction;
		assertThat(showUrlAction.getType(), is(equalTo(expectedType.getName())));
		assertThat(showUrlAction.getHref(), is(equalTo(expectedUrl)));
	}
	
	private class ProcessingModule implements Module{

		@Override
		public void configure(Binder binder) {
			//all default bindings
		}
		
		@Provides
		public FeedbackRegistry getFeedbackRegistry(){
			FeedbackRegistry registry = mock(FeedbackRegistry.class);
			List<Feedback> moduleFeedbackList = new FeedbackCreator(GOOD_MP3, WRONG_MP3, ALLOK_MP3).createFeedbackList();
			List<Feedback> containerFeedbackList = new FeedbackCreator(CONTAINER_OK_MP3, CONTAINER_WRONG_MP3, CONTAINER_ALL_OK_MP3).createFeedbackList();
			
			when(registry.isModuleRegistered(sender)).thenReturn(true);
			when(registry.getModuleFeedbacks(sender)).thenReturn(moduleFeedbackList);
			
			when(registry.isModuleRegistered(container)).thenReturn(true);
			when(registry.getModuleFeedbacks(container)).thenReturn(containerFeedbackList);
			
			return registry;
		}
		
		@Provides
		public SoundActionProcessor getSoundActionProcessor(){
			SoundActionProcessor processor = new SoundActionProcessor();
			injector.injectMembers(processor);
			return spy(processor);
		}	
		
	}
}
