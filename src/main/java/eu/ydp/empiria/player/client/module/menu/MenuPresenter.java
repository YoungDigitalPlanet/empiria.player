package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.menu.view.MenuStyleNameConstants;
import eu.ydp.empiria.player.client.module.menu.view.MenuView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class MenuPresenter {

    private MenuView view;
    private MenuStyleNameConstants styleNameConstants;
    private boolean isHidden;

    @Inject
    public MenuPresenter(MenuView view, MenuStyleNameConstants styleNameConstants, UserInteractionHandlerFactory userInteractionHandlerFactory) {
        this.view = view;
        this.styleNameConstants = styleNameConstants;
        isHidden = true;
        EventHandlerProxy userClickHandler = userInteractionHandlerFactory.createUserClickHandler(createClickCommand());
        view.addClickHandler(userClickHandler);
    }

    private Command createClickCommand() {
        return new Command() {
            @Override
            public void execute(NativeEvent event) {
                if (isHidden) {
                    show();
                } else {
                    hide();
                }
            }
        };
    }

    public void setTable(FlexTable table) {
        view.setTable(table);
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
}
