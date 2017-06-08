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

package eu.ydp.empiria.player.client.module.mathtext;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Font;
import eu.ydp.empiria.player.client.module.core.base.InlineModuleBase;

public class MathTextModule extends InlineModuleBase {

    private static final byte BASELINE_TO_VERTICAL_ALIGN_FACTOR = -1;
    protected Panel mainPanel;

    @Inject
    private MathTextFontInitializer mathTextFontInitializer;

    @Override
    public void initModule(Element element) {
        createMainPanel();
        MathPlayerManager mpm = new MathPlayerManager();
        initFont(element, mpm);

        boolean temporaryAttached = temporaryAttach();
        mpm.createMath(element.getChildNodes().toString(), mainPanel);
        temporaryDetach(temporaryAttached);

        updateVerticalAlign(mpm);
    }

    private void createMainPanel() {
        mainPanel = new FlowPanel();
        mainPanel.setStyleName("qp-mathtext");
    }

    private void initFont(Element element, MathPlayerManager mpm) {
        Font font = mathTextFontInitializer.initialize(this, getModuleSocket(), element);
        mpm.setFont(font);
    }

    private void updateVerticalAlign(MathPlayerManager mpm) {
        int verticalAlignPx = BASELINE_TO_VERTICAL_ALIGN_FACTOR * mpm.getBaseline();
        mainPanel.getElement().getStyle().setVerticalAlign(verticalAlignPx, Unit.PX);
    }

    private boolean temporaryAttach() {
        if (!mainPanel.isAttached()) {
            RootPanel.get().add(mainPanel);
            return true;
        }
        return false;
    }

    private void temporaryDetach(boolean temporaryAttached) {
        if (temporaryAttached) {
            mainPanel.removeFromParent();
        }
    }

    @Override
    public Widget getView() {
        return mainPanel;
    }
}
