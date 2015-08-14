package eu.ydp.empiria.player.client.module.menu.view;

import com.google.gwt.i18n.client.Constants;
import com.google.inject.Singleton;

@Singleton
public interface MenuStyleNameConstants extends Constants {

    @DefaultStringValue("qp-menu")
    String QP_MENU();

    @DefaultStringValue("qp-menu-hidden")
    String QP_MENU_HIDDEN();

    @DefaultStringValue("qp-menu-button")
    String QP_MENU_BUTTON();

    @DefaultStringValue("qp-menu-table")
    String QP_MENU_TABLE();
}
