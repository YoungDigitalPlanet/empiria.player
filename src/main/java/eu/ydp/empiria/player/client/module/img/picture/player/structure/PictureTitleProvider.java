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

package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

@Singleton
public class PictureTitleProvider {

    public Widget getPictureTitleWidget(PicturePlayerBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        if (bean.hasTitle()) {
            Element titleXmlElement = bean.getTitleBean().getTitleName().getValue();
            return inlineBodyGeneratorSocket.generateInlineBody(titleXmlElement);
        } else {
            return new FlowPanel();
        }
    }

    public String getPictureTitleString(PicturePlayerBean bean) {
        if (bean.hasTitle()) {
            Element titleXmlElement = bean.getTitleBean().getTitleName().getValue();
            return stripTextFromHTMLTags(titleXmlElement);
        } else {
            return "";
        }
    }

    private String stripTextFromHTMLTags(Element titleXmlElement) {
        return titleXmlElement.toString().replaceAll("<.*?>", "");
    }
}
