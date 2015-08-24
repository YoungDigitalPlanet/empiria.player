package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.report.table.ReportTable;
import eu.ydp.empiria.player.client.module.menu.view.MenuStyleNameConstants;
import eu.ydp.empiria.player.client.module.menu.view.MenuView;

public class MenuPresenter {

    private MenuView view;
    private MenuStyleNameConstants styleNameConstants;
    private boolean isHidden;
    private ReportTable table;

    @Inject
    public MenuPresenter(MenuView view, MenuStyleNameConstants styleNameConstants) {
        this.view = view;
        this.styleNameConstants = styleNameConstants;
        isHidden = true;
        view.addClickHandler(createClickCommand());
    }

    private ClickHandler createClickCommand() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isHidden) {
                    show();
                } else {
                    hide();
                }
            }
        };
    }

    public void setReportTable(ReportTable table) {
        this.table = table;
        view.setTable(table.getFlexTable());
    }

    public Widget getView() {
        return view.asWidget();
    }

    public void hide() {
        view.addStyleName(styleNameConstants.QP_MENU_HIDDEN());
        isHidden = true;
    }

    private void show() {
        view.removeStyleName(styleNameConstants.QP_MENU_HIDDEN());
        isHidden = false;
    }

    public void unmarkPage(int page) {
        table.removeRowStyleName(page, styleNameConstants.QP_MENU_TABLE_CURRENT_ROW());
    }

    public void markPage(int page) {
        table.addRowStyleName(page, styleNameConstants.QP_MENU_TABLE_CURRENT_ROW());
    }
}
