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

package eu.ydp.empiria.player.client.module.tutor;

import com.google.common.base.Function;

import java.util.Collection;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public enum ActionType {

    DEFAULT, ON_PAGE_ALL_OK, ON_OK, ON_WRONG;

    private static final Function<ActionType, String> function = new Function<ActionType, String>() {

        @Override
        public String apply(ActionType type) {
            return type.toString();
        }
    };

    private static Collection<String> cachedValuesString = createValuesString();

    public static Collection<String> valuesString() {
        return cachedValuesString;
    }

    private static Collection<String> createValuesString() {
        Collection<ActionType> types = asList(values());
        return newArrayList(transform(types, function));
    }

}
