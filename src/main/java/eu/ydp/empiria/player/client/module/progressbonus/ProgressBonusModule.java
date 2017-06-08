/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.progressbonus;

import com.google.common.base.Optional;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.core.flow.LifecycleModule;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.StatefulModule;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.progressbonus.presenter.ProgressBonusPresenter;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ProgressBonusModule extends SimpleModuleBase implements LifecycleModule, StatefulModule {

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
