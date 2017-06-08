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

package eu.ydp.empiria.player.client.module.draggap.math;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.module.draggap.math.structure.MathDragGapBean;
import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapBasePresenter;
import eu.ydp.empiria.player.client.module.draggap.standard.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class MathDragGapPresenter extends DragGapBasePresenter<MathDragGapBean> {

    @Inject
    public MathDragGapPresenter(@ModuleScoped DragGapView view, @ModuleScoped DragGapModuleModel model, @PageScoped AnswerEvaluationSupplier answerEvaluationSupplier, @ModuleScoped SourceListManagerAdapter sourceListManagerAdapter) {
        super(view, model, answerEvaluationSupplier, sourceListManagerAdapter);
    }
}
