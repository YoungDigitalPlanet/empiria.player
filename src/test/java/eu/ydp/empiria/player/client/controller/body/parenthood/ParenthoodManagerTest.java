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

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.base.ParenthoodSocket;
import eu.ydp.gwtutil.client.collections.StackMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParenthoodManagerTest {
    @Spy
    private final StackMap<HasChildren, List<IModule>> parenthood = new StackMap<HasChildren, List<IModule>>();

    @Mock
    private ParenthoodSocket upperLevelParenthoodSocket;
    @InjectMocks
    private ParenthoodManager instance;
    @Mock
    private IModule module;
    @Mock
    private HasChildren hasChildren;

    @Test
    public void getParentNullResult() throws Exception {
        HasChildren parent = instance.getParent(module);
        assertThat(parent).isNull();
    }

    @Test
    public void getParentFromParentHood() throws Exception {
        parenthood.put(hasChildren, Lists.newArrayList(module));
        HasChildren parent = instance.getParent(module);
        assertThat(parent).isEqualTo(hasChildren);
        verifyZeroInteractions(upperLevelParenthoodSocket);
    }

    @Test
    public void getParentFromUpperLevelParenthoodSocket() throws Exception {
        doReturn(hasChildren).when(upperLevelParenthoodSocket).getParent(eq(module));
        HasChildren parent = instance.getParent(module);
        assertThat(parent).isEqualTo(hasChildren);
        verify(upperLevelParenthoodSocket).getParent(eq(module));
    }

    @Test
    public void getParentFromCache() throws Exception {
        doReturn(hasChildren).when(upperLevelParenthoodSocket).getParent(eq(module));
        HasChildren parent = instance.getParent(module);
        // second from cache
        parent = instance.getParent(module);
        assertThat(parent).isEqualTo(hasChildren);
        verify(upperLevelParenthoodSocket).getParent(eq(module));

    }

}
