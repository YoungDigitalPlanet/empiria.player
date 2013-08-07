package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;

public class ConnectionStyleCheckerHandler implements Handler {

	private final IsWidget view;
	private final ConnectionStyleChecker connectionStyleChecker;
	private boolean wasChecked = false;

	public ConnectionStyleCheckerHandler(IsWidget view, ConnectionStyleChecker connectionStyleChecker) {
		this.view = view;
		this.connectionStyleChecker = connectionStyleChecker;
		checkStylesAndShowError();
	}

	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if(!wasChecked) {
			checkStylesAndShowError();
			wasChecked = true;
		}
	}

	private void checkStylesAndShowError() {
		try {
			checkStyles();
		} catch (CssStyleException ex) {
			showError(ex);
		}
	}

	private void checkStyles() {
		connectionStyleChecker.areStylesCorrectThrowsExceptionWhenNot(view);
	}

	private void showError(CssStyleException exception) {
		view.asWidget().getElement().setInnerText(exception.getMessage());
	}
}
