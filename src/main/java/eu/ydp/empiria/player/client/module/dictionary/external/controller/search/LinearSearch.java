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
