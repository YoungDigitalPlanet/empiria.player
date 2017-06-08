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

package eu.ydp.empiria.player.client.module.model.image;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.gwtutil.client.util.geom.Size;
import eu.ydp.gwtutil.client.util.geom.WidgetSize;

public class ImageShowDelegate {

    private ShowImageDTO dto;

    public ImageShowDelegate(ShowImageDTO dto) {
        this.dto = dto;
    }

    public void showOnWidget(Widget content) {
        String srcWithUrlInside = createPath(dto.path);
        setImage(content, srcWithUrlInside);
        setSize(dto.size, content);
    }

    private String createPath(String path) {
        return "url(" + path + ")";
    }

    private void setImage(Widget content, String srcWithUrlInside) {
        Style style = content.getElement().getStyle();
        style.setBackgroundImage(srcWithUrlInside);
    }

    private void setSize(Size size, Widget content) {
        WidgetSize widgetSize = new WidgetSize(size);
        widgetSize.setOnWidget(content);
    }
}
