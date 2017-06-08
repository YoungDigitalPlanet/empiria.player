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

package eu.ydp.empiria.player.client.module.dictionary.external.model;

import com.google.common.base.Optional;

import java.util.*;

public class Words {
    private final Map<String, List<String>> wordsByLetter;
    private final Map<String, Integer> baseIndexes;

    public Words(LinkedHashMap<String, List<String>> wordsByLetter, TreeMap<String, Integer> baseIndexes) {
        this.wordsByLetter = wordsByLetter;
        this.baseIndexes = baseIndexes;
    }

    public Map<String, Integer> getBaseIndexes() {
        return baseIndexes;
    }

    public Set<String> getFirstLetters() {
        return wordsByLetter.keySet();
    }

    public Optional<String> getFirstIndex() {
        if (baseIndexes.isEmpty()) {
            return Optional.absent();
        }
        Set<String> indexesSet = baseIndexes.keySet();
        return Optional.of(indexesSet.iterator().next());
    }

    public List<String> getWordsByLetter(String firstLetter) {
        return wordsByLetter.get(firstLetter);
    }
}
