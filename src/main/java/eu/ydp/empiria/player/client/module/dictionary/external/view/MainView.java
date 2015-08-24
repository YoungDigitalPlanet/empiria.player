package eu.ydp.empiria.player.client.module.dictionary.external.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MainView extends SimplePanel {

    private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);

    interface MainViewUiBinder extends UiBinder<Widget, MainView> {
    }

    @Inject
    @UiField(provided = true)
    MenuView menuView;
    @UiField
    FlowPanel containerView;
    @UiField
    PushButton exitButton;

    public void init() {
        setWidget(uiBinder.createAndBindUi(this));
        menuView.init();
    }

    @UiHandler("exitButton")
    public void onClick(ClickEvent event) {
        exitJs();
    }

    public void hideMenu() {
        menuView.hide();
    }

    public void showMenu() {
        menuView.show();
    }

    public void addViewToContainerView(IsWidget widget) {
        this.containerView.add(widget);
    }

    private native void exitJs()/*-{
        if (typeof $wnd.dictionaryExit == 'function') {
            $wnd.dictionaryExit();
        }
    }-*/;
}
