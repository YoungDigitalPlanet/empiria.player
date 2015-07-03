package eu.ydp.empiria.player.client.module.choice.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ChoiceModuleViewImpl implements ChoiceModuleView {

    private static ChoiceModuleViewUiBinder uiBinder = GWT.create(ChoiceModuleViewUiBinder.class);

    @UiTemplate("ChoiceModuleView.ui.xml")
    interface ChoiceModuleViewUiBinder extends UiBinder<Widget, ChoiceModuleViewImpl> {
    }

    @UiField
    Panel mainPanel;

    @UiField
    Widget promptWidget;

    @UiField
    Panel choicesPanel;

    private final Widget widget;

    public ChoiceModuleViewImpl() {
        widget = uiBinder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void clear() {
        choicesPanel.clear();
    }

    @Override
    public void addChoice(Widget widget) {
        choicesPanel.add(widget);
    }

    @Override
    public Element getPrompt() {
        return promptWidget.getElement();
    }
}
