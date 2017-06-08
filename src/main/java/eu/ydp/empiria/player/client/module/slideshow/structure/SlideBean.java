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

package eu.ydp.empiria.player.client.module.slideshow.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "slide")
public class SlideBean {

    @XmlElement
    private SoundBean sound;
    @XmlElement
    private SourceBean source;
    @XmlElement
    private SlideNarrationBean narration;
    @XmlElement
    private SlideTitleBean slideTitle;

    public SourceBean getSource() {
        return source;
    }

    public void setSource(SourceBean src) {
        this.source = src;
    }

    public SlideNarrationBean getNarration() {
        return narration;
    }

    public void setNarration(SlideNarrationBean narration) {
        this.narration = narration;
    }

    public boolean hasNarration() {
        return narration != null;
    }

    public SlideTitleBean getSlideTitle() {
        return slideTitle;
    }

    public void setSlideTitle(SlideTitleBean title) {
        this.slideTitle = title;
    }

    public boolean hasSlideTitle() {
        return slideTitle != null;
    }

    public SoundBean getSound() {
        return sound;
    }

    public void setSound(SoundBean audio) {
        this.sound = audio;
    }

    public boolean hasSound() {
        return this.sound != null;
    }
}
