package eu.ydp.empiria.player.client.module.ordering.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ordering.OrderingStyleNameConstants;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

import javax.annotation.PostConstruct;
import java.util.List;

public class OrderInteractionViewWidgetImpl extends Composite implements OrderInteractionViewWidget {

    private static OrderInteractionViewWidgetUiBinder uiBinder = GWT.create(OrderInteractionViewWidgetUiBinder.class);

    interface OrderInteractionViewWidgetUiBinder extends UiBinder<Widget, OrderInteractionViewWidgetImpl> {
    }

    @UiField
    protected FlowPanel mainPanel;
    private final String mainPanelUniqueCssClass;
    @Inject
    private OrderingStyleNameConstants styleNameConstants;

    @Inject
    public OrderInteractionViewWidgetImpl(OrderInteractionViewUniqueCssProvider interactionViewUniqueCssClassSuffixGenerator) {
        mainPanelUniqueCssClass = interactionViewUniqueCssClassSuffixGenerator.getNext();
    }

    @PostConstruct
    public void postConstruct() {
        initWidget(uiBinder.createAndBindUi(this));
        mainPanel.addStyleName(mainPanelUniqueCssClass);

    }

    @Override
    public void setOrientation(OrderInteractionOrientation orientation) {
        mainPanel.removeStyleName(styleNameConstants.QP_ORDERED_VARTICAL());
        mainPanel.removeStyleName(styleNameConstants.QP_ORDERED_HORIZONTAL());

        if (orientation == OrderInteractionOrientation.VERTICAL) {
            mainPanel.addStyleName(styleNameConstants.QP_ORDERED_VARTICAL());
        } else {
            mainPanel.addStyleName(styleNameConstants.QP_ORDERED_HORIZONTAL());
        }
    }

    @Override
    public <W extends IsWidget> void putItemsOnView(List<W> itemsInOrder) {
        mainPanel.clear();
        for (IsWidget viewItem : itemsInOrder) {
            mainPanel.add(viewItem);
        }
    }

    @Override
    public void add(IsWidget widget) {
        mainPanel.add(widget);
    }

    @Override
    public String getMainPanelUniqueCssClass() {
        return mainPanelUniqueCssClass;
    }
}
