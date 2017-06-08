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

package eu.ydp.empiria.player.client.controller.style;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class StyleLinkManagerTest {

    @InjectMocks
    private StyleLinkManager testObj;
    @Mock
    private StyleLinkAppender styleLinkAppender;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(styleLinkAppender);
    }

    @Test
    public void testRegisterAssessmentStyles() {
        String style1 = "style1";
        String style2 = "style2";
        testObj.registerAssessmentStyles(Lists.newArrayList(style1, style2, style1));

        verify(styleLinkAppender).appendStyleLink(style1);
        verify(styleLinkAppender).appendStyleLink(style2);
    }

    @Test
    public void testRegisterItemStyles() {
        String style1 = "style1";
        String style2 = "style2";
        testObj.registerItemStyles(Lists.newArrayList(style1, style2, style1));

        verify(styleLinkAppender).appendStyleLink(style1);
        verify(styleLinkAppender).appendStyleLink(style2);
    }

}
