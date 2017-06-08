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

package eu.ydp.empiria.player.client.module.video.structure;

import eu.ydp.empiria.player.client.module.model.media.MimeType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "source")
@XmlAccessorType(XmlAccessType.NONE)
public class SourceBean {

    @XmlAttribute(required = true)
    private String src;
    @XmlAttribute(name = "type", required = true)
    private String typeString;
    private MimeType mimeType;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        mimeType = MimeType.fromValue(typeString);
        this.typeString = typeString;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mime) {
        typeString = mime.value();
        this.mimeType = mime;
    }
}
