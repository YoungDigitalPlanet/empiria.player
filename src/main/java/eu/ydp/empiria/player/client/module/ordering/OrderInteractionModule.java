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

package eu.ydp.empiria.player.client.module.ordering;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;
import eu.ydp.empiria.player.client.module.core.base.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionStructure;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class OrderInteractionModule extends AbstractInteractionModule<OrderInteractionModuleModel, OrderInteractionBean> {

    @Inject
    @ModuleScoped
    private OrderInteractionPresenter presenter;

    @Inject
    private OrderInteractionStructure orderInteractionStructure;

    @Inject
    @ModuleScoped
    private OrderInteractionModuleModel moduleModel;

    @Inject
    @ModuleScoped
    private DragController dragController;

    @Override
    public Widget getView() {
        return presenter.asWidget();
    }

    @Override
    protected void initalizeModule() {
        moduleModel.initialize(this);
    }

    @Override
    protected OrderInteractionModuleModel getResponseModel() {
        return moduleModel;
    }

    @Override
    protected ActivityPresenter<OrderInteractionModuleModel, OrderInteractionBean> getPresenter() {
        return presenter;
    }

    @Override
    protected AbstractModuleStructure<OrderInteractionBean, ? extends JAXBParserFactory<OrderInteractionBean>> getStructure() {
        return orderInteractionStructure;
    }

    @Override
    public void onBodyLoad() {
        super.onBodyLoad();
        dragController.init(presenter.getOrientation());
        presenter.setLocked(locked);
    }
}
