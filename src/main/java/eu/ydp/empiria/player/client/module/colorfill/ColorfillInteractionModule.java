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

package eu.ydp.empiria.player.client.module.colorfill;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;
import eu.ydp.empiria.player.client.module.core.base.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionStructure;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ColorfillInteractionModule extends
        AbstractInteractionModule<ColorfillInteractionModuleModel, ColorfillInteractionBean> {

    @Inject
    private ColorfillInteractionPresenter presenter;
    @Inject
    @ModuleScoped
    private ColorfillInteractionModuleModel moduleModel;

    @Inject
    @ModuleScoped
    private ColorfillInteractionStructure colorfillInteractionStructure;

    @Override
    protected ActivityPresenter<ColorfillInteractionModuleModel, ColorfillInteractionBean> getPresenter() {
        return presenter;
    }

    @Override
    protected void initalizeModule() {
        moduleModel.initialize(this);
    }

    @Override
    protected ColorfillInteractionModuleModel getResponseModel() {
        return moduleModel;
    }

    @Override
    protected AbstractModuleStructure<ColorfillInteractionBean, ? extends JAXBParserFactory<ColorfillInteractionBean>> getStructure() {
        return colorfillInteractionStructure;
    }

}
