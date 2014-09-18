package eu.ydp.empiria.player.client.module.testmode.submit;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ILockable;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class TestPageSubmitModule extends SimpleModuleBase implements ILockable {

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
	protected void initModule(Element element) {
		presenter.bindUi();
	}

	@Override
	public void lock(boolean lo) {
		if (lo) {
			presenter.lock();
		} else {
			presenter.unlock();
		}
	}
}
