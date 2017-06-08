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

import eu.ydp.empiria.player.client.module.EndHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SingleRunTutorEndHandlerTest {

    @InjectMocks
    private SingleRunTutorEndHandler testObj;

    @Mock
    private ActionExecutorService executorService;

    @Test
    public void shouldCallTheEndOnlyOnce() {
        // given
        EndHandler mockEndHandler = mock(EndHandler.class);

        // when
        testObj.setEndHandler(mockEndHandler);
        testObj.onEnd();
        testObj.onEnd();

        // then
        verify(mockEndHandler).onEnd();
    }

    @Test
    public void shouldCallTheEndAfterResettingHandler() {
        // given
        EndHandler mockEndHandler = mock(EndHandler.class);

        // when
        testObj.setEndHandler(mockEndHandler);
        testObj.onEnd();
        testObj.setEndHandler(mockEndHandler);
        testObj.onEnd();

        // then
        verify(mockEndHandler, times(2)).onEnd();
    }

    @Test
    public void shouldCalltheDefaultAction() {
        // when
        testObj.onEndWithDefaultAction();

        // then
        verify(executorService).execute(ActionType.DEFAULT, testObj);
    }

    @Test
    public void shouldNotCalltheDefaultAction() {
        // when
        testObj.onEnd();

        // then
        verify(executorService, never()).execute(ActionType.DEFAULT, testObj);
    }

    @Test
    public void shouldFireOnceWhenTheReferenceIsCircular() {
        // given
        EndHandler endHandler = mock(EndHandler.class);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                testObj.onEnd();
                return null;
            }
        }).when(endHandler).onEnd();

        // when
        testObj.setEndHandler(endHandler);
        testObj.onEnd();

        // then
        verify(endHandler).onEnd();
    }
}
