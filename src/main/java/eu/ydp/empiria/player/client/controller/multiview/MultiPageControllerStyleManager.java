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

package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.PageStyleNameConstants;

import java.util.Collection;

public class MultiPageControllerStyleManager {

    @Inject
    private PageStyleNameConstants styleNames;

    public void clearPagesStyles(Collection<FlowPanel> keyPanels) {
        for (FlowPanel flowPanel : keyPanels) {
            flowPanel.removeStyleName(styleNames.QP_PAGE_UNSELECTED());
            flowPanel.removeStyleName(styleNames.QP_PAGE_SELECTED());
            flowPanel.removeStyleName(styleNames.QP_PAGE_PREV());
            flowPanel.removeStyleName(styleNames.QP_PAGE_NEXT());
        }
    }

    public void setPageStyles(FlowPanel panel, boolean isChangeToNextPage) {
        panel.addStyleName(styleNames.QP_PAGE_UNSELECTED());
        String pageDirectionChangeStyle = findPageDirectionChangeStyle(isChangeToNextPage);
        panel.addStyleName(pageDirectionChangeStyle);
    }

    private String findPageDirectionChangeStyle(boolean isChangeToNextPage) {
        if (isChangeToNextPage) {
            return styleNames.QP_PAGE_NEXT();
        } else {
            return styleNames.QP_PAGE_PREV();
        }
    }
}
