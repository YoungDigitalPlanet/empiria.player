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

import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.HasParent;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class NestedParenthoodManagerTest {

    private NestedParenthoodManager testObj = new NestedParenthoodManager();
    private HasParent child = mock(HasParent.class);
    private HasChildren parent = mock(HasChildren.class);
    private HasChildren grandParent = mock(HasChildren.class);

    @Test
    public void shouldAddChildToParent() {
        // given
        testObj.addParent(parent);
        testObj.addChildToParent(child, parent);

        // when
        List<HasParent> nestedChildren = testObj.getNestedChildren(parent);

        // then
        assertThat(nestedChildren).contains(child);
    }

    @Test
    public void shouldAddChildToGrandParent() {
        // given
        testObj.addParent(grandParent);
        testObj.addChildToParent(parent, grandParent);
        testObj.addParent(parent);
        testObj.addChildToParent(child, parent);

        // when
        List<HasParent> nestedChildren = testObj.getNestedChildren(grandParent);

        // then
        assertThat(nestedChildren).contains(child);
    }

    @Test
    public void shouldAddParentToChild() {
        // given
        testObj.addParent(parent);
        testObj.addChildToParent(child, parent);

        // when
        List<HasChildren> nestedParents = testObj.getNestedParents(child);

        // then
        assertThat(nestedParents).contains(parent);
    }

    @Test
    public void shouldAddGrandParentToChild() {
        // given
        testObj.addParent(grandParent);
        testObj.addChildToParent(parent, grandParent);
        testObj.addParent(parent);
        testObj.addChildToParent(child, parent);

        // when
        List<HasChildren> nestedParents = testObj.getNestedParents(child);

        // then
        assertThat(nestedParents).contains(grandParent);
    }
}