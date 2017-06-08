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

package eu.ydp.empiria.player.client.module.selection.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.structure.SimpleChoiceBaseBean;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "simpleChoice")
public class SelectionSimpleChoiceBean extends SimpleChoiceBaseBean implements PairChoiceBean {

    @XmlAttribute
    protected int matchMax;

    @XmlValue
    @XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
    private XMLContent xmlContent;

    @Override
    public int getMatchMax() {
        return matchMax;
    }

    public void setMatchMax(int matchMax) {
        this.matchMax = matchMax;
    }

    @Override
    public boolean isFixed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public XMLContent getXmlContent() {
        return xmlContent;
    }

    public void setXmlContent(XMLContent xmlContent) {
        this.xmlContent = xmlContent;
    }
}
