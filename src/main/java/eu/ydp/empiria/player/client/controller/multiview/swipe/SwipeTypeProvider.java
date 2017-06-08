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

package eu.ydp.empiria.player.client.controller.multiview.swipe;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.xml.XMLParser;

import java.util.Map;

@Singleton
public class SwipeTypeProvider implements Provider<SwipeType> {
    @Inject
    private StyleSocket styleSocket;
    @Inject
    XMLParser xmlParser;

    @Override
    public SwipeType get() {
        String xml = "<root><swipeoptions class=\"qp-swipe-options\"/></root>";
        Element firstChild = (Element) xmlParser.parse(xml).getDocumentElement().getFirstChild();
        Map<String, String> styles = styleSocket.getStyles(firstChild);
        if (styles.get(EmpiriaStyleNameConstants.EMPIRIA_SWIPE_DISABLE_ANIMATION) != null) {
            return SwipeType.DISABLED;
        } else {
            return SwipeType.DEFAULT;
        }
    }
}
