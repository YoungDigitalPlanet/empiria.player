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

package eu.ydp.empiria.player.client.module.labelling;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.TestJAXBParser;
import eu.ydp.empiria.player.client.module.labelling.structure.ChildBean;
import eu.ydp.empiria.player.client.module.labelling.structure.ChildrenBean;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingInteractionBean;
import eu.ydp.gwtutil.xml.XMLParser;

import java.util.List;

public class LabellingInteractionBeanMockParser extends TestJAXBParser<LabellingInteractionBean> {

    private List<ChildData> children;

    public LabellingInteractionBeanMockParser(List<ChildData> children) {
        this.children = children;
    }

    @Override
    public LabellingInteractionBean parse(String xml) {
        LabellingInteractionBean bean = super.parse(xml);

        ChildrenBean currChildren = bean.getChildren();
        if (currChildren != null) {
            updateXmlContent(currChildren);
        }
        return bean;
    }

    private void updateXmlContent(ChildrenBean currChildren) {
        List<ChildBean> childBeans = currChildren.getChildBeanList();
        for (int i = 0; i < childBeans.size(); i++) {
            XMLContent content = createXmlContent(i);
            childBeans.get(i).setContent(content);
        }
    }

    private XMLContent createXmlContent(int childIndex) {
        final Element childElement = createChildElement(childIndex);
        return new XMLContent() {

            @Override
            public Element getValue() {
                return childElement;
            }
        };
    }

    private Element createChildElement(int childIndex) {
        ChildData child = children.get(childIndex);
        String xml = child.xml();
        Document document = XMLParser.parse(xml);
        return document.getDocumentElement();
    }
}
