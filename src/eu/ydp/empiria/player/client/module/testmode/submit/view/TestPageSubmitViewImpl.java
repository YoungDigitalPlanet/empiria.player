package eu.ydp.empiria.player.client.module.testmode.submit.view;

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

public class TestPageSubmitViewImpl extends Composite implements TestPageSubmitView {

	private static TestPageSubmitViewIUiBinder uiBinder = GWT.create(TestPageSubmitViewIUiBinder.class);

	@UiField
	CustomPushButton showSubmitButton;
	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	@Inject
	private StyleNameConstants styleNameConstants;

	@UiTemplate("TestPageSubmitView.ui.xml")
	interface TestPageSubmitViewIUiBinder extends UiBinder<Widget, TestPageSubmitViewImpl> {
	}

	public TestPageSubmitViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void addHandler(Command command) {
		userInteractionHandlerFactory.createUserClickHandler(command).apply(showSubmitButton);
	}

	@Override
	public void lock() {
		this.addStyleName(styleNameConstants.QP_TEST_SUBMIT_DISABLED());
	}

	@Override
	public void unlock() {
		this.removeStyleName(styleNameConstants.QP_TEST_SUBMIT_DISABLED());
	}
}
