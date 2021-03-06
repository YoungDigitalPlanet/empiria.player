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

package eu.ydp.empiria.player.client.module.bonus.popup;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyObject;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BonusPopupPresenterTest {

    @InjectMocks
    private BonusPopupPresenter bonusPopupPresenter;

    @Mock
    private BonusPopupView view;
    @Mock
    private SwiffyObject swiffy;
    @Mock
    private IsWidget swiffyWidget;
    @Mock
    private EndHandler endHandler;

    @After
    public void verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(view, swiffy, endHandler, swiffyWidget);
    }

    @Test
    public void shouldShowImage() throws Exception {
        // given
        String url = "url";
        Size size = new Size(1, 2);

        // when
        bonusPopupPresenter.showImage(url, size);

        // then
        verify(view).showImage(url, size);
        verify(view).attachToRoot();
    }

    @Test
    public void shouldShowAnimation() throws Exception {
        // given
        Size size = new Size(1, 2);

        when(swiffy.getWidget()).thenReturn(swiffyWidget);

        // when
        bonusPopupPresenter.showAnimation(swiffy, size, endHandler);

        // then
        verify(swiffy).getWidget();
        verify(view).setAnimationWidget(swiffyWidget, size);
        verify(view).attachToRoot();
        verify(swiffy).start();
    }

    @Test
    public void shouldCallEndHandlerWhenCloseAfterSwiffy() throws Exception {
        // given
        Size size = new Size(1, 2);

        when(swiffy.getWidget()).thenReturn(swiffyWidget);

        // when
        bonusPopupPresenter.showAnimation(swiffy, size, endHandler);
        bonusPopupPresenter.closeClicked();
        bonusPopupPresenter.closeClicked();

        // then
        verify(swiffy).getWidget();
        verify(view).setAnimationWidget(swiffyWidget, size);
        verify(view).attachToRoot();
        verify(swiffy).start();
        verify(view, times(2)).reset();
        verify(endHandler).onEnd();
    }

    @Test
    public void shouldSetPresenterOnView() throws Exception {
        // given

        // when
        bonusPopupPresenter.initialize();

        // then
        verify(view).setPresenterOnView(bonusPopupPresenter);
    }
}
