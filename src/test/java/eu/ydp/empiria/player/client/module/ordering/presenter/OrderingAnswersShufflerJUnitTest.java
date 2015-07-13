package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.common.collect.HashMultiset;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import org.fest.assertions.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderingAnswersShufflerJUnitTest {

    private OrderingAnswersShuffler orderingAnswersShuffler = new OrderingAnswersShuffler();

    @Test
    public void shouldShuffleAnswerAndReturnNotCorrectOrCurrent() throws Exception {

        List<String> currentAnswers = Lists.newArrayList("a", "a", "b", "c", "d");
        List<String> correctAnswers = Lists.newArrayList("a", "a", "b", "d", "c");

        List<String> shuffledAnswers = orderingAnswersShuffler.shuffleAnswers(currentAnswers, correctAnswers);

        Assert.assertFalse(currentAnswers.equals(shuffledAnswers));
        Assert.assertFalse(correctAnswers.equals(shuffledAnswers));

        assertEquals(HashMultiset.create(correctAnswers), HashMultiset.create(shuffledAnswers));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnCorrectOrCurrentWhenNoOtherOrderCanBeFound() throws Exception {
        List<String> currentAnswers = Lists.newArrayList("a", "b");
        List<String> correctAnswers = Lists.newArrayList("b", "a");

        List<String> shuffledAnswers = orderingAnswersShuffler.shuffleAnswers(currentAnswers, correctAnswers);

        Assertions.assertThat(shuffledAnswers).isIn(currentAnswers, correctAnswers);
    }
}
