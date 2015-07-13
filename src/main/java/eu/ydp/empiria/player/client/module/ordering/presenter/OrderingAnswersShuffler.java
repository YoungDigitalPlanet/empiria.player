package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.common.collect.Lists;
import eu.ydp.gwtutil.client.collections.RandomizedSet;

import java.util.List;

public class OrderingAnswersShuffler {

    private static final int NUMBER_OF_ATTEMPTS = 30;

    public List<String> shuffleAnswers(List<String> currentAnswers, List<String> correctAnswers) {
        List<String> randomOrder = null;
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            randomOrder = shuffle(currentAnswers);
            if (isNewNotCorrectOrder(currentAnswers, correctAnswers, randomOrder)) {
                return randomOrder;
            }
        }
        return randomOrder;
    }

    private boolean isNewNotCorrectOrder(List<String> currentAnswers, List<String> correctAnswers, List<String> randomOrder) {
        return !randomOrder.equals(currentAnswers) && !randomOrder.equals(correctAnswers);
    }

    private List<String> shuffle(List<String> currentAnswers) {
        RandomizedSet<String> randomizedSet = new RandomizedSet<String>(currentAnswers);
        List<String> randomAnswers = Lists.newArrayList();
        while (randomizedSet.hasMore()) {
            String answer = randomizedSet.pull();
            randomAnswers.add(answer);
        }
        return randomAnswers;
    }

}
