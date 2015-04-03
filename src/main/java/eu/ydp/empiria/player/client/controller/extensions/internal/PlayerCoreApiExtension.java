package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngineSocket;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEngineSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public class PlayerCoreApiExtension extends InternalExtension implements DeliveryEngineSocketUserExtension, PlayerJsObjectModifierExtension,
		DeliveryEventsListenerExtension {

	@Inject
	private EventsBus eventsBus;
	@Inject
	private FlowDataSupplier flowDataSupplier;
	@Inject
	private PlayerWorkModeService workModeService;

	private JavaScriptObject playerJsObject;
	private DeliveryEngineSocket deliveryEngineSocket;

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
		if (!"".equals(state)) {
			deliveryEngineSocket.setStateString(state);
		}
	}

	private native String callImportStateStringJs(JavaScriptObject playerJsObject)/*-{
        if (typeof playerJsObject.importStateString == 'function')
            return playerJsObject.importStateString();
        return "";
    }-*/;

	private String exportState() {
		return deliveryEngineSocket.getStateString();
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