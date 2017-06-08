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

package eu.ydp.empiria.player.client.module.external.common.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;

public class ExternalFrame<T extends ExternalApi> extends Composite {

    private final Frame frame = new Frame();

    public ExternalFrame() {
        initWidget(frame);
    }

    public void init(final ExternalEmpiriaApi api, final ExternalFrameLoadHandler<T> onLoadHandler) {
        frame.addLoadHandler(new LoadHandler() {
            @Override
            public void onLoad(LoadEvent event) {
                T obj = init(frame.getElement(), api);
                onLoadHandler.onExternalModuleLoaded(obj);
            }
        });
    }

    public void setUrl(String url) {
        frame.setUrl(url);
    }

    private native T init(Element frame, ExternalEmpiriaApi api)/*-{
        return frame.contentWindow.init(api);
    }-*/;
}