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

package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.preloader.Preloader;
import eu.ydp.empiria.player.client.preloader.view.ProgressView;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

public class SimulationPreloaderJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(Preloader.class).toInstance(mock(Preloader.class));
            binder.bind(ProgressView.class).toInstance(createProgressViewMock());
        }

        private ProgressView createProgressViewMock() {
            ProgressView progressView = mock(ProgressView.class);
            when(progressView.asWidget()).thenReturn(mock(Widget.class));
            return progressView;
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

    private SimulationPreloader instance;
    private Preloader preloader;
    private final Widget widget = mock(Widget.class);

    @Before
    public void before() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
        instance = injector.getInstance(SimulationPreloader.class);
        preloader = injector.getInstance(Preloader.class);
        doReturn(widget).when(preloader).asWidget();
    }

    @Test
    public void testShow() {
        instance.show(10, 20);
        verify(preloader).setPreloaderSize(Matchers.eq(10), Matchers.eq(20));
        verify(preloader).show();
    }

    @Test
    public void testHidePreloaderAndRemoveFromParent() {
        instance.hidePreloaderAndRemoveFromParent();
        verify(preloader).hide();
        verify(widget).removeFromParent();
    }

    @Test
    public void testAsWidget() {
        instance.asWidget();
        verify(preloader).asWidget();
    }

}
