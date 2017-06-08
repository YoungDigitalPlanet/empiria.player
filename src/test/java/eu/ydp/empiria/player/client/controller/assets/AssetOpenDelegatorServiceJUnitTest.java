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

package eu.ydp.empiria.player.client.controller.assets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssetOpenDelegatorServiceJUnitTest {

    private final static String PATH = "path";

    @InjectMocks
    private AssetOpenDelegatorService service;

    @Mock
    private AssetOpenJSDelegator assetOpenJSDelegator;
    @Mock
    private URLOpenService urlOpenService;

    @Test
    public void shouldOpenExternal() {
        // given
        when(assetOpenJSDelegator.empiriaExternalLinkSupported()).thenReturn(true);

        // when
        service.open(PATH);

        // then
        verify(assetOpenJSDelegator).empiriaExternalLinkOpen(PATH);
    }

    @Test
    public void shouldOpenInWindow() {
        // given
        when(assetOpenJSDelegator.empiriaExternalLinkSupported()).thenReturn(false);

        // when
        service.open(PATH);

        // then
        verify(urlOpenService).open(PATH);
    }
}
