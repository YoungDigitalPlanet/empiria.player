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

package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.common.base.Strings;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngineSocket;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateImportCreator;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateExportCreator;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.json.EmpiriaStateSerializer;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEngineSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;

public class PlayerCoreApiExtension extends InternalExtension implements DeliveryEngineSocketUserExtension, PlayerJsObjectModifierExtension,
        DeliveryEventsListenerExtension {

    private final FlowDataSupplier flowDataSupplier;
    private final PlayerWorkModeService workModeService;
    private final EmpiriaStateImportCreator empiriaStateImportCreator;
    private final EmpiriaStateExportCreator empiriaStateExportCreator;
    private final EmpiriaStateSerializer empiriaStateSerializer;

    private JavaScriptObject playerJsObject;
    private DeliveryEngineSocket deliveryEngineSocket;

    @Inject
    public PlayerCoreApiExtension(FlowDataSupplier flowDataSupplier, PlayerWorkModeService workModeService, EmpiriaStateImportCreator empiriaStateImportCreator, EmpiriaStateExportCreator empiriaStateExportCreator, EmpiriaStateSerializer empiriaStateSerializer) {
        this.flowDataSupplier = flowDataSupplier;
        this.workModeService = workModeService;
        this.empiriaStateImportCreator = empiriaStateImportCreator;
        this.empiriaStateExportCreator = empiriaStateExportCreator;
        this.empiriaStateSerializer = empiriaStateSerializer;
    }

    @Override
    public void init() {
        initApiJs(playerJsObject);
        initWorkMode();
    }

    private void initWorkMode() {
        if (isPreviewMode(playerJsObject)) {
            workModeService.tryToUpdateWorkMode(PlayerWorkMode.PREVIEW);
        }
    }

    @Override
    public void setDeliveryEngineSocket(DeliveryEngineSocket des) {
        deliveryEngineSocket = des;
    }

    @Override
    public void setPlayerJsObject(JavaScriptObject playerJsObject) {
        this.playerJsObject = playerJsObject;
    }

    @Override
    public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
        if (deliveryEvent.getType() == DeliveryEventType.ASSESSMENT_LOADING) {
            setOptions();
        }
        if (deliveryEvent.getType() == DeliveryEventType.ASSESSMENT_STARTING) {
            importInitialItemIndex();
            importState();
        }
    }

    private void setOptions() {
        JavaScriptObject flowOptionsJs = callImportFlowOptionsJs(playerJsObject);

        if (flowOptionsJs != null) {
            FlowOptions flowOptions = FlowOptions.fromJsObject(flowOptionsJs);
            deliveryEngineSocket.setFlowOptions(flowOptions);
        }

        JavaScriptObject displayOptionsJs = callImportDisplayOptionsJs(playerJsObject);

        if (displayOptionsJs != null) {
            DisplayOptions displayOptions = DisplayOptions.fromJsObject(displayOptionsJs);
            deliveryEngineSocket.setDisplayOptions(displayOptions);
        }
    }

    private native JavaScriptObject callImportFlowOptionsJs(JavaScriptObject playerJsObject)/*-{
        if (typeof playerJsObject.importFlowOptions == 'function')
            return playerJsObject.importFlowOptions();
        return null;
    }-*/;

    private native JavaScriptObject callImportDisplayOptionsJs(JavaScriptObject playerJsObject)/*-{
        if (typeof playerJsObject.importDisplayOptions == 'function')
            return playerJsObject.importDisplayOptions();
        return null;
    }-*/;

    private native boolean isPreviewMode(JavaScriptObject playerJsObject)/*-{
        if (!!playerJsObject.enablePreviewMode) {
            return playerJsObject.enablePreviewMode();
        }
        return false;
    }-*/;

    private void importState() {
        String state = callImportStateStringJs(playerJsObject);

        if (!Strings.isNullOrEmpty(state)) {

            String empiriaState = empiriaStateImportCreator.createState(state);
            deliveryEngineSocket.setStateString(empiriaState);
        }
    }

    private native String callImportStateStringJs(JavaScriptObject playerJsObject)/*-{
        if (typeof playerJsObject.importStateString == 'function')
            return playerJsObject.importStateString();
        return "";
    }-*/;

    private String exportState() {
        String stateString = deliveryEngineSocket.getStateString();
        EmpiriaState empiriaState = empiriaStateExportCreator.create(stateString);
        return empiriaStateSerializer.serialize(empiriaState).toString();
    }

    private int exportItemIndex() {
        return flowDataSupplier.getCurrentPageIndex();
    }

    private native void initApiJs(JavaScriptObject playerJsObject)/*-{
        var instance = this;
        playerJsObject.exportStateString = function () {
            return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension::exportState()();
        }
        playerJsObject.exportItemIndex = function () {
            return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension::exportItemIndex()();
        }

    }-*/;

    private void importInitialItemIndex() {
        int importedItemIndex = callImportInitialItemIndex(playerJsObject);
        Integer itemIndex = (importedItemIndex > -1) ? Integer.valueOf(importedItemIndex) : null;
        deliveryEngineSocket.setInitialItemIndex(itemIndex);
    }

    private native int callImportInitialItemIndex(JavaScriptObject playerJsObject)/*-{
        var itemIndex = -1;
        if (typeof playerJsObject.importInitialItemIndex == 'function') {
            var importedIndex = playerJsObject.importInitialItemIndex();
            if (!isNaN(importedIndex)) {
                itemIndex = parseInt(importedIndex);
            }
        }

        return itemIndex;

    }-*/;
}
