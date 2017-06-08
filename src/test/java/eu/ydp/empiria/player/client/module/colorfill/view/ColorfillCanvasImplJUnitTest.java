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

package eu.ydp.empiria.player.client.module.colorfill.view;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.*;
import eu.ydp.empiria.player.client.*;
import eu.ydp.empiria.player.client.module.colorfill.fill.ICanvasImageData;
import eu.ydp.empiria.player.client.module.colorfill.structure.*;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.event.factory.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ColorfillCanvasImplJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
    private static final int POSITION_Y = 20;
    private static final int POSITION_X = 10;

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(CanvasImageView.class)
                  .toInstance(canvasStubView);
            binder.bind(UserInteractionHandlerFactory.class)
                  .toInstance(userInteractionHandlerFactory);
            binder.bind(PositionHelper.class)
                  .toInstance(positionHelper);
            binder.bind(CanvasImageDataProvider.class).toInstance(canvasImageDataProvider);
        }
    }

    private final CanvasImageView canvasStubView = mock(CanvasImageView.class);
    private final UserInteractionHandlerFactory userInteractionHandlerFactory = mock(UserInteractionHandlerFactory.class);
    private final PositionHelper positionHelper = mock(PositionHelper.class);
    private final CanvasImageDataProvider canvasImageDataProvider = mock(CanvasImageDataProvider.class);
    private ColorfillCanvasImpl instance;
    private Canvas canvas;
    private ICanvasImageData imageData;

    @Before
    public void before() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());

        instance = injector.getInstance(ColorfillCanvasImpl.class);
        canvas = mock(Canvas.class);
        imageData = mock(ICanvasImageData.class);
        doReturn(canvas).when(canvasStubView)
                        .getCanvas();
        doReturn(POSITION_X).when(positionHelper)
                            .getXPositionRelativeToTarget(any(NativeEvent.class), Matchers.any(Element.class));
        doReturn(POSITION_Y).when(positionHelper)
                            .getYPositionRelativeToTarget(any(NativeEvent.class), Matchers.any(Element.class));

    }

    @Test
    public void bindView() throws Exception {
        // before
        ColorfillAreaClickListener colorfillAreaClickListener = mock(ColorfillAreaClickListener.class);
        when(canvasImageDataProvider.getCanvasImageData(canvasStubView)).thenReturn(imageData);
        instance.setAreaClickListener(colorfillAreaClickListener);

        ArgumentCaptor<LoadHandler> argumentCaptor = ArgumentCaptor.forClass(LoadHandler.class);
        verify(canvasStubView).setImageLoadHandler(argumentCaptor.capture());
        argumentCaptor.getValue()
                      .onLoad(null);
        verify(userInteractionHandlerFactory).applyUserClickHandler(any(Command.class), eq(canvas));
        verify(imageData).flush();
    }

    @Test
    public void onAreaClick() throws Exception {
        // before
        ColorfillAreaClickListener colorfillAreaClickListener = mock(ColorfillAreaClickListener.class);
        instance.setAreaClickListener(colorfillAreaClickListener);
        when(canvasImageDataProvider.getCanvasImageData(canvasStubView)).thenReturn(imageData);

        ArgumentCaptor<LoadHandler> loadHandlerCaptor = ArgumentCaptor.forClass(LoadHandler.class);
        verify(canvasStubView).setImageLoadHandler(loadHandlerCaptor.capture());
        loadHandlerCaptor.getValue()
                         .onLoad(null);

        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(userInteractionHandlerFactory).applyUserClickHandler(commandCaptor.capture(), eq(canvas));

        NativeEvent nativeEvent = mock(NativeEvent.class);
        commandCaptor.getValue()
                     .execute(nativeEvent);

        ArgumentCaptor<Area> areaCaptor = ArgumentCaptor.forClass(Area.class);
        verify(colorfillAreaClickListener).onAreaClick(areaCaptor.capture());
        verify(nativeEvent).preventDefault();
        Area area = areaCaptor.getValue();
        assertThat(area.getX()).isEqualTo(POSITION_X);
        assertThat(area.getY()).isEqualTo(POSITION_Y);
    }

    @Test
    public void onAreaClickWrongCoordinates() throws Exception {
        ColorfillAreaClickListener colorfillAreaClickListener = mock(ColorfillAreaClickListener.class);
        instance.setAreaClickListener(colorfillAreaClickListener);
        when(canvasImageDataProvider.getCanvasImageData(canvasStubView)).thenReturn(imageData);

        doReturn(-10).when(positionHelper)
                     .getXPositionRelativeToTarget(any(NativeEvent.class), Matchers.any(Element.class));
        doReturn(-15).when(positionHelper)
                     .getYPositionRelativeToTarget(any(NativeEvent.class), Matchers.any(Element.class));

        ArgumentCaptor<LoadHandler> loadHandlerCaptor = ArgumentCaptor.forClass(LoadHandler.class);
        verify(canvasStubView).setImageLoadHandler(loadHandlerCaptor.capture());
        loadHandlerCaptor.getValue()
                         .onLoad(null);

        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(userInteractionHandlerFactory).applyUserClickHandler(commandCaptor.capture(), eq(canvas));

        NativeEvent nativeEvent = mock(NativeEvent.class);
        commandCaptor.getValue()
                     .execute(nativeEvent);

        verify(colorfillAreaClickListener, times(0)).onAreaClick(any(Area.class));
    }

    @Test
    public void setImage() throws Exception {
        // given
        Image image = new Image();
        image.setSrc("src");
        image.setHeight(20);
        image.setWidth(32);

        // when
        instance.setImage(image);

        // then
        verify(canvasStubView).setImageUrl(eq("src"), eq(32), eq(20));
    }

    @Test
    public void shouldResetCanvas() throws Exception {
        // when
        instance.reset();

        // then
        verify(canvasStubView).reload();
        verify(canvasImageDataProvider).getCanvasImageData(canvasStubView);
    }

    @Test
    public void shouldGetViewAsWidget() throws Exception {
        // when
        instance.asWidget();

        // then
        verify(canvasStubView).asWidget();
    }
}
