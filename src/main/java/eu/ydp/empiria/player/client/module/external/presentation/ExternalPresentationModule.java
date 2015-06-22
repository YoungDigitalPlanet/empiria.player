package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.external.common.ExternalFolderNameProvider;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalPresentationModule extends SimpleModuleBase implements ILockable, IResetable, ExternalFolderNameProvider,
		StatefulModule {

	public static final String SOURCE_ATTRIBUTE = "src";
	private final ExternalPresentationPresenter presenter;
	private final ExternalPaths externalPaths;
	private String presentationName;

	@Inject
	public ExternalPresentationModule(ExternalPresentationPresenter presenter, @ModuleScoped ExternalPaths externalPaths) {
		this.presenter = presenter;
		this.externalPaths = externalPaths;
	}

	@Override
	protected void initModule(Element element) {
		presentationName = element.getAttribute(SOURCE_ATTRIBUTE);
		externalPaths.setExternalFolderNameProvider(this);
		presenter.init();
	}

	@Override
	public String getIdentifier() {
		return getModuleId();
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

	@Override
	public String getExternalFolderName() {
		return presentationName;
	}
}
