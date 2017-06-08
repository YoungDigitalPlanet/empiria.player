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

package eu.ydp.empiria.player.client.module.progressbonus;

import com.google.common.collect.Range;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ProgressAssetTest {

    private static final Integer ID = 7;

    @Test
    public void shouldReturnImageForRange() {
        // given
        ProgressAsset asset = new ProgressAsset(ID);

        ShowImageDTO dto1 = createShowImageDTO("PATH1");
        asset.add(Range.closedOpen(0, 100), dto1);

        ShowImageDTO dto100 = createShowImageDTO("PATH100");
        asset.add(Range.atLeast(100), dto100);

        // when
        ShowImageDTO imageFor0 = asset.getImageForProgress(45);
        ShowImageDTO imageFor100 = asset.getImageForProgress(100);

        // then
        assertThat(imageFor0.path).isEqualTo("PATH1");
        assertThat(imageFor100.path).isEqualTo("PATH100");
        assertThat(asset.getId()).isEqualTo(ID);
    }

    private ShowImageDTO createShowImageDTO(String path) {
        return new ShowImageDTO(path, new Size(300, 400));
    }

}
