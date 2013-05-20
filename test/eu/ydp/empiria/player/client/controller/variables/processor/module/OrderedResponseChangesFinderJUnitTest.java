package eu.ydp.empiria.player.client.controller.variables.processor.module;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

@RunWith(MockitoJUnitRunner.class)
public class OrderedResponseChangesFinderJUnitTest {

	private OrderedResponseChangesFinder orderedResponseChangesFinder;

	@Before
	public void before() {
		orderedResponseChangesFinder = new OrderedResponseChangesFinder();
	}

	@Test
	public void findChangesOfAnswersTest_noChanges_shouldReturnResultWithEmptyLists() {
		List<String> previousAnswers = Lists.newArrayList("a", "b", "c", "d");
		List<String> currentAnswers = Lists.newArrayList("a", "b", "c", "d");

		LastAnswersChanges result = orderedResponseChangesFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

		assertTrue(result.getAddedAnswers().isEmpty());
		assertTrue(result.getRemovedAnswers().isEmpty());
	}

	@Test
	public void findChangesOfAnswersTest_foundChanges_shouldReturnResultWithLists() {
		List<String> previousAnswers = Lists.newArrayList("a", "b", "c", "d");
		List<String> currentAnswers = Lists.newArrayList("a", "c", "d", "b");

		LastAnswersChanges result = orderedResponseChangesFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

		assertSame(previousAnswers, result.getRemovedAnswers());
		assertSame(currentAnswers, result.getAddedAnswers());
	}
}
