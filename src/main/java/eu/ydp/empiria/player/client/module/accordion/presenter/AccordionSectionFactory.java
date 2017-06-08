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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.accordion.AccordionContentGenerator;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionSectionBean;

@Singleton
public class AccordionSectionFactory {
    @Inject
    private Provider<AccordionSectionPresenter> sectionProvider;

    public AccordionSectionPresenter createSection(AccordionSectionBean bean, AccordionContentGenerator generator) {
        AccordionSectionPresenter presenter = sectionProvider.get();

        Element title = bean.getTitle().getValue();
        Widget widget = generator.generateInlineBody(title);
        presenter.setTitle(widget);

        Element content = bean.getContent().getValue();
        generator.generateBody(content, presenter.getContentContainer());

        return presenter;
    }
}
