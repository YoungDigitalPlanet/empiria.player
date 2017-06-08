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

package eu.ydp.empiria.player.client.module.external.presentation.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalFrame;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalView;

public class ExternalPresentationViewImpl extends Composite implements ExternalView<ExternalApi, ExternalEmpiriaApi> {

    private static ExternalInteractionViewUiBinder uiBinder = GWT.create(ExternalInteractionViewUiBinder.class);

    @UiTemplate("ExternalPresentationView.ui.xml")
    interface ExternalInteractionViewUiBinder extends UiBinder<Widget, ExternalPresentationViewImpl> {
    }

    @UiField
    ExternalFrame<ExternalApi> frame;

    @Inject
    public ExternalPresentationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void init(ExternalEmpiriaApi api, ExternalFrameLoadHandler<ExternalApi> onLoadHandler, String url) {
        frame.init(api, onLoadHandler);
        frame.setUrl(url);
    }

    @Override
    public void setIframeUrl(String url) {
        frame.setUrl(url);
    }
}
