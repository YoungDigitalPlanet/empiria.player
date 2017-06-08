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

package eu.ydp.empiria.player.module.abstractmodule.structure;

import com.google.gwt.xml.client.Element;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.gwtutil.xml.XMLParser;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XMLContentTypeAdapter extends XmlAdapter<String, XMLContent> {

    @Override
    public XMLContent unmarshal(final String value) throws Exception {
        return new XMLContent() {

            @Override
            public Element getValue() {
                return XMLParser.parse("<root_from_XMLContentTypeAdapter>" + value + "</root_from_XMLContentTypeAdapter>").getDocumentElement();
            }
        };
    }

    @Override
    public String marshal(XMLContent value) throws Exception {
        return null;
    }
}
