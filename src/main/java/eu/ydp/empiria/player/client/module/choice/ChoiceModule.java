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

package eu.ydp.empiria.player.client.module.choice;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.module.core.base.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleJAXBParser;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleStructure;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ChoiceModule extends AbstractInteractionModule<ChoiceModuleModel, ChoiceInteractionBean> {

    private ChoiceModuleStructure choiceStructure;

    private ChoiceModuleModel moduleModel;

    private ChoiceModulePresenter presenter;

    @Inject
    public ChoiceModule(ChoiceModuleStructure choiceStructure, @ModuleScoped ChoiceModuleModel moduleModel, @ModuleScoped ChoiceModulePresenter presenter) {
        this.choiceStructure = choiceStructure;
        this.moduleModel = moduleModel;
        this.presenter = presenter;
    }

    @Override
    protected void initalizeModule() {
        choiceStructure.setMulti(isMulti());
        if (isMulti()) {
            getResponse().setCountMode(getCountMode());
        }
        presenter.setInlineBodyGenerator(getModuleSocket().getInlineBodyGeneratorSocket());

        moduleModel.initialize(this);
    }

    private boolean isMulti() {
        return Cardinality.MULTIPLE == getResponse().cardinality;
    }

    @Override
    protected ActivityPresenter<ChoiceModuleModel, ChoiceInteractionBean> getPresenter() {
        return presenter;
    }

    @Override
    protected ChoiceModuleModel getResponseModel() {
        return moduleModel;
    }

    @Override
    protected AbstractModuleStructure<ChoiceInteractionBean, ChoiceModuleJAXBParser> getStructure() {
        return choiceStructure;
    }

    @Override
    public boolean isIgnored() {
        return getStructure().getBean().isIgnored();
    }

    @Override
    public void markAnswers(boolean mark) {
        if (isIgnored()) {
            return;
        }
        super.markAnswers(mark);
    }
}
