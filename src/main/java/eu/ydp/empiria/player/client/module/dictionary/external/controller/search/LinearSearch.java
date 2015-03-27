package eu.ydp.empiria.player.client.module.dictionary.external.controller.search;

import java.util.Comparator;
import java.util.List;

public class LinearSearch<T> {

    private List<T> passwords;
    private T prefix;
    private Comparator<T> comparator;

    public int search(List<T> passwords, T prefix, int startIndex, Comparator<T> comparator) {
        this.passwords = passwords;
        this.prefix = prefix;
        this.comparator = comparator;
        return matchLinear(startIndex, comparator.compare(prefix, passwords.get(startIndex)));
    }

    private int matchLinear(int index, int comparationResult) {
        int prevIndex = index - 1;
        boolean indexIsValid = prevIndex >= 0;
        if (indexIsValid) {

            int prevComparationResult = comparator.compare(prefix, passwords.get(prevIndex));

            if (prevComparationResult <= comparationResult) {
                index = matchLinear(prevIndex, prevComparationResult);
            }
        }
        return index;
    }
}
