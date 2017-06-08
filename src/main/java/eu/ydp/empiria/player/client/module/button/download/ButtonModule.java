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

package eu.ydp.empiria.player.client.module.button.download;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.base.ISimpleModule;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.button.download.presenter.ButtonModulePresenter;
import eu.ydp.empiria.player.client.module.button.download.structure.ButtonBean;
import eu.ydp.empiria.player.client.module.button.download.structure.ButtonModuleStructure;
import eu.ydp.gwtutil.client.service.json.IJSONService;

public class ButtonModule extends SimpleModuleBase implements ISimpleModule {

    @Inject
    private ButtonModuleStructure buttonModuleStructure;
    @Inject
    private IJSONService ijsonService;
    @Inject
    private ButtonModulePresenter buttonModulePresenter;

    @Override
    public Widget getView() {
        return buttonModulePresenter.asWidget();
    }

    @Override
    protected void initModule(Element element) {
        buttonModuleStructure.createFromXml(element.toString(), ijsonService.createArray());
        ButtonBean bean = buttonModuleStructure.getBean();
        buttonModulePresenter.setBean(bean);
        buttonModulePresenter.init();
    }

}
