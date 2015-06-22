package eu.ydp.empiria.player.client.module.progressbonus;

import com.google.common.base.Optional;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.progressbonus.presenter.ProgressBonusPresenter;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventHandler;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ProgressBonusModule extends SimpleModuleBase implements ILifecycleModule, StatefulModule {

	private static final String PROGRESS_BONUS_ID_ATTR = "progressBonusId";

	private final PlayerEventHandler testPageLoadedHandler = new PlayerEventHandler() {

		@Override
		public void onPlayerEvent(PlayerEvent event) {
			onProgressChanged();
		}
	};
	private final StateChangeEventHandler stateChangedHandler = new StateChangeEventHandler() {

		@Override
		public void onStateChange(StateChangeEvent event) {
			onProgressChanged();
		}
	};

	private ProgressBonusView view;
	private ProgressBonusPresenter presenter;
	private ProgressAssetProvider assetProvider;
	private ProgressCalculator progressCalculator;

	private Optional<Integer> assetId = Optional.absent();
	private ProgressAsset asset;
	private String identifier;

	@Inject
	public ProgressBonusModule(@ModuleScoped ProgressBonusView view, @ModuleScoped ProgressBonusPresenter presenter, ProgressAssetProvider assetProvider,
			ProgressCalculator progressCalculator, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
		this.view = view;
		this.presenter = presenter;
		this.assetProvider = assetProvider;
		this.progressCalculator = progressCalculator;
		addHandlers(eventsBus, pageScopeFactory);
	}

	private void addHandlers(EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
		final CurrentPageScope currentPageScope = pageScopeFactory.getCurrentPageScope();
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), testPageLoadedHandler, currentPageScope);
		eventsBus.addHandler(StateChangeEvent.getType(StateChangeEventTypes.OUTCOME_STATE_CHANGED), stateChangedHandler);
	}

	@Override
	protected void initModule(Element element) {
		identifier = element.getAttribute(PROGRESS_BONUS_ID_ATTR);
	}

	@Override
	public Widget getView() {
		return view.asWidget();
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public JSONArray getState() {
		JSONArray state = new JSONArray();
		state.set(0, new JSONNumber(asset.getId()));
		return state;
	}

	@Override
	public void setState(JSONArray newState) {
		JSONValue jsonValue = newState.get(0);
		if (jsonValue.isNumber() != null) {
			int idFromState = (int) jsonValue.isNumber().doubleValue();
			assetId = Optional.of(idFromState);
		}
	}

	private void onProgressChanged() {
		int progress = progressCalculator.getProgress();
		ShowImageDTO imageDTO = asset.getImageForProgress(progress);
		presenter.showImage(imageDTO);
	}

	@Override
	public void onSetUp() {
		if (assetId.isPresent()) {
			asset = assetProvider.createFrom(assetId.get());
		} else {
			asset = assetProvider.createRandom();
		}
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onBodyLoad() {
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onClose() {
	}
}
