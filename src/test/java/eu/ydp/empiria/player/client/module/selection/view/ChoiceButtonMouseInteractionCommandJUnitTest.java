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

package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.junit.GWTMockUtilities;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("PMD")
public class ChoiceButtonMouseInteractionCommandJUnitTest {

    private final SelectionChoiceButton button = mock(SelectionChoiceButton.class);
    private ChoiceButtonMouseInteractionCommand instance;

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Test
    public void testExecuteMouseOver() {
        instance = new ChoiceButtonMouseInteractionCommand(button, true);
        instance.execute(mock(NativeEvent.class));
        verify(button).setMouseOver(true);
    }

    @Test
    public void testExecuteMouseNotOver() {
        instance = new ChoiceButtonMouseInteractionCommand(button, false);
        instance.execute(mock(NativeEvent.class));
        verify(button).setMouseOver(false);
    }

}
