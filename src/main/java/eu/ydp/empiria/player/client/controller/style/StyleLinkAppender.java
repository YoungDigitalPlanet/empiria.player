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

public class StyleLinkAppender {

    public void appendStyleLink(String link) {
        appendStyleLinkNative(link);
    }

    private native void appendStyleLinkNative(String link) /*-{
        var headID = $wnd.document.getElementsByTagName("head")[0];
        var cssNode = $wnd.document.createElement('link');
        cssNode.type = 'text/css';
        cssNode.rel = 'stylesheet';
        cssNode.href = link;
        cssNode.media = 'screen';
        headID.appendChild(cssNode);
    }-*/;
}
