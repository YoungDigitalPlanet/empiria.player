package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;

public class ConnectionItemView extends Composite {

	private static ConnectionItemViewUiBinder uiBinder = GWT.create(ConnectionItemViewUiBinder.class);

	interface ConnectionItemViewUiBinder extends UiBinder<Widget, ConnectionItemView> {
	}

	protected StyleNameConstants styleNames;
	protected HasTouchHandlers touchRecognition;
	@UiField
	protected FlowPanel selection;

	@UiField
	protected FlowPanel item;

	private final PairChoiceBean bean;
	private final InlineBodyGeneratorSocket bodyGenerator;

	@Inject
	public ConnectionItemView(StyleNameConstants styleNames, @Assisted InlineBodyGeneratorSocket bodyGenerator, @Assisted PairChoiceBean bean) {
		initWidget(uiBinder.createAndBindUi(this));
		this.bean = bean;
		this.styleNames = styleNames;
		this.bodyGenerator = bodyGenerator;
		buildView();
	}

	private void buildView() {
		item.add(bodyGenerator.generateInlineBody(bean.getXmlContent().getValue(),true));
		item.getElement().getStyle().setZIndex(1);
	}

	public void reset() {
		removeStyleName(styleNames.QP_CONNECTION_ITEM_SELECTED());
		removeStyleName(styleNames.QP_CONNECTION_ITEM_CONECTED());
	}

	public void selected() {
		addStyleName(styleNames.QP_CONNECTION_ITEM_SELECTED());
	}

	public FlowPanel getItemView() {
		return item;
	}

	public FlowPanel getSelectionElement() {
		return selection;
	}

	public void setSelected(boolean connected) {
		if (connected) {
			addStyleName(styleNames.QP_CONNECTION_ITEM_CONECTED());
		} else {
			removeStyleName(styleNames.QP_CONNECTION_ITEM_CONECTED());
		}
	}

}
