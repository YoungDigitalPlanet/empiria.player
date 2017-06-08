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

package eu.ydp.empiria.player.client.module.bonus;

import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageBonusTest {

    @InjectMocks
    private ImageBonus bonus;
    @Mock
    private BonusPopupPresenter presenter;
    @Mock
    private EmpiriaPaths empiriaPaths;

    @Test
    public void execute() {
        // given
        String url = "bonus.png";
        String fullUrl = "http://x.y.z/bonus1/bonus.png";
        Size size = new Size(100, 100);
        when(empiriaPaths.getCommonsFilePath(url)).thenReturn(fullUrl);
        bonus.setAsset(url, size);

        // when
        bonus.execute();

        // then
        verify(presenter).showImage(fullUrl, size);
    }
}
