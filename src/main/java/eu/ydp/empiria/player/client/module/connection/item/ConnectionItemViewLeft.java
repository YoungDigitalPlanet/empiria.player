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

package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionStyleNameConstants;

public class ConnectionItemViewLeft extends AbstractConnectionItemView {
    private static ConnectionItemViewUiBinder uiBinder = GWT.create(ConnectionItemViewUiBinder.class);

    interface ConnectionItemViewUiBinder extends UiBinder<Widget, ConnectionItemViewLeft> {
    }

    @Inject
    public ConnectionItemViewLeft(@Assisted InlineBodyGeneratorSocket bodyGenerator, @Assisted PairChoiceBean bean, ConnectionStyleNameConstants styleNames) {
        super(bodyGenerator, bean, styleNames);
        initWidget(uiBinder.createAndBindUi(this));
        buildView();
    }
}
