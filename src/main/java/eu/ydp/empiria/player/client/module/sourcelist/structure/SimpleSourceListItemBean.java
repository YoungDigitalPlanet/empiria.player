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

package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.google.common.base.Predicate;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasFixed;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "simpleSourceListItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleSourceListItemBean implements HasFixed {

    private static final String SRC_ATTR = "src";
    private static final String IMG_NODE = "img";

    @XmlAttribute
    private String alt;

    @XmlAttribute
    private boolean fixed;

    @XmlValue
    @XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
    private XMLContent content;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public SourcelistItemValue getItemValue(Predicate<Element> hasComplexTextPredicate) {
        Element value = content.getValue();
        NodeList imgNodes = value.getElementsByTagName(IMG_NODE);
        if (imgNodes.getLength() > 0) {
            return createImageItemValue();
        }

        boolean hasComplexText = hasComplexTextPredicate.apply(value);
        if (hasComplexText) {
            return createComplexTexItemValue();
        }

        return createTextItemValue();
    }

    private SourcelistItemValue createImageItemValue() {
        NodeList imgNodes = content.getValue().getElementsByTagName(IMG_NODE);
        Element imgElement = (Element) imgNodes.item(0);
        String src = imgElement.getAttribute(SRC_ATTR);
        return new SourcelistItemValue(SourcelistItemType.IMAGE, src, alt);
    }

    private SourcelistItemValue createTextItemValue() {
        return createTypedItemValue(SourcelistItemType.TEXT);
    }

    private SourcelistItemValue createComplexTexItemValue() {
        return createTypedItemValue(SourcelistItemType.COMPLEX_TEXT);
    }

    private SourcelistItemValue createTypedItemValue(SourcelistItemType type) {
        NodeList textNode = content.getValue().getChildNodes();
        String text = textNode.toString();
        return new SourcelistItemValue(type, text, alt);
    }

    public void setContent(XMLContent content) {
        this.content = content;
    }

    public XMLContent getContent() {
        return content;
    }

    @Override
    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
