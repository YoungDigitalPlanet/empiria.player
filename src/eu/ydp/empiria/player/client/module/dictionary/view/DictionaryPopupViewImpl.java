package eu.ydp.empiria.player.client.module.dictionary.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class DictionaryPopupViewImpl extends Composite implements DictionaryPopupView {

	private static DictionaryPopupViewIUiBinder uiBinder = GWT.create(DictionaryPopupViewIUiBinder.class);

	@UiTemplate("DictionaryPopupView.ui.xml")
	interface DictionaryPopupViewIUiBinder extends UiBinder<Widget, DictionaryPopupViewImpl> {
	}

	public DictionaryPopupViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	FlowPanel container;

	@UiField
	CustomPushButton closeButton;

	@Override
	public void addClickHandler(ClickHandler clickHandler) {
		closeButton.addClickHandler(clickHandler);
	}
}
