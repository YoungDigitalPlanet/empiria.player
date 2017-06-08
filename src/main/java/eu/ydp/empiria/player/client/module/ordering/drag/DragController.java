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

package eu.ydp.empiria.player.client.module.ordering.drag;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DragController {

    @Inject
    @ModuleScoped
    private OrderInteractionView interactionView;
    @Inject
    private Sortable sortable;
    @Inject
    private SortCallback callback;

    public void init(final OrderInteractionOrientation orientation) {
        String id = getIdSelector();
        sortable.init(id, orientation, callback);
    }

    public void enableDrag() {
        String id = getIdSelector();
        sortable.enable(id);
    }

    public void disableDrag() {
        String id = getIdSelector();
        sortable.disable(id);
    }

    private String getIdSelector() {
        return "." + interactionView.getMainPanelUniqueCssClass();
    }
}
