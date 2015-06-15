package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;

public class ExternalPresentationModule extends SimpleModuleBase implements IStateful, IUniqueModule, ILockable, IResetable {

	@Inject
	private ExternalPresentationPresenter presenter;

	@Override
	protected void initModule(Element element) {
		presenter.init();
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	public Widget getView() {
		return presenter.getView();
	}

	@Override
	public JSONArray getState() {
		return presenter.getState();
	}

	@Override
	public void setState(JSONArray newState) {
		presenter.setState(newState);
	}

	@Override
	public void lock(boolean shouldLock) {
		if (shouldLock) {
			presenter.lock();
		} else {
			presenter.unlock();
		}
	}

	@Override
	public void reset() {
		presenter.reset();
	}
}
