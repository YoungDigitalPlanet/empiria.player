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

import com.google.common.base.Optional;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.inject.Instance;
import eu.ydp.empiria.player.client.module.simulation.SimulationModule.TouchReservationHandler;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.handler.ManifestLoadHandler;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;
import eu.ydp.gwtcreatejs.client.loader.Manifest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SimulationModuleTest {

    private static final String URL = "http://dummyurl/";

    @InjectMocks
    private SimulationModule testObj;

    @Mock
    private EventsBus eventBus;
    @Mock
    private TouchController touchController;
    @Mock
    private Instance<SimulationModuleView> viewInstance;
    @Mock
    private SimulationModuleView moduleView;
    @Mock
    private SimulationPreloader preloader;
    @Mock
    private Instance<CreateJsLoader> createJsLoaderInstance;
    @Mock
    private CreateJsLoader createJsLoader;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PageScopeFactory pageScopeFactory;
    @Mock
    private SimulationController simulationController;
    @Mock
    private SimulationCanvasProvider simulationCanvasProvider;
    @Mock
    private com.google.gwt.user.client.Element element;

    private Element createElementMock() {
        Element element = mock(Element.class);
        doReturn(URL).when(element)
                .getAttribute(Matchers.anyString());
        return element;
    }

    @Before
    public void before() {
        when(createJsLoaderInstance.get()).thenReturn(createJsLoader);
        when(viewInstance.get()).thenReturn(moduleView);
        Optional<com.google.gwt.user.client.Element> elementOptional = Optional.of(element);
        when(simulationCanvasProvider.getSimulationCanvasElement(createJsLoader)).thenReturn(elementOptional);
    }

    @Test
    public void testInitModuleElement() {
        testObj.initModule(createElementMock());
        verify(createJsLoader).addManifestLoadHandler(Matchers.any(ManifestLoadHandler.class));
        verify(createJsLoader).setLibraryURL(Matchers.eq("http://dummyurl/../../../common/jslibs/"));
        verify(createJsLoader).addCompleteHandler(Matchers.any(CompleteHandler.class));
        verify(createJsLoader).load(Matchers.eq(URL));
    }

    @Test
    public void testGetView() {
        testObj.getView();
        verify(moduleView).asWidget();
    }

    @Test
    public void testOnClose() {
        testObj.initModule(createElementMock());
        testObj.onClose();
        verify(createJsLoader).stopSounds();
    }

    @Test
    public void testOnManifestLoad() {
        Manifest manifest = mock(Manifest.class);
        double width = 50d;
        doReturn(width).when(manifest)
                .getWidth();
        double height = 70d;
        doReturn(height).when(manifest)
                .getHeight();

        testObj.onManifestLoad(manifest);
        verify(moduleView).add(Matchers.eq(preloader));
        verify(preloader).show(Matchers.eq((int) width), Matchers.eq((int) height));

    }

    @Test
    public void initializeCanvasTest() {
        Canvas canvas = mock(Canvas.class);
        testObj.initializeCanvas(canvas);

        verify(canvas).addTouchStartHandler(Matchers.any(TouchStartHandler.class));
        verify(moduleView).add(canvas);
        verify(preloader).hidePreloaderAndRemoveFromParent();

    }

    @Test
    public void touchReservationTest() {
        Canvas canvas = mock(Canvas.class);
        ArgumentCaptor<TouchReservationHandler> touchReservationCaptor = ArgumentCaptor.forClass(TouchReservationHandler.class);

        testObj.initializeCanvas(canvas);
        verify(canvas).addTouchStartHandler(touchReservationCaptor.capture());
        TouchReservationHandler reservationHandler = touchReservationCaptor.getValue();
        reservationHandler.onTouchStart(null);

        // then
        verify(touchController).setTouchReservation(eq(true));
    }

    @Test
    public void onPlayerEventNoInteractionTest() {
        testObj.initModule(createElementMock());
        for (PlayerEventTypes eventType : PlayerEventTypes.values()) {
            if (eventType != PlayerEventTypes.PAGE_CHANGE && eventType != PlayerEventTypes.WINDOW_RESIZED) {
                PlayerEvent event = new PlayerEvent(eventType);
                testObj.onPlayerEvent(event);
            }
        }
        verifyNoMoreInteractions(simulationController);
    }

    @Test
    public void onPlayerEventPageNotChangeTest() {
        // given
        testObj.initModule(createElementMock());
        Canvas canvas = mock(Canvas.class);
        testObj.initializeCanvas(canvas);
        PlayerEvent event = new PlayerEvent(PlayerEventTypes.PAGE_CHANGE, 0, null);

        // when
        testObj.onPlayerEvent(event);

        // then
        verify(simulationController).resumeAnimation(Matchers.any(JavaScriptObject.class));
    }

    @Test
    public void onPlayerEventPageChangeTest() {
        // given
        testObj.initModule(createElementMock());
        Canvas canvas = mock(Canvas.class);
        testObj.initializeCanvas(canvas);
        PlayerEvent event = new PlayerEvent(PlayerEventTypes.PAGE_CHANGE, 1, null);

        // when
        testObj.onPlayerEvent(event);

        // then
        verify(simulationController).pauseAnimation(element);
    }

    @Test
    public void onWindowResizedEventTest() {
        // given
        testObj.initModule(createElementMock());
        Canvas canvas = mock(Canvas.class);
        testObj.initializeCanvas(canvas);
        PlayerEvent event = new PlayerEvent(PlayerEventTypes.WINDOW_RESIZED);

        // when
        testObj.onPlayerEvent(event);

        // then
        verify(simulationController).onWindowResized(element);
    }
}
