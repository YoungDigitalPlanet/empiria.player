package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.view.ViewStyleNameConstants;

import java.util.Collection;

public class MultiPageControllerStyleManager {

    @Inject
    private ViewStyleNameConstants styleNames;

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
