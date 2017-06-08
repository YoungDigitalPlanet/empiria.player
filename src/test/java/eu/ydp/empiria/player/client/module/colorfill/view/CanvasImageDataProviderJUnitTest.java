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

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.colorfill.fill.CanvasImageData;
import eu.ydp.empiria.player.client.module.colorfill.fill.CanvasImageDataSlower;
import eu.ydp.empiria.player.client.module.colorfill.fill.ICanvasImageData;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class CanvasImageDataProviderJUnitTest {

    private CanvasImageDataProvider canvasImageDataProvider;
    private UserAgentUtil userAgentUtil;
    private final CanvasImageView canvasStubView = Mockito.mock(CanvasImageView.class);

    @Before
    public void setUp() throws Exception {
        userAgentUtil = Mockito.mock(UserAgentUtil.class);
        canvasImageDataProvider = new CanvasImageDataProvider(userAgentUtil);

        Canvas canvas = Mockito.mock(Canvas.class);
        when(canvasStubView.getCanvas()).thenReturn(canvas);

        Context2d context = Mockito.mock(Context2d.class);
        when(canvas.getContext2d()).thenReturn(context);

        ImageData imageData = Mockito.mock(ImageData.class);
        when(context.getImageData(0, 0, 0, 0)).thenReturn(imageData);
    }

    @Test
    public void shouldReturnSlowerImplemntationOfCanvasImageDataWhenOnInternetExplorer() throws Exception {
        when(userAgentUtil.isIE()).thenReturn(true);

        ICanvasImageData imageData = canvasImageDataProvider.getCanvasImageData(canvasStubView);

        assertTrue(imageData instanceof CanvasImageDataSlower);
    }

    @Test
    public void shouldReturnFasterImplemntationOfCanvasImageDataWhenSomethingElseThanInternetExplorer() throws Exception {
        when(userAgentUtil.isIE()).thenReturn(false);

        ICanvasImageData imageData = canvasImageDataProvider.getCanvasImageData(canvasStubView);

        assertTrue(imageData instanceof CanvasImageData);
    }
}
