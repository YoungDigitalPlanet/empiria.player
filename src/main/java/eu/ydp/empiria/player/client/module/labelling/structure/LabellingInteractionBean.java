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

package eu.ydp.empiria.player.client.module.labelling.structure;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "labellingInteraction")
public class LabellingInteractionBean {

    @XmlElement
    private ImgBean img;
    @XmlElement
    private ChildrenBean children;
    @XmlAttribute
    private String id;

    public ImgBean getImg() {
        return img;
    }

    public void setImg(ImgBean img) {
        this.img = img;
    }

    public ChildrenBean getChildren() {
        return children;
    }

    public void setChildren(ChildrenBean children) {
        this.children = children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return Objects.firstNonNull(id, "");
    }

}
