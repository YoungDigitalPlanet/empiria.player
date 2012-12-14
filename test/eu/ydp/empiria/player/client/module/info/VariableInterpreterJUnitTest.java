package eu.ydp.empiria.player.client.module.info;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.feedback.OutcomeCreator;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor;

public class VariableInterpreterJUnitTest extends AbstractTestBase{

	private VariableInterpreter interpreter;

	private DataSourceDataSupplier sourceSupplier;

	private SessionDataSupplier sessionSupplier;

	@Before
	public void initialize() {
		sourceSupplier = mock(DataSourceDataSupplier.class);
		sessionSupplier = mock(SessionDataSupplier.class);
		VariableInterpreterFactory factory = injector.getInstance(VariableInterpreterFactory.class);
		interpreter = factory.getInterpreter(sourceSupplier, sessionSupplier);
	}

	@Test
	public void shouldReturnCorrectContentString(){		
		ItemSessionDataSocket itemSessionDataSocket = mock(ItemSessionDataSocket.class);
		AssessmentSessionDataSocket assessmentSessionDataSocket = mock(AssessmentSessionDataSocket.class);
		VariableProviderSocket itemVariableSocket = mock(VariableProviderSocket.class);
		VariableProviderSocket assessmentVariableSocket = mock(VariableProviderSocket.class);
		OutcomeCreator outcomeCreator = new OutcomeCreator();
		int refItemIndex = 0;
		
		when(sourceSupplier.getAssessmentTitle()).thenReturn("Lesson 1");
		when(sourceSupplier.getItemTitle(0)).thenReturn("Page 1");
		when(sourceSupplier.getItemTitle(1)).thenReturn("Page 2");
		when(sourceSupplier.getItemsCount()).thenReturn(1);
		
		when(sessionSupplier.getItemSessionDataSocket(anyInt())).thenReturn(itemSessionDataSocket);
		when(itemSessionDataSocket.getVariableProviderSocket()).thenReturn(itemVariableSocket);
		
		when(sessionSupplier.getAssessmentSessionDataSocket()).thenReturn(assessmentSessionDataSocket);
		when(assessmentSessionDataSocket.getVariableProviderSocket()).thenReturn(assessmentVariableSocket);
		
		when(itemVariableSocket.getVariableValue(DefaultVariableProcessor.TODO)).thenReturn(outcomeCreator.createTodoOutcome(3));
		when(assessmentVariableSocket.getVariableValue(DefaultVariableProcessor.DONE)).thenReturn(outcomeCreator.createDoneOutcome(2));
		
		List<ContentInfo> infos = Lists.newArrayList(
					ContentInfo.create("$[item.title]", "Page 1", 0),
					ContentInfo.create("$[item.title]", "Page 2", 1),
					ContentInfo.create("$[test.title]", "Lesson 1", 0), 
					ContentInfo.create("$[item.index]", "1", 0),
					ContentInfo.create("$[item.page_num]", "1", 0),
					ContentInfo.create("$[item.page_count]", "1", 0),
					ContentInfo.create("$[item.todo]", "3", 0),
					ContentInfo.create("$[item.done]", "0", 0),
					ContentInfo.create("$[item.checks]", "0", 0),
					ContentInfo.create("$[item.mistakes]", "0", 0),
					ContentInfo.create("$[item.show_answers]", "0", 0),
					ContentInfo.create("$[item.reset]", "0", 0),
					ContentInfo.create("$[item.result]", "0", 0),
					ContentInfo.create("$[test.title]", "Lesson 1", 0),
					ContentInfo.create("$[test.todo]", "0", 0),
					ContentInfo.create("$[test.done]", "2", 0),
					ContentInfo.create("$[test.checks]", "0", 0),
					ContentInfo.create("$[test.mistakes]", "0", 0),
					ContentInfo.create("$[test.show_answers]", "0", 0),
					ContentInfo.create("$[test.reset]", "0", 0),
					ContentInfo.create("$[test.result]", "0", 0),
					ContentInfo.create("$[item.title], $[test.title] result is $[test.result]%", "Page 1, Lesson 1 result is 0%", 0)
				);		
		
		for(ContentInfo info: infos){
			assertInfo(info);
		}
		
		
	}

	private void assertInfo(ContentInfo info) {
		String template = "This is %1$s.";
		String content = String.format(template, info.getContentTag());
		String expectedValue = String.format(template, info.getExpectedValue());

		assertThat(info.getContentTag(), interpreter.replaceAllTags(content, info.getRefItemIndex()), is(equalTo(expectedValue)));
	}

	private static class ContentInfo {

		private String contentTag;
		private String expectedValue;
		private int refItemIndex;

		public static ContentInfo create(String content, String expectedValue, int refItemIndex) {
			return new ContentInfo(content, expectedValue, refItemIndex);
		}

		public ContentInfo(String contentTag, String expectedValue, int refItemIndex) {
			this.contentTag = contentTag;
			this.expectedValue = expectedValue;
			this.refItemIndex = refItemIndex;
		}

		public String getContentTag() {
			return contentTag;
		}
		
		public int getRefItemIndex() {
			return refItemIndex;
		}

		public String getExpectedValue() {
			return expectedValue;
		}

	}

}
