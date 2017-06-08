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

package eu.ydp.empiria.player.client.module.labelling;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingInteractionBean;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;

public class LabellingBuilder {

    private final LabellingModuleJAXBParserFactory parserFactory;
    private final LabellingViewBuilder labellingViewBuilder;

    @Inject
    public LabellingBuilder(LabellingModuleJAXBParserFactory parserFactory, LabellingViewBuilder labellingViewBuilder) {
        this.parserFactory = parserFactory;
        this.labellingViewBuilder = labellingViewBuilder;
    }

    public LabellingView build(Element element, BodyGeneratorSocket bgs) {
        LabellingInteractionBean structure = findStructure(element);
        return labellingViewBuilder.buildView(structure, bgs);
    }

    private LabellingInteractionBean findStructure(Element element) {
        JAXBParser<LabellingInteractionBean> parser = parserFactory.create();
        return parser.parse(element.toString());
    }

}
