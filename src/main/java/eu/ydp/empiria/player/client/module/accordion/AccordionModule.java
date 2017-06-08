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

package eu.ydp.empiria.player.client.module.accordion;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionPresenter;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionBean;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionJAXBParser;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;


public class AccordionModule extends AbstractActivityContainerModuleBase {

    @Inject
    private AccordionPresenter presenter;
    @Inject
    private AccordionJAXBParser parser;

    @Override
    public void initModule(Element element) {
        AccordionBean accordionBean = parser.create().parse(element.toString());
        AccordionContentGenerator generator = new AccordionContentGenerator(getModuleSocket().getInlineBodyGeneratorSocket(), getBodyGenerator());
        presenter.initialize(accordionBean, generator);
    }

    @Override
    public Widget getView() {
        return presenter.asWidget();
    }
}
