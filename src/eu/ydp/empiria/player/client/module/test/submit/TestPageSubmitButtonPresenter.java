package eu.ydp.empiria.player.client.module.test.submit;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitButtonView;
import eu.ydp.gwtutil.client.event.factory.Command;

public class TestPageSubmitButtonPresenter {

	private final TestPageSubmitButtonView testPageSubmitButtonView;
	private final FlowRequestInvoker flowRequestInvoker;
	private boolean locked;

	@Inject
	public TestPageSubmitButtonPresenter(TestPageSubmitButtonView testPageSubmitButtonView, FlowManager flowManager) {
		this.testPageSubmitButtonView = testPageSubmitButtonView;
		this.flowRequestInvoker = flowManager.getFlowRequestInvoker();
		addHandlerToButton();
	}

	private void addHandlerToButton() {
		testPageSubmitButtonView.addHandler(new Command() {

			@Override
			public void execute(NativeEvent event) {
				if (!locked) {
					nextPage();
				}
			}
		});
	}

	private void nextPage() {
		flowRequestInvoker.invokeRequest(new FlowRequest.NavigateNextItem());
	}

	public Widget getView() {
		return testPageSubmitButtonView.asWidget();
	}

	public void lock() {
		locked = true;
		testPageSubmitButtonView.lock();
	}

	public void unlock() {
		locked = false;
		testPageSubmitButtonView.unlock();
	}

	public void enableTestMode() {
		unlock();
		testPageSubmitButtonView.enableTestMode();
	}

	public void enableTestSubmittedMode() {
		lock();
		testPageSubmitButtonView.enableTestSubmittedMode();
	}

	public void enablePreviewMode() {
		lock();
		testPageSubmitButtonView.enablePreviewMode();
	}
}
