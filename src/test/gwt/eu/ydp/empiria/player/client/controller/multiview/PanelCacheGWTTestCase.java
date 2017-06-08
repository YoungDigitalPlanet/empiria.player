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

package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeType;
import eu.ydp.gwtutil.client.collections.KeyValue;

@SuppressWarnings("PMD")
public class PanelCacheGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testPageViewWithSwipeAndWithout() {
        PanelCache cache = PlayerGinjectorFactory.getNewPlayerGinjectorForGWTTestCase().getPanelCache();
        assertTrue(cache.isEmpty());
        KeyValue<FlowPanel, FlowPanel> value = cache.getOrCreateAndPut(0);
        Style style = value.getKey().getElement().getStyle();
        assertTrue(style.getPosition().equals(Position.ABSOLUTE.getCssName()));
        assertTrue(style.getTop().equals("0px"));
        assertEquals("0.0%", style.getLeft());
        assertEquals("100.0%", style.getWidth());
        value = cache.getOrCreateAndPut(2);
        style = value.getKey().getElement().getStyle();
        assertEquals(Position.ABSOLUTE.getCssName(), style.getPosition());
        assertEquals("0px", style.getTop());
        assertEquals("200.0%", style.getLeft());
        assertEquals("100.0%", style.getWidth());
        cache.setSwipeType(SwipeType.DISABLED);
        value = cache.getOrCreateAndPut(3);
        assertNotSame(Position.ABSOLUTE.getCssName(), style.getPosition());
        assertNotSame("0px", style.getTop());
        assertNotSame("200.0%", style.getLeft());
        assertNotSame("100.0%", style.getWidth());

    }

}
