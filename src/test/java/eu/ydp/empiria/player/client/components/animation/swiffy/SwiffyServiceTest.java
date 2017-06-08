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

package eu.ydp.empiria.player.client.components.animation.swiffy;

import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SwiffyServiceTest {

    @Mock
    private SwiffyObject swiffyObject;
    @Mock
    private Provider<SwiffyObject> swiffyObjectProvider;
    @InjectMocks
    private SwiffyService instance;

    @Before
    public void before() {
        doReturn(swiffyObject).when(swiffyObjectProvider).get();
    }

    @Test
    public void getSwiffyObject() throws Exception {
        String animationUrl = "url";
        SwiffyObject swiffyObject = instance.getSwiffyObject("name", animationUrl);
        assertThat(swiffyObject).isNotNull();
        assertThat(swiffyObject).isEqualTo(this.swiffyObject);
        verify(swiffyObject).setAnimationUrl(eq(animationUrl));
    }

    @Test
    public void clear() throws Exception {
        String animationUrl = "url";
        String animationName = "name";
        SwiffyObject swiffyObject = instance.getSwiffyObject(animationName, animationUrl);
        instance.clear(animationName);
        instance.clear(animationName);
        verify(swiffyObject).destroy();
    }

}
