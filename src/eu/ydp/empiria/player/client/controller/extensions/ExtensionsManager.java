package eu.ydp.empiria.player.client.controller.extensions;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

import eu.ydp.empiria.player.client.controller.extensions.internal.DefaultAssessmentFooterViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.DefaultAssessmentHeaderViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsAssessmentFooterViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsAssessmentHeaderViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowCommandSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowRequestProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsInteractionEventSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsPageInterferenceSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsPlayerJsObjectUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsSessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsStatefulExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsStyleSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.IStateful;

public class ExtensionsManager implements IStateful {

	public List<Extension> extensions;

	public ExtensionsManager() {
		extensions = new ArrayList<Extension>();
	}

	public void init() {

		for (Extension ext : extensions) {
			ext.init();
		}
	}

	private Extension getExtensionInstance(ExtensionType exType) {
		Extension currExt = null;
		if (exType != null) {
			switch (exType) {
			case EXTENSION_PROCESSOR_FLOW_REQUEST:
				currExt = new JsFlowRequestProcessorExtension();
				break;
			case EXTENSION_PROCESSOR_MEDIA:
				currExt = new JsMediaProcessorExtension();
				break;
			case EXTENSION_LISTENER_DELIVERY_EVENTS:
				currExt = new JsDeliveryEventsListenerExtension();
				break;
			case EXTENSION_SOCKET_USER_STYLE_CLIENT:
				currExt = new JsStyleSocketUserExtension();
				break;
			case EXTENSION_SOCKET_USER_SESSION_DATA_CLIENT:
				currExt = new JsSessionDataSocketUserExtension();
				break;
			case EXTENSION_SOCKET_USER_DATA_SOURCE_DATA_CLIENT:
				currExt = new JsDataSourceDataSocketUserExtension();
				break;
			case EXTENSION_SOCKET_USER_FLOW_DATA_CLIENT:
				currExt = new JsFlowDataSocketUserExtension();
				break;
			case EXTENSION_SOCKET_USER_FLOW_COMMAND:
				currExt = new JsFlowCommandSocketUserExtension();
				break;
			case EXTENSION_SOCKET_USER_FLOW_REQUEST:
				currExt = new JsFlowRequestSocketUserExtension();
				break;
			case EXTENSION_SOCKET_USER_INTERFERENCE_PAGE:
				currExt = new JsPageInterferenceSocketUserExtension();
				break;
			case EXTENSION_SOCKET_USER_INTERACTION_EVENT:
				currExt = new JsInteractionEventSocketUserExtension();
				break;
			case EXTENSION_VIEW_ASSESSMENT_HEADER:
				currExt = new JsAssessmentHeaderViewExtension();
				break;
			case EXTENSION_VIEW_ASSESSMENT_FOOTER:
				currExt = new JsAssessmentFooterViewExtension();
				break;
			case EXTENSION_CLIENT_STATEFUL:
				currExt = new JsStatefulExtension();
				break;
			case EXTENSION_PLAYER_JS_OBJECT_USER:
				currExt = new JsPlayerJsObjectUserExtension();
				break;
			default:
				break;
			}
		}
		return currExt;
	}

	public List<Extension> addExtension(JavaScriptObject extensionJsObject) {
		String extType = getFieldType(extensionJsObject);
		List<Extension> currExtensions = new ArrayList<Extension>();
		if (extType != null) {
			String[] extTypes = extType.split(",");

			for (String currExtTypeString : extTypes) {
				ExtensionType currExtType = ExtensionType.fromString(currExtTypeString.trim());
				Extension currExt = getExtensionInstance(currExtType);
				if (currExt instanceof JsExtension) {
					((JsExtension) currExt).initJs(extensionJsObject);
					extensions.add(currExt);
					currExtensions.add(currExt);
				}
			}
		}
		return currExtensions;
	}

	public Extension addExtension(Extension extension) {
		extensions.add(extension);
		return extension;

	}

	private native String getFieldType(JavaScriptObject jsObject)/*-{
		if (typeof jsObject.getType == 'function')
			return jsObject.getType();
		return null;
	}-*/;

	public List<Extension> getExtensions() {
		return extensions;
	}

	public Extension getInternaleExtensionByName(String name) {
		Extension retValue = null;
		if ("DefaultAssessmentHeaderViewExtension".equals(name)) {
			retValue = new DefaultAssessmentHeaderViewExtension();
		}
		if ("DefaultAssessmentFooterViewExtension".equals(name)) {
			retValue = new DefaultAssessmentFooterViewExtension();
		}
		if ("DefaultSoundProcessorExtension".equals(name)) {
			retValue = PlayerGinjector.INSTANCE.getDefaultMediaExtension();
		}
		return retValue;
	}

	@Override
	public JSONArray getState() {
		JSONArray arr = new JSONArray();
		for (Extension ext : extensions) {
			if (ext instanceof StatefulExtension) {
				arr.set(arr.size(), ((StatefulExtension) ext).getState());
			}
		}
		return arr;
	}

	@Override
	public void setState(JSONArray newState) {
		int counter = 0;
		for (Extension ext : extensions) {
			if (ext instanceof StatefulExtension  &&  counter < newState.size()) {
				((StatefulExtension) ext).setState(newState.get(counter).isArray());
				counter++;
			}
		}
	}

}
