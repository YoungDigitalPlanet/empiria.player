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

package eu.ydp.empiria.player.client.module.object.impl.flash;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.gwtutil.client.PathUtil;

public abstract class FlashLocalMediaImpl extends Composite {

    protected String elementId;
    protected String src;
    protected FlowPanel panelMain;
    protected FlowPanel panelContent;

    public FlashLocalMediaImpl(String name) {
        elementId = Document.get().createUniqueId();
        panelMain = new FlowPanel();
        panelMain.setStyleName("qp-" + name + "-flash-local");
        panelContent = new FlowPanel();
        panelContent.getElement().setId(elementId);
        panelMain.add(panelContent);

        initWidget(panelMain);
    }

    @Override
    public void onLoad() {
        String swfSrc = getSwfSrc();
        String installSrc = GWT.getModuleBaseURL() + "swfobject/expressInstall.swf";
        String srcNormalized = PathUtil.normalizePath(src);
        loadFlvPlayerThroughSwfobject(elementId, swfSrc, installSrc, srcNormalized, getWidth(), getHeight());
    }

    protected abstract void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String videoSrc, int width, int height);

    protected abstract String getSwfSrc();

    protected abstract int getWidth();

    protected abstract int getHeight();

    public void setSrc(String src) {
        this.src = src;
    }

}
