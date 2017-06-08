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

import com.google.common.base.Strings;
import eu.ydp.empiria.player.client.structure.ModuleBean;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "img")
public class PicturePlayerBean extends ModuleBean {

    @XmlElement(name = "title")
    private PicturePlayerTitleBean titleBean;
    @XmlAttribute
    private String alt;
    @XmlAttribute
    private String src;
    @XmlAttribute
    private String srcFullScreen;
    @XmlAttribute
    private String fullscreenMode;

    public PicturePlayerTitleBean getTitleBean() {
        return titleBean;
    }

    public void setTitleBean(PicturePlayerTitleBean titleBean) {
        this.titleBean = titleBean;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrcFullScreen() {
        return srcFullScreen;
    }

    public void setSrcFullScreen(String srcFullScreen) {
        this.srcFullScreen = srcFullScreen;
    }

    public String getFullscreenMode() {
        return fullscreenMode;
    }

    public void setFullscreenMode(String fullscreenMode) {
        this.fullscreenMode = fullscreenMode;
    }

    public boolean hasTitle() {
        return titleBean != null;
    }

    public boolean hasAlt() {
        return alt != null;
    }

    public boolean hasFullscreen() {
        return !Strings.isNullOrEmpty(getSrcFullScreen());
    }
}
