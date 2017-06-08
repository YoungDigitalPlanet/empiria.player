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

package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.SlideshowPagerView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SlideshowPagerPresenterTest {

    @InjectMocks
    private SlideshowPagerPresenter testObj;
    @Mock
    private SlideshowPagerView view;
    @Mock
    private Provider<SlideshowPagerButtonPresenter> pagerButtonProvider;
    @Mock
    private SlideshowPagerButtonPresenter pagerButton;
    @Mock
    private SlideshowController slideshowController;

    @Captor
    private ArgumentCaptor<Command> commandCaptor;

    @Before
    public void init() {
        when(pagerButtonProvider.get()).thenReturn(pagerButton);
        testObj.setSlideshowController(slideshowController);
    }

    @Test
    public void shouldCreatePagerModule() {
        // given
        int slidesSize = 1;

        Widget pagerButtonView = mock(Widget.class);
        when(pagerButton.getView()).thenReturn(pagerButtonView);

        // when
        testObj.createPager(slidesSize);

        // then
        verify(view).addPager(pagerButtonView);
    }

    @Test
    public void shouldExecuteClickCommand() {
        // given
        int slidesSize = 1;
        testObj.createPager(slidesSize);

        NativeEvent event = mock(NativeEvent.class);
        verify(pagerButton).setClickCommand(commandCaptor.capture());
        Command command = commandCaptor.getValue();

        // when
        command.execute(event);

        // then
        verify(pagerButton).deactivatePagerButton();
        verify(pagerButton).activatePagerButton();
        verify(slideshowController).showSlide(0);
    }

    @Test
    public void shouldUpdateButtons() {
        // given
        int slidesSize = 1;
        testObj.createPager(slidesSize);

        // when
        testObj.updateButtons(0);

        // then
        verify(pagerButton).deactivatePagerButton();
        verify(pagerButton).activatePagerButton();
    }

    @Test
    public void shouldNotUpdateButtons_whenPagerIsNotInitialized() {
        // given

        // when
        testObj.updateButtons(2);

        // then
        verify(pagerButton, never()).deactivatePagerButton();
        verify(pagerButton, never()).activatePagerButton();
    }
}
