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
import eu.ydp.empiria.player.client.module.draggap.DragGapBaseModule;
import eu.ydp.empiria.player.client.module.draggap.math.structure.MathDragGapBean;
import eu.ydp.empiria.player.client.module.draggap.math.structure.MathDragGapModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.mathjax.interaction.InteractionMathJaxModule;
import eu.ydp.empiria.player.client.module.mathjax.interaction.MathJaxGapContainer;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public class MathDragGapModule extends DragGapBaseModule<MathDragGapBean, MathDragGapModuleJAXBParserFactory> {
    @Inject
    private MathJaxGapContainer mathJaxGapContainer;


    @Override
    protected void initalizeModule() {
        super.initalizeModule();
        mathJaxGapContainer.addMathGap(getPresenter().asWidget(), getIdentifier());
    }

    @Override
    public void setSize(HasDimensions size) {
        if (!size.equals(getSize())) {
            super.setSize(size);
            InteractionMathJaxModule parentModule = (InteractionMathJaxModule) getParentModule();
            parentModule.markToRerender();
        }
    }
}
