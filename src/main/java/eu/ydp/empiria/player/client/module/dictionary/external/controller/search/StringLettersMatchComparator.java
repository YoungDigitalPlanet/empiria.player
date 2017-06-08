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

public class StringLettersMatchComparator implements Comparator<String> {

    @Override
    public int compare(String reference, String test) {
        String referenceLower = reference.toLowerCase();
        String testLower = test.toLowerCase();
        int counter = 0;
        for (int i = 0; i < referenceLower.length() && i < testLower.length(); i++) {
            if (referenceLower.charAt(i) == testLower.charAt(i)) {
                counter++;
            } else {
                break;
            }
        }
        return reference.length() - counter;
    }

}
