package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApiNullObject;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalPresentationPresenter implements ExternalFrameLoadHandler<ExternalApi> {

	private final ExternalStateEncoder stateEncoder;
	private final ExternalView<ExternalApi, ExternalEmpiriaApi> view;
	private final ExternalStateSaver stateSaver;
	private final ExternalPaths externalPaths;
	private final ExternalEmpiriaApi empiriaApi;

	private ExternalApi externalApi;

	@Inject
	public ExternalPresentationPresenter(ExternalStateEncoder stateEncoder, ExternalView<ExternalApi, ExternalEmpiriaApi> view,
			@ModuleScoped ExternalStateSaver stateSaver,
			@ModuleScoped ExternalPaths externalPaths,
			ExternalEmpiriaApi empiriaApi) {
		this.stateEncoder = stateEncoder;
		this.view = view;
		this.stateSaver = stateSaver;
		this.externalPaths = externalPaths;
		this.empiriaApi = empiriaApi;

		externalApi = new ExternalApiNullObject();
	}

	public void init() {
		String url = externalPaths.getExternalEntryPointPath();
		view.init(empiriaApi, this, url);
	}

	@Override
	public void onExternalModuleLoaded(ExternalApi externalObject) {
		this.externalApi = externalObject;

		Optional<JavaScriptObject> externalState = stateSaver.getExternalState();
		if (externalState.isPresent()) {
			externalObject.setStateOnExternal(externalState.get());
		}
	}

	public Widget getView() {
		return view.asWidget();
	}

	public JSONArray getState() {
		JavaScriptObject state = externalApi.getStateFromExternal();
		stateSaver.setExternalState(state);
		return stateEncoder.encodeState(state);
	}

	public void setState(JSONArray newState) {
		JavaScriptObject state = stateEncoder.decodeState(newState);
		stateSaver.setExternalState(state);
	}

	public void lock() {
		externalApi.lock();
	}

	public void unlock() {
		externalApi.unlock();
	}

	public void reset() {
		externalApi.reset();
	}

}
