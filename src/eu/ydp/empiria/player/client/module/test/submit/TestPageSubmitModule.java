package eu.ydp.empiria.player.client.module.test.submit;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ILockable;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.workmode.WorkModeClient;

public class TestPageSubmitModule extends SimpleModuleBase implements ILockable, WorkModeClient {

	private final TestPageSubmitPresenter presenter;

	@Inject
	public TestPageSubmitModule(TestPageSubmitPresenter testPageSubmitPresenter) {
		this.presenter = testPageSubmitPresenter;
	}

	@Override
	public Widget getView() {
		return presenter.getView();
	}

	@Override
	public void lock(boolean lock) {
		if (lock) {
			presenter.lock();
		} else {
			presenter.unlock();
		}
	}

	@Override
	public void enableTestMode() {
		presenter.enableTestMode();
	}

	@Override
	public void disableTestMode() {
	}

	@Override
	public void enableTestSubmittedMode() {
		presenter.enableTestSubmittedMode();
	}

	@Override
	public void disableTestSubmittedMode() {
	}

	@Override
	public void enablePreviewMode() {
		presenter.enablePreviewMode();
	}

	@Override
	protected void initModule(Element element) {
	}
}
