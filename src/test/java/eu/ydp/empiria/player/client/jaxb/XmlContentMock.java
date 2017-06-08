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

package eu.ydp.empiria.player.client.jaxb;

import com.google.gwt.xml.client.Element;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.gwtutil.client.xml.EmptyElement;

public class XmlContentMock implements XMLContent {

    private String stringContent;

    public XmlContentMock() {
        stringContent = "";
    }

    public XmlContentMock(String stringContent) {
        this.stringContent = stringContent;
    }

    @Override
    public Element getValue() {
        return new EmptyElement("elementName");
    }

    public void setStringElement(String str) {
        stringContent = str;
    }

    @Override
    public String toString() {
        return stringContent;
    }
}
