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

package eu.ydp.empiria.player.client.module.accordion.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.accordion.AccordionContentGenerator;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.empiria.player.client.module.accordion.controller.AccordionController;
import eu.ydp.empiria.player.client.module.accordion.controller.AccordionSectionsControllerProvider;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionBean;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionSectionBean;
import eu.ydp.empiria.player.client.module.accordion.view.AccordionView;
import eu.ydp.gwtutil.client.event.factory.Command;

public class AccordionPresenter implements IsWidget {

    private final AccordionView view;
    private final AccordionSectionFactory sectionFactory;
    private final AccordionSectionsControllerProvider controllerProvider;

    @Inject
    public AccordionPresenter(AccordionView view, AccordionSectionFactory sectionFactory, AccordionSectionsControllerProvider controllerProvider) {
        this.view = view;
        this.sectionFactory = sectionFactory;
        this.controllerProvider = controllerProvider;
    }

    public void initialize(AccordionBean bean, AccordionContentGenerator generator) {
        Transition transition = bean.getTransition();
        AccordionController controller = controllerProvider.getController(transition);

        for (AccordionSectionBean accordionSectionBean : bean.getSections()) {
            AccordionSectionPresenter section = sectionFactory.createSection(accordionSectionBean, generator);
            section.init(transition);
            section.addClickCommand(createClickEvent(controller, section));
            view.addSection(section);
        }
    }

    private Command createClickEvent(final AccordionController controller, final AccordionSectionPresenter section) {
        return new Command() {
            @Override
            public void execute(NativeEvent event) {
                controller.onClick(section);
            }
        };
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
}
