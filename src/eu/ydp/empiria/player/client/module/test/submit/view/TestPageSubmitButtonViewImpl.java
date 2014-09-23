package eu.ydp.empiria.player.client.module.test.submit.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class TestPageSubmitButtonViewImpl extends Composite implements TestPageSubmitButtonView {

	private static TestPageSubmitViewIUiBinder uiBinder = GWT.create(TestPageSubmitViewIUiBinder.class);

	@UiField
	CustomPushButton showSubmitButton;
	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	@Inject
	private StyleNameConstants styleNameConstants;

	@UiTemplate("TestPageSubmitButtonView.ui.xml")
	interface TestPageSubmitViewIUiBinder extends UiBinder<Widget, TestPageSubmitButtonViewImpl> {
	}

	public TestPageSubmitButtonViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void addHandler(Command command) {
		userInteractionHandlerFactory.createUserClickHandler(command).apply(showSubmitButton);
	}

	@Override
	public void lock() {
		addStyleName(styleNameConstants.QP_TEST_SUBMIT_DISABLED());
	}

	@Override
	public void unlock() {
		removeStyleName(styleNameConstants.QP_TEST_SUBMIT_DISABLED());
	}

	@Override
	public void enableTestMode() {
		removeStyleName(styleNameConstants.QP_TEST_SUBMIT_SUBMITTED_MODE());
	}

	@Override
	public void enableTestSubmittedMode() {
		addStyleName(styleNameConstants.QP_TEST_SUBMIT_SUBMITTED_MODE());
	}

	@Override
	public void enablePreviewMode() {
		addStyleName(styleNameConstants.QP_TEST_SUBMIT_PREVIEW_MODE());
	}
}
