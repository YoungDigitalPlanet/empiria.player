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

package eu.ydp.empiria.player.client.module.drawing.model;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DrawingModelProvider implements Provider<DrawingBean> {

    @Inject
    private DrawingModuleJAXBParserFactory jaxbFactory;
    @Inject
    @ModuleScoped
    Provider<Element> elementProvider;

    @Override
    public DrawingBean get() {
        Element element = elementProvider.get();
        JAXBParser<DrawingBean> parser = jaxbFactory.create();
        return parser.parse(element.toString());
    }

}
