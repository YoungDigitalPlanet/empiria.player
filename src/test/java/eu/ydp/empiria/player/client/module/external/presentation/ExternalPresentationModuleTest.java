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

package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ExternalPresentationModuleTest {

    @InjectMocks
    private ExternalPresentationModule testObj;
    @Mock
    private ExternalPresentationPresenter presenter;
    @Mock
    private ExternalPaths externalPaths;
    @Mock
    private Element element;
    @Mock
    private EmpiriaPaths empiriaPaths;
    private static final String source = "SOURCE";
    private static final String ID = "ID";

    @Before
    public void setUp() throws Exception {
        when(element.getAttribute("src")).thenReturn(source);
        when(element.getAttribute("id")).thenReturn(ID);
    }

    @Test
    public void shouldRegisterAsFolderNameProviderAndThenInitializePresenter() {
        // when
        testObj.initModule(element);

        // then
        InOrder inOrder = inOrder(externalPaths, presenter);
        inOrder.verify(externalPaths).setExternalFolderNameProvider(testObj);
        inOrder.verify(presenter).init();
    }

    @Test
    public void shouldReturnPresentationNameAsModuleIdentifier() {
        // given
        testObj.initModule(element, mock(ModuleSocket.class), mock(EventsBus.class));

        // when
        String actual = testObj.getIdentifier();

        // then
        assertThat(actual).isEqualTo(ID);
    }

    @Test
    public void shouldReturnPresentationNameAsExsternalFolderName() {
        // given
        testObj.initModule(element);

        // when
        String actual = testObj.getExternalFolderName();

        // then
        assertThat(actual).isEqualTo(source);
    }

    @Test
    public void shouldReturnPathToCommon_whenCoursePresentationModule() {
        // when
        String expectedPath = "common/NAME/test.jpg";
        String tagName = "externalCommonPresentation";
        String filename = "NAME/test.jpg";

        when(element.getTagName()).thenReturn(tagName);
        when(empiriaPaths.getCommonsFilePath(filename)).thenReturn(expectedPath);

        testObj.initModule(element);

        // when
        String actual = testObj.getExternalRelativePath(filename);

        // then
        assertThat(actual).isEqualTo(expectedPath);
    }

    @Test
    public void shouldReturnPathToMedia_whenLessonPresentationModule() {
        // when
        String expectedPath = "media/NAME/test.jpg";
        String tagName = "externalPresentation";
        String filename = "NAME/test.jpg";

        when(element.getTagName()).thenReturn(tagName);
        when(empiriaPaths.getMediaFilePath(filename)).thenReturn(expectedPath);

        testObj.initModule(element);

        // when
        String actual = testObj.getExternalRelativePath(filename);

        // then
        assertThat(actual).isEqualTo(expectedPath);
    }
}
