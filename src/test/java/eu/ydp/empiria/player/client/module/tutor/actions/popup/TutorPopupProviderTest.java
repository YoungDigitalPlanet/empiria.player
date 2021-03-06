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

package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TutorPopupProviderTest {

    private TutorPopupProvider tutorPopupProvider;
    @Mock
    private Provider<TutorPopupPresenter> provider;
    @Mock
    private TutorPopupPresenter popupPresenter;

    @Before
    public void setUp() throws Exception {
        tutorPopupProvider = new TutorPopupProvider(provider);
    }

    @Test
    public void shouldCreateAndInitializeNewPresenterWhenNoCached() throws Exception {
        // given
        String tutorId = "tutor1";

        when(provider.get()).thenReturn(popupPresenter);

        // when
        TutorPopupPresenter resultPresenter = tutorPopupProvider.get(tutorId);

        // then
        assertEquals(popupPresenter, resultPresenter);
        verify(popupPresenter).init(tutorId);
    }

    @Test
    public void shouldReturnCachedPresenter() throws Exception {
        // given
        String tutorId = "tutor1";

        when(provider.get()).thenReturn(popupPresenter);

        // when
        TutorPopupPresenter firstlyReturned = tutorPopupProvider.get(tutorId);
        TutorPopupPresenter secondlyReturned = tutorPopupProvider.get(tutorId);

        // then
        assertEquals(popupPresenter, firstlyReturned);
        assertEquals(firstlyReturned, secondlyReturned);
        verify(popupPresenter, times(1)).init(tutorId);
    }
}
