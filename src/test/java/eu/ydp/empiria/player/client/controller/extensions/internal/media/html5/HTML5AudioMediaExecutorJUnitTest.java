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

package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwtmockito.GwtMockito;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class HTML5AudioMediaExecutorJUnitTest extends AbstractHTML5MediaExecutorJUnitBase {

    @Before
    public void setUp() {
        GwtMockito.initMocks(this);
        instance = (AbstractHTML5MediaExecutor) new HTML5AudioMediaExecutor(mediaEventMapper, html5MediaNativeListeners);
        mediaBase = mock(Audio.class);

        Map<String, String> sources = Maps.newHashMap();
        sources.put("http://dummy", "audio/mp4");

        mediaConfiguration = mock(BaseMediaConfiguration.class);
        when(mediaConfiguration.getSources()).thenReturn(sources);
        when(mediaConfiguration.getMediaType()).thenReturn(MediaType.AUDIO);
        when(mediaConfiguration.isTemplate()).thenReturn(true);
    }

    @Override
    public String getAssumedMediaPreloadType() {
        return MediaElement.PRELOAD_AUTO;
    }
}
