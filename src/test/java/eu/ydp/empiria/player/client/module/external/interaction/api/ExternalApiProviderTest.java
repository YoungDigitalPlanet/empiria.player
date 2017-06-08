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

package eu.ydp.empiria.player.client.module.external.interaction.api;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ExternalApiProviderTest {

    private ExternalApiProvider testObj = new ExternalApiProvider();

    @Test
    public void shouldReturnNullApiObject_whenNotOtherApiWasSet() throws Exception {
        // WHEN
        ExternalInteractionApi result = testObj.getExternalApi();

        // THEN
        assertThat(result).isInstanceOf(ExternalInteractionApiNullObject.class);
    }

    @Test
    public void shouldReturnSetApiObject() throws Exception {
        // GIVEN
        ExternalInteractionApi givenApi = mock(ExternalInteractionApi.class);
        testObj.setExternalApi(givenApi);

        // WHEN
        ExternalInteractionApi result = testObj.getExternalApi();

        // THEN
        assertThat(result).isEqualTo(givenApi);
    }
}