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

package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;

public abstract class SimpleContainerModuleBase extends AbstractActivityContainerModuleBase {

    private Panel panel;

    public SimpleContainerModuleBase() {
        this.panel = new FlowPanel();
    }

    public SimpleContainerModuleBase(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void initModule(Element element) {
        readAttributes(element);
        applyIdAndClassToView(getView());

        getBodyGenerator().generateBody(element, panel);
    }

    @Override
    public Widget getView() {
        return panel;
    }

    protected Panel getContainer() {
        return panel;
    }

    protected void setContainerStyleName(String styleName) {
        panel.setStyleName(styleName);
    }

}
