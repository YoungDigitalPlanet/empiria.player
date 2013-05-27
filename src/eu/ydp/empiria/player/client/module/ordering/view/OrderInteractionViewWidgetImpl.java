package eu.ydp.empiria.player.client.module.ordering.view;

import java.util.List;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class OrderInteractionViewWidgetImpl extends Composite implements OrderInteractionViewWidget {

	private static OrderInteractionViewWidgetUiBinder uiBinder = GWT.create(OrderInteractionViewWidgetUiBinder.class);

	interface OrderInteractionViewWidgetUiBinder extends UiBinder<Widget, OrderInteractionViewWidgetImpl> {
	}

	@UiField
	protected FlowPanel mainPanel;

	@Inject
	private StyleNameConstants styleNameConstants;

	@PostConstruct
	public void postConstruct() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setOrientation(OrderInteractionOrientation orientation){
		mainPanel.removeStyleName(styleNameConstants.QP_ORDERED_VARTICAL());
		mainPanel.removeStyleName(styleNameConstants.QP_ORDERED_HORIZONTAL());

		if(orientation == OrderInteractionOrientation.VERTICAL){
			mainPanel.addStyleName(styleNameConstants.QP_ORDERED_VARTICAL());
		}else{
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
	public void add(IsWidget widget){
		mainPanel.add(widget);
	}

}
