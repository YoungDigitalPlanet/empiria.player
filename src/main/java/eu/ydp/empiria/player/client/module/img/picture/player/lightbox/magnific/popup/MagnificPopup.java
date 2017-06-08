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

package eu.ydp.empiria.player.client.module.img.picture.player.lightbox.magnific.popup;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;

@Singleton
public class MagnificPopup implements LightBox {

    @Override
    public void openImage(String imageSrc, Widget title) {

        openImageNative(imageSrc, title.getElement());
    }

    private native void openImageNative(String imageSrc, Element title) /*-{
        $wnd.$.magnificPopup.open({
            items: {
                src: imageSrc
            },
            type: 'image',

            image: {
                titleSrc: function () {
                    return title
                }
            },
            closeOnContentClick: true
        });
    }-*/;
}
