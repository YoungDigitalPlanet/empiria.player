package eu.ydp.empiria.player.client.module.pageinpage;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.PageStyleNameConstants;

public class PageInPageModule extends SimpleModuleBase {

    private Panel pagePanel;

    @Inject
    private PageStyleNameConstants styleNames;
    @Inject
    private MultiPageController controller;

    @Override
    public void initModule(Element element) {
    }

    @Override
    public Widget getView() {
        if (pagePanel == null) {
            pagePanel = controller.getView();
            pagePanel.setStyleName(styleNames.QP_PAGE_IN_PAGE());
        }
        return pagePanel;
    }
}
