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

package eu.ydp.empiria.player.client.module.expression.adapters;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import eu.ydp.empiria.player.client.style.StyleSocket;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static eu.ydp.empiria.player.client.module.expression.adapters.ExpressionCharacterMappingProvider.SELECTOR;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_EXPRESSION_MAPPING;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ExpressionCharacterMappingProviderJUnitTest {

    private ExpressionCharacterMappingProvider provider;
    private final StyleSocket styleSocket = mock(StyleSocket.class);
    private final ExpressionCharactersMappingParser parser = Guice.createInjector().getInstance(ExpressionCharactersMappingParser.class);

    @Before
    public void setUp() {
        provider = new ExpressionCharacterMappingProvider(styleSocket, parser);
    }

    @Test
    public void getMapping() {
        // given
        when(styleSocket.getStyles(SELECTOR)).thenReturn(ImmutableMap.of(EMPIRIA_EXPRESSION_MAPPING, " ×|*|:,;,÷|/|≤|<= "));

        // when
        Map<String, String> replacements = provider.getMapping();

        // then
        assertThat(replacements).isEqualTo(ImmutableMap.of("×", "*", ":", "/", ";", "/", "÷", "/", "≤", "<="));
    }

    @Test
    public void caching() {
        // given
        when(styleSocket.getStyles(SELECTOR)).thenReturn(ImmutableMap.of(EMPIRIA_EXPRESSION_MAPPING, " ×,•|*|:,;|/ "));

        // when
        provider.getMapping();
        provider.getMapping();

        // then
        verify(styleSocket).getStyles(SELECTOR);
    }

    @Test
    public void mappingNotSet() {
        // given
        when(styleSocket.getStyles(SELECTOR)).thenReturn(new HashMap<String, String>());

        // when
        Map<String, String> replacements = provider.getMapping();

        // then
        assertThat(replacements).isEmpty();
    }

    @Test
    public void empty() {
        // given
        when(styleSocket.getStyles(SELECTOR)).thenReturn(ImmutableMap.of(EMPIRIA_EXPRESSION_MAPPING, ""));

        // when
        Map<String, String> replacements = provider.getMapping();

        // then
        assertThat(replacements).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidNumberOfParts() {
        // given
        when(styleSocket.getStyles(SELECTOR)).thenReturn(ImmutableMap.of(EMPIRIA_EXPRESSION_MAPPING, " *|+|/"));

        // when
        provider.getMapping();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidMapping() {
        // given
        when(styleSocket.getStyles(SELECTOR)).thenReturn(ImmutableMap.of(EMPIRIA_EXPRESSION_MAPPING, " *||:|/"));

        // when
        provider.getMapping();
    }

}
