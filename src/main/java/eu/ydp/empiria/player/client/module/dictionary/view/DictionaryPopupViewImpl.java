package eu.ydp.empiria.player.client.module.dictionary.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

import javax.inject.Inject;

public class DictionaryPopupViewImpl extends Composite implements DictionaryPopupView {

    private static DictionaryPopupViewIUiBinder uiBinder = GWT.create(DictionaryPopupViewIUiBinder.class);

    @UiTemplate("DictionaryPopupView.ui.xml")
    interface DictionaryPopupViewIUiBinder extends UiBinder<Widget, DictionaryPopupViewImpl> {
    }

    @Inject
    private RootPanelDelegate rootPanelDelegate;

    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;

    public DictionaryPopupViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    FlowPanel container;

    @UiField
    CustomPushButton closeButton;

    @Override
    public void addHandler(Command command) {
        userInteractionHandlerFactory.createUserClickHandler(command).apply(closeButton);
    }

    @Override
    public void show() {
        RootPanel rootPanel = rootPanelDelegate.getRootPanel();
        rootPanel.add(this);
    }

    @Override
    public void hide() {
        RootPanel rootPanel = rootPanelDelegate.getRootPanel();
        rootPanel.remove(this);
    }

    @Override
    public Panel getContainer() {
        return container;
    }
}
