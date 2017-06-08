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

package eu.ydp.empiria.player.client.view.page;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PageContentView extends Composite {

    private static PageContentUiBinder uiBinder = GWT.create(PageContentUiBinder.class);

    @UiTemplate("PageContentView.ui.xml")
    interface PageContentUiBinder extends UiBinder<Widget, PageContentView> {
    }

    @UiField
    Panel itemsPanel;
    @UiField
    Panel titlePanel;

    @Inject
    public PageContentView(@Assisted Panel parentPanel) {
        uiBinder.createAndBindUi(this);
        parentPanel.add(itemsPanel);
    }

    public Panel getItemsPanel() {
        return itemsPanel;
    }

    public Panel getTitlePanel() {
        return titlePanel;
    }
}
