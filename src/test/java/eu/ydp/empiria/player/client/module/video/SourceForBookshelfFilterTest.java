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

package eu.ydp.empiria.player.client.module.video;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;


public class SourceForBookshelfFilterTest {

    private final SourceForBookshelfFilter testObj = new SourceForBookshelfFilter();

    @Test
    public void shouldGetFilteredSources() {
        // given
        String sourceAVI = "source.avi";
        String sourceMP4 = "source.mp4";
        List<String> sources = new ArrayList<>();
        sources.add(sourceAVI);
        sources.add(sourceMP4);

        // when
        List<String> resultList = testObj.getFilteredSources(sources);

        // then
        assertThat(resultList.size()).isEqualTo(1);
        assertThat(resultList.contains(sourceMP4)).isTrue();
        assertThat(resultList.contains(sourceAVI)).isFalse();
    }
}
