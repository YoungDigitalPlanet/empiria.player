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

package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaletteButtonCreatorTest {

    @InjectMocks
    private PaletteButtonCreator creator;

    @Mock
    private UserInteractionHandlerFactory userInteractionHandlerFactory;

    @Mock
    private Provider<PaletteButton> buttonProvider;

    @Test
    public void createButton() {
        // given
        String description = "buttonDescription";
        ColorModel color = ColorModel.createEraser();
        Command command = mock(Command.class);
        when(buttonProvider.get()).thenReturn(mock(PaletteButton.class));

        // when
        PaletteButton button = creator.createButton(color, command, description);

        // then
        verify(button).setColor(eq(color));
        verify(button).setDescription(description);
        verify(userInteractionHandlerFactory).applyUserClickHandler(eq(command), eq(button));
    }

}
