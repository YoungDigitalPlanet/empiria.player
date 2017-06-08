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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.structure.ModuleBean;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sourceList")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceListBean extends ModuleBean implements HasShuffle {

    @XmlAttribute
    private boolean moveElements;

    @XmlAttribute
    private boolean shuffle;

    @XmlAttribute
    private int imagesWidth;

    @XmlAttribute
    private int imagesHeight;

    @XmlElement(name = "simpleSourceListItem")
    private List<SimpleSourceListItemBean> simpleSourceListItemBeans = Lists.newArrayList();

    @XmlAttribute
    private String sourcelistId;

    public boolean isMoveElements() {
        return moveElements;
    }

    public void setMoveElements(boolean moveElements) {
        this.moveElements = moveElements;
    }

    @Override
    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public List<SimpleSourceListItemBean> getSimpleSourceListItemBeans() {
        return ImmutableList.copyOf(simpleSourceListItemBeans);
    }

    public void setSimpleSourceListItemBeans(List<SimpleSourceListItemBean> simpleSourceListItemBeans) {
        this.simpleSourceListItemBeans = simpleSourceListItemBeans;
    }

    public int getImagesHeight() {
        return imagesHeight;
    }

    public void setImagesHeight(int imagesHeight) {
        this.imagesHeight = imagesHeight;
    }

    public int getImagesWidth() {
        return imagesWidth;
    }

    public void setImagesWidth(int imagesWidth) {
        this.imagesWidth = imagesWidth;
    }

    public String getSourcelistId() {
        return sourcelistId;
    }

    public void setSourcelistId(String sourcelistId) {
        this.sourcelistId = sourcelistId;
    }

}
