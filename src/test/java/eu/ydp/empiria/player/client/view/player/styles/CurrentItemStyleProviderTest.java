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

package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.controller.data.ItemDataSourceCollectionManager;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentItemStyleProviderTest {

    @InjectMocks
    private CurrentItemStyleProvider testObj;
    @Mock
    private FlowDataSupplier flowData;
    @Mock
    private ItemDataSourceCollectionManager itemDataSourceCollectionManager;
    @Mock
    private ItemStylesContainer itemStylesContainer;

    private String identifier = "id";

    @Before
    public void init() {
        when(flowData.getCurrentPageIndex()).thenReturn(0);
        when(itemDataSourceCollectionManager.getItemIdentifier(0)).thenReturn(identifier);
    }

    @Test
    public void shouldReturnStyleWrappedWithOptional() {
        // given
        String style = "style";
        Optional<String> optionalStyle = Optional.of(style);
        when(itemStylesContainer.getStyle(identifier)).thenReturn(optionalStyle);

        // when
        Optional<String> result = testObj.getCurrentItemStyle();

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(style);
    }

    @Test
    public void shouldReturnEmptyOptional_whenStyleForPageDoesNotExist() {
        // given
        Optional<String> optionalStyle = Optional.absent();
        when(itemStylesContainer.getStyle(identifier)).thenReturn(optionalStyle);

        // when
        Optional<String> result = testObj.getCurrentItemStyle();

        // then
        assertThat(result.isPresent()).isFalse();
    }
}