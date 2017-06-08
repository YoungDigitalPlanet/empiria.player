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

package eu.ydp.empiria.player.client.module.menu.view;

import com.google.gwt.i18n.client.Constants;
import com.google.inject.Singleton;

@Singleton
public interface MenuStyleNameConstants extends Constants {

    @DefaultStringValue("qp-menu")
    String QP_MENU();

    @DefaultStringValue("qp-menu-hidden")
    String QP_MENU_HIDDEN();

    @DefaultStringValue("qp-menu-button")
    String QP_MENU_BUTTON();

    @DefaultStringValue("qp-menu-table")
    String QP_MENU_TABLE();

    @DefaultStringValue("qp-menu-table-current-row")
    String QP_MENU_TABLE_CURRENT_ROW();
}
