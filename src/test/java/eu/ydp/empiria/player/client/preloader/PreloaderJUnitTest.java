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

package eu.ydp.empiria.player.client.preloader;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.preloader.view.ProgressView;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class PreloaderJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private Preloader instance;
    ProgressView progressView = mock(ProgressView.class);

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(ProgressView.class).toInstance(progressView);
            Widget w = mock(Widget.class);
            doReturn(w).when(progressView).asWidget();
        }
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Before
    public void before() {
        GuiceModuleConfiguration configuration = new GuiceModuleConfiguration();
        setUp(configuration, new CustomGinModule());
        instance = injector.getInstance(Preloader.class);

    }

    @Test
    public void testShow() {
        instance.show();

        verify(progressView.asWidget()).setVisible(Matchers.eq(true));
    }

    @Test
    public void testHide() {
        instance.hide();

        verify(progressView.asWidget(), times(2)).setVisible(Matchers.eq(false));
    }

    @Test
    public void testSetPreloaderSize() {

        for (int x = 100; x > 0; x -= 10) {
            int width = x;
            int height = x + 9;
            instance.setPreloaderSize(width, height);
            verify(progressView.asWidget()).setWidth(width + "px");
            verify(progressView.asWidget()).setHeight(height + "px");
        }

    }

}
