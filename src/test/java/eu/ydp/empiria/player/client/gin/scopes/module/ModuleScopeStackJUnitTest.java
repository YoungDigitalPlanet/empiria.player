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

package eu.ydp.empiria.player.client.gin.scopes.module;

import com.google.gwt.xml.client.Element;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.EmptyStackException;

import static org.fest.assertions.api.Assertions.assertThat;

public class ModuleScopeStackJUnitTest {

    private ModuleScopeStack scopeStack;

    @Before
    public void setUp() throws Exception {
        scopeStack = new ModuleScopeStack();
    }

    @Test(expected = EmptyStackException.class)
    public void shouldThrowExceptionWhenPopOnEmpty() throws Exception {
        scopeStack.pop();
    }

    @Test
    public void shouldReturnPushedContextWhenPop() throws Exception {
        ModuleCreationContext pushedContext = getContext();
        scopeStack.pushContext(pushedContext);

        ModuleCreationContext returnedContext = scopeStack.pop();
        assertThat(returnedContext).isEqualTo(pushedContext);
    }

    @Test
    public void shouldAlwaysReturnTopElement() throws Exception {
        ModuleCreationContext firstContext = getContext();
        scopeStack.pushContext(firstContext);

        assertThat(scopeStack.getCurrentTopContext()).isEqualTo(firstContext);

        ModuleCreationContext secondContext = getContext();
        scopeStack.pushContext(secondContext);

        assertThat(scopeStack.getCurrentTopContext()).isEqualTo(secondContext);

        scopeStack.pop();
        assertThat(scopeStack.getCurrentTopContext()).isEqualTo(firstContext);
    }

    @Test(expected = EmptyStackException.class)
    public void shouldThrowExceptionWhenAskedForTopWithEmptyStack() throws Exception {
        scopeStack.getCurrentTopContext();
    }

    private ModuleCreationContext getContext() {
        return new ModuleCreationContext(Mockito.mock(Element.class));
    }
}
