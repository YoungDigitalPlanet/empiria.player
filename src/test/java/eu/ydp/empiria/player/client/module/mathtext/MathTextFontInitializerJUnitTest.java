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

package eu.ydp.empiria.player.client.module.mathtext;

import com.google.gwt.xml.client.Element;
import com.mathplayer.player.geom.Font;
import eu.ydp.empiria.player.client.module.core.base.IInlineModule;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MathTextFontInitializerJUnitTest {

    @InjectMocks
    private MathTextFontInitializer helper;
    @Mock
    private StyleSocket styleSocket;

    @Test
    public void updateFontPropertiesAccordingToInlineFormatters() {
        IInlineModule testModule = mock(IInlineModule.class);
        ModuleSocket socket = mock(ModuleSocket.class);
        Element element = mock(Element.class);
        Set<InlineFormattingContainerType> inlineStyles = new HashSet<InlineFormattingContainerType>();
        inlineStyles.add(InlineFormattingContainerType.BOLD);
        when(socket.getInlineFormattingTags(testModule)).thenReturn(inlineStyles);

        Font result = helper.initialize(testModule, socket, element);

        assertThat(result.bold, is(equalTo(true)));
    }

    @Test
    public void updateFontPropertiesAccordingToStyles() {
        DTOMathTextDefaultFontPropertiesProvider defaultFontPropertiesProvider = new DTOMathTextDefaultFontPropertiesProvider();
        DTOMathTextFontProperties fontProperties = defaultFontPropertiesProvider.createDefaultProprerties();
        HashMap<String, String> styles = new HashMap<String, String>();
        styles.put("-empiria-math-font-size", "26");

        helper.updateFontPropertiesAccordingToStyles(styles, fontProperties);

        assertThat(fontProperties.getSize(), is(equalTo(26)));
    }
}
