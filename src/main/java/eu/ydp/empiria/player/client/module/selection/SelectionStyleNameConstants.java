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

package eu.ydp.empiria.player.client.module.selection;

import com.google.gwt.i18n.client.Constants;

public interface SelectionStyleNameConstants extends Constants {

    @DefaultStringValue("qp-selection-choice")
    String QP_SELECTION_CHOICE();

    @DefaultStringValue("qp-selection-item")
    String QP_SELECTION_ITEM();

    @DefaultStringValue("qp-selection-item-label")
    String QP_SELECTION_ITEM_LABEL();

    @DefaultStringValue("qp-selection-module")
    String QP_SELECTION_MODULE();

    @DefaultStringValue("qp-selection-table")
    String QP_SELECTION_TABLE();

    @DefaultStringValue("selection")
    String SELECTION();

    @DefaultStringValue("selection-multi")
    String SELECTION_MULTI();
}
