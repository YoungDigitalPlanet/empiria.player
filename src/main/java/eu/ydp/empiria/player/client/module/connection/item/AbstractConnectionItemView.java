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

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionStyleNameConstants;

public abstract class AbstractConnectionItemView extends Composite {

    protected ConnectionStyleNameConstants styleNames;

    @UiField
    protected FlowPanel selection;

    @UiField
    protected FlowPanel item;

    private final PairChoiceBean bean;
    private final InlineBodyGeneratorSocket bodyGenerator;

    @Inject
    public AbstractConnectionItemView(@Assisted InlineBodyGeneratorSocket bodyGenerator, @Assisted PairChoiceBean bean, ConnectionStyleNameConstants styleNames) {
        this.bean = bean;
        this.styleNames = styleNames;
        this.bodyGenerator = bodyGenerator;
    }

    protected void buildView() {
        item.add(bodyGenerator.generateInlineBody(bean.getXmlContent().getValue(), true));
        item.getElement().getStyle().setZIndex(1);
    }

    public void reset() {
        removeStyleName(styleNames.QP_CONNECTION_ITEM_SELECTED());
        removeStyleName(styleNames.QP_CONNECTION_ITEM_CONECTED());
        removeStyleName(styleNames.QP_CONNECTION_ITEM_CONECTED_DISABLED());
    }

    public FlowPanel getItemView() {
        return item;
    }

    public FlowPanel getSelectionElement() {
        return selection;
    }

    protected void addRemoveStyle(boolean add, String style) {
        if (add) {
            addStyleName(style);
        } else {
            removeStyleName(style);
        }
    }

    protected void addRemoveStyle(boolean add, MultiplePairModuleConnectType connectType) {
        switch (connectType) {
            case NONE:
                addRemoveStyle(add, styleNames.QP_CONNECTION_ITEM_CONECTED_DISABLED());
                addRemoveStyle(false, styleNames.QP_CONNECTION_ITEM_CONECTED());
                break;
            case CORRECT:
            case NORMAL:
            case WRONG:
            default:
                addRemoveStyle(add, styleNames.QP_CONNECTION_ITEM_CONECTED());
                addRemoveStyle(false, styleNames.QP_CONNECTION_ITEM_CONECTED_DISABLED());
                break;
        }
    }

    public void setSelected(boolean connected, MultiplePairModuleConnectType connectType) {
        addRemoveStyle(connected, connectType);
    }

}
