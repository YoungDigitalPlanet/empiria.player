package eu.ydp.empiria.player.client.module.test.submit;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TestPageSubmitPresenter {

	private final TestPageSubmitView testPageSubmitButtonView;
	private final FlowRequestInvoker flowRequestInvoker;
	private boolean disabled = false;

	@Inject
	public TestPageSubmitPresenter(@ModuleScoped TestPageSubmitView testPageSubmitButtonView, FlowManager flowManager) {
		this.testPageSubmitButtonView = testPageSubmitButtonView;
		this.flowRequestInvoker = flowManager.getFlowRequestInvoker();
	}

	public void bindUi() {
		testPageSubmitButtonView.addHandler(new Command() {

			@Override
			public void execute(NativeEvent event) {
				if (!disabled) {
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
		disabled = true;
		testPageSubmitButtonView.lock();
	}

	public void unlock() {
		disabled = false;
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
