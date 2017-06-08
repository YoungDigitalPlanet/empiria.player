/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.dictionary.external.controller.search;

import java.util.Comparator;
import java.util.List;

public class BinarySearchBestMatch<T extends Comparable<T>> {

    private int size;
    private int low;
    private int high;
    private int mid;
    private int index;
    private List<T> passwordsList;
    private Comparator<T> comparator;
    private T prefix;

    public int search(List<T> list, T prefix, Comparator<T> comparator) {
        initVariables(list, prefix, comparator);

        while (!doSearchIteration()) {
            // continue search
        }

        return index;
    }

    private int compareToPrefix(int comparedIndex) {
        T compared = passwordsList.get(comparedIndex);
        return compare(prefix, compared);
    }

    private int compare(T t1, T t2) {
        return comparator.compare(t1, t2);
    }

    private void initVariables(List<T> passwordsList, T prefix, Comparator<T> comparator) {
        this.passwordsList = passwordsList;
        this.prefix = prefix;
        this.comparator = comparator;
        size = passwordsList.size();
        low = 0;
        high = size - 1;
        index = 0;
        mid = 0;
    }

    private boolean doSearchIteration() {
        boolean found = false;
        mid = low + (high - low) / 2;
        int comparationResult = compareToPrefix(mid);

        if (comparationResult < 0) { // searched index is before mid
            found = searchInFirstHalf();
        } else if (comparationResult > 0) { // searched index is after mid
            found = searchInSecondHalf();
        } else {
            index = mid;
            found = true;
        }

        return found;
    }

    private boolean searchInFirstHalf() {
        boolean found = false;
        if (mid > 0) {
            found = checkPrevious();
        } else {
            index = 0;
            found = true;
        }
        if (high != mid) {
            high = mid;
        } else {
            found = true;
        }
        return found;
    }

    private boolean checkPrevious() {
        boolean found = false;

        int comparationResultForPrevious = compareToPrefix(mid - 1);

        boolean prefixIsAfterPrevious = comparationResultForPrevious > 0;
        boolean previousIsFirst = mid - 1 == 0;

        if (prefixIsAfterPrevious || previousIsFirst) {
            index = mid;
            found = true;
        }

        return found;
    }

    private boolean searchInSecondHalf() {
        boolean found = false;
        if (mid < size - 1) {
            found = checkNext();
        } else {
            index = 0;
            found = true;
        }
        if (low != mid) {
            low = mid;
        } else {
            index = mid;
            found = true;
        }
        return found;
    }

    private boolean checkNext() {
        int comparationResultForNext = compareToPrefix(mid + 1);

        boolean prefixIsBeforeNext = comparationResultForNext < 0;
        boolean nextIsLast = mid + 1 == size - 1;

        if (prefixIsBeforeNext || nextIsLast) {
            index = mid + 1;
            return true;
        }

        return false;
    }
}
