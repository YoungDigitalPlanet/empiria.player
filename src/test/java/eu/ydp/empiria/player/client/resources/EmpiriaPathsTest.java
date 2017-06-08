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

package eu.ydp.empiria.player.client.resources;

import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class EmpiriaPathsTest {

    @InjectMocks
    private EmpiriaPaths empiriaPaths;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private DataSourceManager dataSourceManager;
    @Mock
    private XmlData assesmentData;

    @Mock
    private XmlData itemData;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(dataSourceManager.getAssessmentData().getData()).thenReturn(assesmentData);
        when(dataSourceManager.getItemData(0).getData()).thenReturn(itemData);
    }

    @Test
    @Parameters(method = "itemURLParams")
    public void shouldReturnMediaFilepath(String path) {
        // given
        String filepath = "external/module.html";
        when(itemData.getBaseURL()).thenReturn(path);

        // when
        String actual = empiriaPaths.getMediaFilePath(filepath);

        // then
        assertThat(actual).isEqualTo("http://url/test/script_00001/media/external/module.html");
    }

    @Test
    @Parameters(method = "baseURLParams")
    public void shouldGetBasePath(String path) {
        // when
        when(assesmentData.getBaseURL()).thenReturn(path);
        final String basePath = empiriaPaths.getBasePath();

        // then
        verify(dataSourceManager.getAssessmentData()).getData();
        verify(dataSourceManager.getAssessmentData(), never()).getSkinData(); // skin
        // specific
        // data
        assertThat(basePath).isEqualTo("http://url/test/");
    }

    @Test
    @Parameters(method = "baseURLParams")
    public void testGetCommonsPath(String path) throws Exception {
        // when
        when(assesmentData.getBaseURL()).thenReturn(path);
        final String commonsPath = empiriaPaths.getCommonsPath();

        // then
        assertThat(commonsPath).isEqualTo("http://url/test/common/");
    }

    @Test
    @Parameters(method = "baseURLParams")
    public void shouldGetFilePath(String path) {
        // given
        when(assesmentData.getBaseURL()).thenReturn(path);
        final String filename = "file.png";

        // when
        String filepath = empiriaPaths.getCommonsFilePath(filename);

        // then
        assertThat(filepath).isEqualTo("http://url/test/common/file.png");
    }

    @Test
    @Parameters(method = "baseURLParams")
    public void shouldReturnPathRelativeToCommons(String path) {
        // given
        final String relativePath = "some/dir";

        when(assesmentData.getBaseURL()).thenReturn(path);
        final String expected = "http://url/test/common/some/dir/";

        // when
        String actual = empiriaPaths.getPathRelativeToCommons(relativePath);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Parameters(method = "baseURLParams")
    public void shouldReturnPathRelativeToCommons_trailingSlash(String path) {
        // given
        final String relativePath = "some/dir/";

        when(assesmentData.getBaseURL()).thenReturn(path);
        final String expected = "http://url/test/common/some/dir/";

        // when
        String actual = empiriaPaths.getPathRelativeToCommons(relativePath);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    Object[] baseURLParams() {
        return $("http://url/test/", "http://url/test");
    }

    Object[] itemURLParams() {
        return $("http://url/test/script_00001/", "http://url/test/script_00001");
    }

}
