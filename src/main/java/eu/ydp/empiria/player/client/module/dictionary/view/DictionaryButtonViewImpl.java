package eu.ydp.empiria.player.client.module.dictionary.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class DictionaryButtonViewImpl extends Composite implements DictionaryButtonView {

	private static DictionaryButtonViewIUiBinder uiBinder = GWT.create(DictionaryButtonViewIUiBinder.class);

	@UiTemplate("DictionaryButtonView.ui.xml")
	interface DictionaryButtonViewIUiBinder extends UiBinder<Widget, DictionaryButtonViewImpl> {
	}

	public DictionaryButtonViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	CustomPushButton showPopupButton;

	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;

	@Override
	public void addHandler(Command command) {
		userInteractionHandlerFactory.createUserClickHandler(command).apply(showPopupButton);
	}
}
