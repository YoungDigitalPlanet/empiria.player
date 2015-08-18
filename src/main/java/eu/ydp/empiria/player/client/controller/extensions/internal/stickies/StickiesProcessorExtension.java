package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieDragController;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieDragHandlersManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieMinimizeMaximizeController;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.geom.MathUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StickiesProcessorExtension extends InternalExtension implements DataSourceDataSocketUserExtension, PlayerJsObjectModifierExtension,
        StatefulExtension {

    static final int DISTANCE_MIN_COMPONENT = 20;
    static final int DISTANCE_MIN = (int) (DISTANCE_MIN_COMPONENT * Math.pow(2, 0.5));
    @Inject
    IPlayerContainersAccessor itemBodyAccessor;
    @Inject
    EventsBus eventsBus;
    @Inject
    StickieFactory stickieFactory;
    @Inject
    Provider<IStickieProperties> propertiesProvider;
    @Inject
    PageScopeFactory pageScopeFactory;

    List<List<IStickieProperties>> stickies = new ArrayList<>();
    Map<IStickieProperties, IStickieView> views = new HashMap<>();
    private DataSourceDataSupplier dataSourceSupplier;
    int currItemIndex = 0;
    JavaScriptObject playerJsObject;

    @Override
    public void init() {
        initStickiesList(dataSourceSupplier.getItemsCount());

        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), new PlayerEventHandler() {

            @Override
            public void onPlayerEvent(PlayerEvent event) {
                currItemIndex = (Integer) event.getValue();
            }
        });

        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.ASSESSMENT_STARTING), new PlayerEventHandler() {
            @Override
            public void onPlayerEvent(PlayerEvent event) {
                parseExternalStickies();

            }
        });

        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_INITIALIZED), new PlayerEventHandler() {
            @Override
            public void onPlayerEvent(PlayerEvent event) {
                initStickies(currItemIndex);
            }
        });

    }

    private void parseExternalStickies() {
        JavaScriptObject externalStickies = getExternalStickies();
        if (externalStickies != null) {
            JSONArray externalState = (JSONArray) JSONParser.parseLenient(externalStickies.toString());
            if (externalState.size() > 0) {
                fillStickies(externalState);
            }
        }

    }

    private void initStickiesList(int itemsCount) {
        stickies.clear();
        for (int i = 0; i < itemsCount; i++) {
            stickies.add(new ArrayList<IStickieProperties>());
        }
    }

    @Override
    public JSONArray getState() {
        JSONArray stateArray = new JSONArray();
        for (int i = 0; i < stickies.size(); i++) {
            stateArray.set(i, getItemState(stickies.get(i)));
        }
        return stateArray;
    }

    private JSONArray getItemState(List<IStickieProperties> list) {
        JSONArray arr = new JSONArray();
        for (IStickieProperties sp : list) {
            arr.set(arr.size(), new JSONObject((JavaScriptObject) sp));
        }
        return arr;
    }

    @Override
    public void setState(JSONArray newState) {
        if (stickies.isEmpty()) {
            fillStickies(newState);
        }

    }

    private void fillStickies(JSONArray state) {
        stickies.clear();
        for (int i = 0; i < state.size(); i++) {
            stickies.add(decodeItemState(state.get(i).isArray()));
        }
    }

    private List<IStickieProperties> decodeItemState(JSONArray array) {
        List<IStickieProperties> itemStickies = new ArrayList<IStickieProperties>();
        for (int i = 0; i < array.size(); i++) {
            IStickieProperties sp = array.get(i).isObject().getJavaScriptObject().<StickieProperties>cast();
            itemStickies.add(sp);
        }
        return itemStickies;
    }

    native JavaScriptObject getExternalStickies()/*-{
        var playerJso = this.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::playerJsObject;
        if (typeof playerJso != 'undefined' && playerJso != null && typeof playerJso.getExternalStickies == 'function') {
            return playerJso.getExternalStickies();
        }
        return null;
    }-*/;

    @Override
    public void setPlayerJsObject(JavaScriptObject playerJsObject) {
        this.playerJsObject = playerJsObject;
        initJsApi(playerJsObject);
    }

    private native void initJsApi(JavaScriptObject playerJsObject) /*-{
        var self = this;
        playerJsObject.stickiesAdd = function (colorIndex) {
            self.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::addStickie(I)(colorIndex);
        }
        playerJsObject.stickiesClearAll = function () {
            self.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::clearAll()();
        }
        playerJsObject.getStickies = function () {
            return self.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::getState()();
        }
        playerJsObject.setStickies = function (stickiesStateJson) {
            self.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::setState(Lcom/google/gwt/json/client/JSONArray;)(stickiesStateJson);
        }
    }-*/;

    @Override
    public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
        dataSourceSupplier = supplier;
    }

    private void addStickie(int colorIndex) {
        IStickieProperties sp = createStickie(colorIndex);
        getStickiesForCurrentItem().add(sp);
        addStickieView(sp, true);
    }

    private IStickieProperties createStickie(int colorIndex) {
        IStickieProperties sp = propertiesProvider.get();
        sp.setColorIndex(colorIndex);
        sp.updateTimestamp();
        return sp;
    }

    private void addStickieView(final IStickieProperties stickieProperties, boolean initialAddition) {
        StickieRegistration stickieRegistration = new StickieRegistration() {

            @Override
            public void removeStickie() {
                deleteStickie(stickieProperties);
            }
        };

        StickieMinimizeMaximizeController stickieMinimizeMaximizeController = stickieFactory.createStickieMinimizeMaximizeController(stickieProperties);
        final IStickiePresenter stickiePresenter = stickieFactory.createStickiePresenter(stickieProperties, stickieMinimizeMaximizeController,
                stickieRegistration);
        StickieDragController stickieDragController = stickieFactory.createStickieDragController(stickiePresenter, stickieProperties);
        StickieDragHandlersManager stickieDragHandlersManager = stickieFactory.createStickieDragHandlerManager(stickieDragController);

        HasWidgets itemBodyContainer = itemBodyAccessor.getItemBodyContainer(currItemIndex);
        IStickieView stickieView = stickieFactory.createStickieView(itemBodyContainer, stickiePresenter, stickieDragHandlersManager);

        stickiePresenter.setView(stickieView);
        stickiePresenter.updateStickieView();

        views.put(stickieProperties, stickieView);

        if (initialAddition) {
            stickiePresenter.centerPositionToView();
            checkStickieOverlay(stickieProperties);
            updateStickiePosition(stickieProperties);
        } else {
            addPageContentResizedHandlerToAdjustStickiePosition(stickiePresenter);
        }
    }

    private void addPageContentResizedHandlerToAdjustStickiePosition(final IStickiePresenter stickiePresenter) {
        PlayerEventHandler pageContentResizedHandler = new PlayerEventHandler() {
            @Override
            public void onPlayerEvent(PlayerEvent event) {
                stickiePresenter.correctStickiePosition();
            }
        };
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CONTENT_GROWN), pageContentResizedHandler, pageScopeFactory.getCurrentPageScope());
    }

    private void updateStickiePosition(IStickieProperties sp) {
        views.get(sp).setPosition(sp.getX(), sp.getY());
    }

    private void checkStickieOverlay(IStickieProperties sp) {
        for (int s = 0; s < getStickiesForCurrentItem().size(); s++) {
            IStickieProperties refSp = getStickiesForCurrentItem().get(s);
            if (refSp != sp) {
                if (MathUtil.distance(sp.getX(), sp.getY(), refSp.getX(), refSp.getY()) < DISTANCE_MIN) {
                    sp.setX(refSp.getX() + DISTANCE_MIN_COMPONENT);
                    sp.setY(refSp.getY() + DISTANCE_MIN_COMPONENT);
                }
            }
        }
    }

    private void deleteStickie(IStickieProperties sp) {
        deleteStickieView(sp);
        getStickiesForCurrentItem().remove(sp);
    }

    private void deleteStickieView(IStickieProperties sp) {
        views.get(sp).remove();
        views.remove(sp);
    }

    private void initStickies(int itemIndex) {
        if (stickies.size() > itemIndex) {
            for (IStickieProperties sp : stickies.get(itemIndex)) {
                addStickieView(sp, false);
            }
        }

    }

    private void clearAll() {
        List<IStickieProperties> currStickies = getStickiesForCurrentItem();
        while (!currStickies.isEmpty()) {
            deleteStickie(currStickies.get(0));
        }
    }

    private List<IStickieProperties> getStickiesForCurrentItem() {
        return stickies.get(currItemIndex);
    }

}
