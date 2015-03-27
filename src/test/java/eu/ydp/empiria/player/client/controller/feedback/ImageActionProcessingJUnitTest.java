package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ImageActionProcessor;

public class ImageActionProcessingJUnitTest extends ProcessingFeedbackActionTestBase {

	private ImageActionProcessor imageProcessor;

	@Test
	public void shouldProcessSingleImageAction() { // given
		List<FeedbackAction> actions = ActionListBuilder.create().addUrlAction(ActionType.NARRATION, "good.mp3").addUrlAction(ActionType.IMAGE, "good.jpg")
				.getList();

		initializeWithActions(actions);
		initializeModuleHierarchyWithImageProcessor();

		// when
		processor.processActions(source);

		// then
		ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);
		verify(imageProcessor).processSingleAction(argument.capture());
		FeedbackAction processedAction = argument.getValue();

		assertThat(argument.getAllValues().size(), is(equalTo(1)));
		assertThat(processedAction, is(instanceOf(ShowUrlAction.class)));
		assertThat(((ShowUrlAction) processedAction).getHref(), is(equalTo("good.jpg")));
		assertThat(collector.getActions().size(), is(equalTo(0)));
	}

	@Test
	public void shouldProcessManyImageActions() {
		// given
		List<FeedbackAction> actions = ActionListBuilder.create().addUrlAction(ActionType.NARRATION, "good.mp3").addUrlAction(ActionType.IMAGE, "good.jpg")
				.addUrlAction(ActionType.NARRATION, "allok.mp3").addUrlAction(ActionType.IMAGE, "bad.jpg").getList();

		initializeWithActions(actions);
		initializeModuleHierarchyWithImageProcessor();

		// when
		processor.processActions(source);

		// then
		ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);
		verify(imageProcessor, times(2)).processSingleAction(argument.capture());
		assertThat(collector.getActions().size(), is(equalTo(0)));
		List<FeedbackAction> processedActions = argument.getAllValues();

		FeedbackAction processedAction1 = processedActions.get(0);
		assertThat(processedAction1, is(instanceOf(ShowUrlAction.class)));
		assertThat(((ShowUrlAction) processedAction1).getHref(), is(equalTo("good.jpg")));

		FeedbackAction processedAction2 = processedActions.get(1);
		assertThat(processedAction2, is(instanceOf(ShowUrlAction.class)));
		assertThat(((ShowUrlAction) processedAction2).getHref(), is(equalTo("bad.jpg")));
	}

	private void initializeModuleHierarchyWithImageProcessor() {
		HasChildren parentModule = mock(HasChildren.class);
		imageProcessor = spy(injector.getInstance(ImageActionProcessor.class));

		when(source.getParentModule()).thenReturn(parentModule);
		when(parentModule.getChildren()).thenReturn(Lists.newArrayList(mock(IModule.class), mock(IModule.class), source, imageProcessor));
	}

}
