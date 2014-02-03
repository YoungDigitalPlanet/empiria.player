package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;

public class ItemsOrderByAnswersFinderJUnitTest {

	private ItemsOrderByAnswersFinder itemsOrderByAnswersFinder = new ItemsOrderByAnswersFinder();

	@Test
	public void shouldFindCorrectItemsOrderByUserAnswers() throws Exception {
		List<String> currentAnswers = Lists.newArrayList("answer1", "answer3", "answer2");
		List<OrderingItem> items = Lists.newArrayList(createItem("id2", "answer2"), createItem("id1", "answer1"), createItem("id3", "answer3"));
		List<String> itemsOrder = itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(currentAnswers, items);

		List<String> expectedItemsOrder = Lists.newArrayList("id1", "id3", "id2");
		Assertions.assertThat(itemsOrder).isEqualTo(expectedItemsOrder);
	}

	@Test(expected = CannotMatchOrderingItemsToUserAnswersException.class)
	public void shouldThrowExceptionWhenCannotMatchOrderingItemsToUserAnswer() throws Exception {
		List<String> currentAnswers = Lists.newArrayList("not match any OrderingItem", "answer3", "answer2");
		List<OrderingItem> items = Lists.newArrayList(createItem("id2", "answer2"), createItem("id1", "answer1"), createItem("id3", "answer3"));

		itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(currentAnswers, items);
	}

	private OrderingItem createItem(String id, String answerValue) {
		return new OrderingItem(id, answerValue);
	}

}
