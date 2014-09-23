package eu.ydp.empiria.player.client.module.test.submit;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ILockable;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.workmode.WorkModeClient;

public class TestPageSubmitButtonModule extends SimpleModuleBase implements ILockable, WorkModeClient {

	private final TestPageSubmitButtonPresenter presenter;

	@Inject
	public TestPageSubmitButtonModule(TestPageSubmitButtonPresenter presenter) {
		this.presenter = presenter;
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
