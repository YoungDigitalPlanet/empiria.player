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

package eu.ydp.empiria.player.client.module.draggap.structure;

import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.gwtutil.client.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class DragGapBaseBean extends ModuleBean {

    @XmlAttribute(name = "name")
    private String name = StringUtils.EMPTY_STRING;

    @XmlAttribute(name = "expressionMode")
    private String expressionMode = StringUtils.EMPTY_STRING;

    @XmlAttribute(name = "widthBindingGroup")
    private String widthBindingGroup = StringUtils.EMPTY_STRING;

    @XmlAttribute(name = "responseIdentifier")
    private String responseIdentifier = StringUtils.EMPTY_STRING;

    @XmlAttribute(name = "sourcelistId")
    private String sourcelistId = StringUtils.EMPTY_STRING;

    public String getName() {
        return name;
    }

    public String getExpressionMode() {
        return expressionMode;
    }

    public String getWidthBindingGroup() {
        return widthBindingGroup;
    }

    public String getResponseIdentifier() {
        return responseIdentifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpressionMode(String expressionMode) {
        this.expressionMode = expressionMode;
    }

    public void setWidthBindingGroup(String widthBindingGroup) {
        this.widthBindingGroup = widthBindingGroup;
    }

    public void setResponseIdentifier(String responseIdentifier) {
        this.responseIdentifier = responseIdentifier;
    }

    public String getSourcelistId() {
        return sourcelistId;
    }

    public void setSourcelistId(String sourcelistId) {
        this.sourcelistId = sourcelistId;
    }

}
