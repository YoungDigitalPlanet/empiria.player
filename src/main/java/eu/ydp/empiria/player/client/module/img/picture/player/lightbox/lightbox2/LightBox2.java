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

package eu.ydp.empiria.player.client.module.img.picture.player.lightbox.lightbox2;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;

@Singleton
public class LightBox2 implements LightBox {

    private final JavaScriptObject lightbox;

    public LightBox2() {
        lightbox = enableLightBox2();
    }

    private native JavaScriptObject enableLightBox2()/*-{
        var lightbox, options;
        options = new $wnd.LightboxOptions();
        lightbox = new $wnd.Lightbox(options);

        return lightbox;
    }-*/;

    @Override
    public void openImage(String url, Widget title) {
        openImageNative(url, title.getElement(), lightbox);
    }

    private native void openImageNative(String url, Element title, JavaScriptObject lightbox) /*-{
        lightbox.clear();
        lightbox.add(url, title);
        lightbox.start();

    }-*/;

}
