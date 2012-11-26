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

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.TextActionProcessor;

public class TextActionProcessingJUnitTest extends ProcessingFeedbackActionTestBase {
	
	private TextActionProcessor textProcessor;
	
	@Test
	public void shouldProcessSingleTextAction(){
		/*
		//given
		List<FeedbackAction> actions = ActionListBuilder.create().
											addUrlAction(ActionType.NARRATION, "good.mp3").
											addTextAction("Good").
											getList();
		initializeWithActions(actions);
		initializeModuleHierarchyWithTextProcessor();
		
		//when
		processor.processActions(source);
		
		//then
		ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);		
		verify(textProcessor).processSingleAction(argument.capture());
		FeedbackAction processedAction = argument.getValue();
		
		assertThat(argument.getAllValues().size(), is(equalTo(1)));
		assertThat(processedAction, is(instanceOf(ShowTextAction.class)));
		assertThat(((ShowTextAction) processedAction).getText(), is(equalTo("Good")));
		assertThat(collector.getActions().size(), is(equalTo(0)));
		*/
	}
	
	@Test
	public void shouldProcessManyTextActions(){
		/*
		//given
		String[] actionTexts = new String[]{"Good", "Very good!!!"};
		List<FeedbackAction> actions = ActionListBuilder.create().
											addUrlAction(ActionType.NARRATION, "good.mp3").
											addTextAction(actionTexts[0]).
											addUrlAction(ActionType.NARRATION, "allok.mp3").
											addTextAction(actionTexts[1]).
											getList();
		
		initializeWithActions(actions);
		initializeModuleHierarchyWithTextProcessor();
		
		//when
		processor.processActions(source);
		
		//then
		ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);
		verify(textProcessor, times(2)).processSingleAction(argument.capture());
		assertThat(collector.getActions().size(), is(equalTo(0)));
		List<FeedbackAction> processedActions = argument.getAllValues();
		
		for (int i = 0; i < actionTexts.length; i++) {
			FeedbackAction processedAction = processedActions.get(i);
			String actionText = actionTexts[i];
			
			assertThat(processedAction, is(instanceOf(ShowTextAction.class)));
			assertThat(((ShowTextAction) processedAction).getText(), is(equalTo(actionText)));
		}
		*/
	}
	
	private void initializeModuleHierarchyWithTextProcessor(){
		HasChildren parentModule = mock(HasChildren.class);
		textProcessor = spy(injector.getInstance(TextActionProcessor.class));
		
		when(source.getParentModule()).thenReturn(parentModule);
		when(parentModule.getChildren()).thenReturn(Lists.newArrayList(
														mock(IModule.class),
														mock(IModule.class),
														source, 
														textProcessor));		
	}
	
}
