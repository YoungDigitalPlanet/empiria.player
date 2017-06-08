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

package eu.ydp.empiria.player.client.view.item;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ItemViewCarrier {

    private Widget titleView;
    private Widget contentView;
    private Widget scoreView;
    private Panel errorView;

    public ItemViewCarrier(Widget title, Widget _contentView, Widget _scoreView) {
        titleView = title;
        contentView = _contentView;
        scoreView = _scoreView;
        errorView = null;
    }

    public ItemViewCarrier(Widget _contentView) {
        contentView = _contentView;
    }

    public ItemViewCarrier(String err) {
        errorView = new FlowPanel();
        errorView.setStyleName("qp-item-error");

        Label errorLabel = new Label(err);
        errorLabel.setStyleName("qp-item-error-text");
        errorView.add(errorLabel);
    }

    public boolean isError() {
        return errorView != null;
    }

    public Widget getErrorView() {
        return errorView;
    }

    public Widget getTitleView() {
        return titleView;
    }

    public Widget getContentView() {
        return contentView;
    }

    public Widget getScoreView() {
        return scoreView;
    }
}
