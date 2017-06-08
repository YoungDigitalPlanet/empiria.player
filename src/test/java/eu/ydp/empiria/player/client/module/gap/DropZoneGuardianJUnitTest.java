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

package eu.ydp.empiria.player.client.module.gap;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class DropZoneGuardianJUnitTest extends AbstractTestBase {

    private DropZoneGuardian dropZoneGuardian;

    private DroppableObject<? extends Widget> droppable;
    private Widget moduleWidget;
    private StyleNameConstants styleNames;

    private final String DROP_ZONE_LOCKED_STYLE = "qp-drop-zone-locked";

    @Override
    @Before
    public void setUp() {

        droppable = mock(DroppableObject.class);

        moduleWidget = mock(Widget.class);

        styleNames = mock(StyleNameConstants.class);
        when(styleNames.QP_DROP_ZONE_LOCKED()).thenReturn(DROP_ZONE_LOCKED_STYLE);

        dropZoneGuardian = new DropZoneGuardian(droppable, moduleWidget, styleNames);
    }

    @Test
    public void shouldLockDropZone() {
        // when
        dropZoneGuardian.lockDropZone();

        // then
        verify(droppable).setDisableDrop(true);
        verify(moduleWidget).addStyleName(DROP_ZONE_LOCKED_STYLE);
    }

    @Test
    public void shouldUnlockDropZone() {
        // when
        dropZoneGuardian.unlockDropZone();

        // then
        verify(droppable).setDisableDrop(false);
        verify(moduleWidget).removeStyleName(DROP_ZONE_LOCKED_STYLE);
    }
}
