package eu.ydp.empiria.player.client.module.test.reset.view;

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

public class TestResetButtonViewImpl extends Composite implements TestResetButtonView {

    private static TestResetButtonViewIUiBinder uiBinder = GWT.create(TestResetButtonViewIUiBinder.class);

    @UiTemplate("TestResetButtonView.ui.xml")
    interface TestResetButtonViewIUiBinder extends UiBinder<Widget, TestResetButtonViewImpl> {
    }

    public TestResetButtonViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    CustomPushButton testResetButton;

    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @Inject
    private StyleNameConstants styleNameConstants;

    @Override
    public void addHandler(Command command) {
        userInteractionHandlerFactory.createUserClickHandler(command)
                .apply(this);
    }

    @Override
    public void lock() {
        addStyleName(styleNameConstants.QP_TEST_RESET_DISABLED());
    }

    @Override
    public void unlock() {
        removeStyleName(styleNameConstants.QP_TEST_RESET_DISABLED());
    }

    @Override
    public void enablePreviewMode() {
        addStyleName(styleNameConstants.QP_MODULE_MODE_PREVIEW());
    }
}
