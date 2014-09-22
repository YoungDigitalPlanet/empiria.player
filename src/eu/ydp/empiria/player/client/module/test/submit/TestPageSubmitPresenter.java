package eu.ydp.empiria.player.client.module.test.submit;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitView;
import eu.ydp.gwtutil.client.event.factory.Command;

public class TestPageSubmitPresenter {

	private final TestPageSubmitView testPageSubmitButtonView;
	private final FlowRequestInvoker flowRequestInvoker;
	private boolean enabled = true;

	@Inject
	public TestPageSubmitPresenter(TestPageSubmitView testPageSubmitButtonView, FlowManager flowManager) {
		this.testPageSubmitButtonView = testPageSubmitButtonView;
		this.flowRequestInvoker = flowManager.getFlowRequestInvoker();
		addHandlerToButton();
	}

	private void addHandlerToButton() {
		testPageSubmitButtonView.addHandler(new Command() {

			@Override
			public void execute(NativeEvent event) {
				if (enabled) {
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
		enabled = false;
		testPageSubmitButtonView.lock();
	}

	public void unlock() {
		enabled = true;
		testPageSubmitButtonView.unlock();
	}

	public void enableTestSubmittedMode() {
		lock();
		testPageSubmitButtonView.enableTestSubmittedMode();
	}

	public void enablePreviewMode() {
		lock();
		testPageSubmitButtonView.enablePreviewMode();
	}

	public void disableTestSubmittedMode() {
		unlock();
		testPageSubmitButtonView.disableTestSubmittedMode();
	}
}
