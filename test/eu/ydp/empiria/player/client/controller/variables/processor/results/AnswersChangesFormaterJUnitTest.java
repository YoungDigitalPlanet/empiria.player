package eu.ydp.empiria.player.client.controller.variables.processor.results;

import java.util.List;

import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasItems;


public class AnswersChangesFormaterJUnitTest {

	private AnswersChangesFormater answersChangesFormater = new AnswersChangesFormater();

	@Test
	public void shouldFormatAddedAnswer() throws Exception {
		List<String> addedAnswers = Lists.newArrayList("addedAnswer");
		List<String> removedAnswers = Lists.newArrayList();
		LastAnswersChanges answersChanges = new LastAnswersChanges(addedAnswers, removedAnswers);
		
		List<String> formattedAnswers = answersChangesFormater.formatLastAnswerChanges(answersChanges);
		
		String expectedFormattedAnswer = AnswersChangesFormater.ADDED_ASWER_PREFIX+"addedAnswer";
		assertThat(formattedAnswers, hasItems(expectedFormattedAnswer));
	}

	@Test
	public void shouldFormatRemovedAnswer() throws Exception {
		List<String> addedAnswers = Lists.newArrayList();
		List<String> removedAnswers = Lists.newArrayList("removedAnswer");
		LastAnswersChanges answersChanges = new LastAnswersChanges(addedAnswers, removedAnswers);
		
		List<String> formattedAnswers = answersChangesFormater.formatLastAnswerChanges(answersChanges);
		
		String expectedFormattedAnswer = AnswersChangesFormater.REMOVED_ASWER_PREFIX+"removedAnswer";
		assertThat(formattedAnswers, hasItems(expectedFormattedAnswer));
	}
	
}
