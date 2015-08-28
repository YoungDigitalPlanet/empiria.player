package eu.ydp.empiria.player.client.module.accordion.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;

public class AccordionViewImpl extends Composite implements AccordionView {
    private static AccordionUiBinder uiBinder = GWT.create(AccordionUiBinder.class);

    @UiTemplate("AccordionView.ui.xml")
    interface AccordionUiBinder extends UiBinder<Widget, AccordionViewImpl> {
    }

    @UiField
    FlowPanel container;

    public AccordionViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void addSection(AccordionSectionPresenter view) {
        container.add(view.asWidget());
    }
}
