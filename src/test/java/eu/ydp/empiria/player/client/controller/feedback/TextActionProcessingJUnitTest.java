package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.Lists;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.jaxb.XmlContentMock;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.TextActionProcessor;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class TextActionProcessingJUnitTest extends ProcessingFeedbackActionTestBase {

	private TextActionProcessor textProcessor;

	@Test
	public void shouldProcessSingleTextAction() {
		// given
		List<FeedbackAction> actions = ActionListBuilder.create()
														.addUrlAction(ActionType.NARRATION, "good.mp3")
														.addTextAction(new XmlContentMock("Good"))
														.getList();

		initializeWithActions(actions);
		initializeModuleHierarchyWithTextProcessor();

		// when
		processor.processActions(source);

		// then
		ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);
		verify(textProcessor).processSingleAction(argument.capture());
		FeedbackAction processedAction = argument.getValue();

		assertThat(argument.getAllValues().size(), is(equalTo(1)));
		assertThat(processedAction, is(instanceOf(ShowTextAction.class)));
		assertThat(((ShowTextAction) processedAction).getContent().toString(), is(equalTo("Good")));
		assertThat(collector.getActions().size(), is(equalTo(0)));

		verify(mathJaxNative).renderMath();
		verifyNoMoreInteractions(mathJaxNative);
	}

	@Test
	public void shouldProcessManyTextActions() {
		// given
		XMLContent[] actionTexts = new XMLContent[] { new XmlContentMock("Good"), new XmlContentMock("Very good!!!") };

		List<FeedbackAction> actions = ActionListBuilder.create()
														.addUrlAction(ActionType.NARRATION, "good.mp3")
														.addTextAction(actionTexts[0])
														.addUrlAction(ActionType.NARRATION, "allok.mp3")
														.addTextAction(actionTexts[1])
														.getList();

		initializeWithActions(actions);
		initializeModuleHierarchyWithTextProcessor();

		// when
		processor.processActions(source);

		// then
		ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);
		verify(textProcessor, times(2)).processSingleAction(argument.capture());

		verify(mathJaxNative, times(2)).renderMath();
		verifyNoMoreInteractions(mathJaxNative);

		assertThat(collector.getActions().size(), is(equalTo(0)));
		List<FeedbackAction> processedActions = argument.getAllValues();

		for (int i = 0; i < actionTexts.length; i++) {
			FeedbackAction processedAction = processedActions.get(i);
			String actionText = actionTexts[i].toString();

			assertThat(processedAction, is(instanceOf(ShowTextAction.class)));
			assertThat(((ShowTextAction) processedAction).getContent().toString(), is(equalTo(actionText)));
		}
	}

	private void initializeModuleHierarchyWithTextProcessor() {
		HasChildren parentModule = mock(HasChildren.class);
		textProcessor = spy(injector.getInstance(TextActionProcessor.class));

		when(source.getParentModule()).thenReturn(parentModule);
		when(parentModule.getChildren()).thenReturn(Lists.newArrayList(mock(IModule.class), mock(IModule.class), source, textProcessor));
	}

}
