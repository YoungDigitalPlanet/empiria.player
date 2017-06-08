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

package eu.ydp.empiria.player.client.module.external.interaction;

import eu.ydp.empiria.player.client.module.external.common.ExternalFolderNameProvider;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalPathsTest {

    @InjectMocks
    private ExternalPaths testObj;
    @Mock
    private ExternalFolderNameProvider externalFolderNameProvider;

    @Before
    public void init() {
        when(externalFolderNameProvider.getExternalFolderName()).thenReturn("src");
        testObj.setExternalFolderNameProvider(externalFolderNameProvider);
    }

    @Test
    public void shouldReturnPathToFileInExternalModuleFolder() {
        // given
        String fileName = "file";
        String expectedPath = "media/src/file";
        when(externalFolderNameProvider.getExternalRelativePath("src/file")).thenReturn(expectedPath);

        // when
        String pathToFile = testObj.getExternalFilePath(fileName);

        // then
        assertThat(pathToFile).isEqualTo(expectedPath);
    }

    @Test
    public void shouldReturnPathToEntryPoint() {
        // given
        String expectedPath = "media/src/index.html";
        when(externalFolderNameProvider.getExternalRelativePath("src/index.html")).thenReturn(expectedPath);

        // when
        String pathToEntryPoint = testObj.getExternalEntryPointPath();

        // then
        assertThat(pathToEntryPoint).isEqualTo(expectedPath);
    }
}
