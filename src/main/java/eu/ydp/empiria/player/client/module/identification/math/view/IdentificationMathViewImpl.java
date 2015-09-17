package eu.ydp.empiria.player.client.module.identification.math.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

public class IdentificationMathViewImpl extends Composite implements IdentificationMathView {

    private static SelectableChoiceUiBinder uiBinder = GWT.create(SelectableChoiceUiBinder.class);

    @UiTemplate("IdentificationMathView.ui.xml")
    interface SelectableChoiceUiBinder extends UiBinder<Widget, IdentificationMathViewImpl> {
    }

    @UiField
    FlowPanel panel;

    public IdentificationMathViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void add(Widget w) {
        panel.add(w);
    }

    @Override
    public void clear() {
        panel.clear();
    }
    @Override
    public Iterator<Widget> iterator() {
        return panel.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return panel.remove(w);
    }
}
