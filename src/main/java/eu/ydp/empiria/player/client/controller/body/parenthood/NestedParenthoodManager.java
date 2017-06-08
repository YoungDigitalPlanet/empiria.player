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

package eu.ydp.empiria.player.client.controller.body.parenthood;

import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.HasParent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NestedParenthoodManager {

    private Map<HasParent, List<HasChildren>> nestedParents = Maps.newHashMap();
    private Map<HasChildren, List<HasParent>> nestedChildren = Maps.newHashMap();

    public void addParent(HasChildren parent) {
        if (!nestedChildren.containsKey(parent)) {
            nestedChildren.put(parent, new ArrayList<HasParent>());
        }
    }

    public void addChildToParent(HasParent child, HasChildren parent) {
        nestedParents.put(child, new ArrayList<HasChildren>());
        nestedParents.get(child).add(parent);

        nestedChildren.get(parent).add(child);

        if (nestedParents.containsKey(parent)) {
            List<HasChildren> grandParents = nestedParents.get(parent);
            nestedParents.get(child).addAll(grandParents);

            for (HasChildren grandParent : grandParents) {
                nestedChildren.get(grandParent).add(child);
            }
        }
    }

    public List<HasChildren> getNestedParents(HasParent child) {
        return nestedParents.get(child);
    }

    public List<HasParent> getNestedChildren(HasChildren parent) {
        return nestedChildren.get(parent);
    }
}
