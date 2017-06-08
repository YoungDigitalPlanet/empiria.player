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

package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "showText")
public class ShowTextAction implements FeedbackAction {

    @XmlValue
    @XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
    private XMLContent content;
    @XmlAttribute
    private Integer notify;
    @XmlAttribute
    private String notifyOperator;

    public void setContent(XMLContent content) {
        this.content = content;
    }

    public XMLContent getContent() {
        return content;
    }

    public Integer getNotify() {
        return notify;
    }

    public void setNotify(Integer notify) {
        this.notify = notify;
    }

    public String getNotifyOperator() {
        return notifyOperator;
    }

    public void setNotifyOperator(String notifyOperator) {
        this.notifyOperator = notifyOperator;
    }

    public boolean hasNotify() {
        return notify != null;
    }

    @Override
    public String toString() {
        return "ShowTextAction [text=" + content.toString() + "]";
    }
}
