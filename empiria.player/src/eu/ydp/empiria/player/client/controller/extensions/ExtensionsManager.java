package eu.ydp.empiria.player.client.controller.extensions;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

import eu.ydp.empiria.player.client.controller.extensions.internal.DefaultAssessmentFooterViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.DefaultAssessmentHeaderViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultSoundProcessorExtension;
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
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsSoundProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsStatefulExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsStyleSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
import eu.ydp.empiria.player.client.module.IStateful;

public class ExtensionsManager implements IStateful  {

	public List<Extension> extensions;
	
	public ExtensionsManager(){
		extensions = new ArrayList<Extension>();
	}
	
	public void init(){	
		
		for (Extension ext : extensions){
			ext.init();
		}
	}
	
	public List<Extension> addExtension(JavaScriptObject extensionJsObject){
		String extType = getFieldType(extensionJsObject);
		List<Extension> currExtensions = new ArrayList<Extension>();
		
		if (extType == null)
			return currExtensions;
		
		String[] extTypes = extType.split(",");
		
		for (String currExtTypeString : extTypes){
			
			Extension currExt = null;
			ExtensionType currExtType = ExtensionType.fromString(currExtTypeString.trim());
		
			if ( currExtType == ExtensionType.EXTENSION_PROCESSOR_FLOW_REQUEST ){
				currExt = new JsFlowRequestProcessorExtension();
			} else if ( currExtType == ExtensionType.EXTENSION_PROCESSOR_SOUND ){
				currExt = new JsSoundProcessorExtension();
			} 
			
			else if ( currExtType == ExtensionType.EXTENSION_LISTENER_DELIVERY_EVENTS ){
				currExt = new JsDeliveryEventsListenerExtension();
			} 
			
			else if ( currExtType == ExtensionType.EXTENSION_SOCKET_USER_STYLE_CLIENT ){
				currExt = new JsStyleSocketUserExtension();
			} else if ( currExtType == ExtensionType.EXTENSION_SOCKET_USER_SESSION_DATA_CLIENT){
				currExt = new JsSessionDataSocketUserExtension();
			} else if ( currExtType == ExtensionType.EXTENSION_SOCKET_USER_DATA_SOURCE_DATA_CLIENT){
				currExt = new JsDataSourceDataSocketUserExtension();
			} else if ( currExtType == ExtensionType.EXTENSION_SOCKET_USER_FLOW_DATA_CLIENT){
				currExt = new JsFlowDataSocketUserExtension();
			} 
			
			else if ( currExtType == ExtensionType.EXTENSION_SOCKET_USER_FLOW_COMMAND ){
				currExt = new JsFlowCommandSocketUserExtension();
			} else if ( currExtType == ExtensionType.EXTENSION_SOCKET_USER_FLOW_REQUEST ){
				currExt = new JsFlowRequestSocketUserExtension();
			} else if ( currExtType == ExtensionType.EXTENSION_SOCKET_USER_INTERFERENCE_PAGE){
				currExt = new JsPageInterferenceSocketUserExtension();
			} else if ( currExtType == ExtensionType.EXTENSION_SOCKET_USER_INTERACTION_EVENT ){
				currExt = new JsInteractionEventSocketUserExtension();
			}
			
			else if (currExtType == ExtensionType.EXTENSION_VIEW_ASSESSMENT_HEADER){
				currExt = new JsAssessmentHeaderViewExtension();
			} else if (currExtType == ExtensionType.EXTENSION_VIEW_ASSESSMENT_FOOTER){
				currExt = new JsAssessmentFooterViewExtension();
			}

			else if ( currExtType == ExtensionType.EXTENSION_CLIENT_STATEFUL ){
				currExt = new JsStatefulExtension();
			} else if ( currExtType == ExtensionType.EXTENSION_PLAYER_JS_OBJECT_USER){
				currExt = new JsPlayerJsObjectUserExtension();
			}
			
			
			if (currExt != null  &&  currExt instanceof JsExtension){
				((JsExtension)currExt).initJs(extensionJsObject);
				extensions.add(currExt);
				currExtensions.add(currExt);
			}
		}
		return currExtensions;
	}
	
	public Extension addExtension(Extension extension){
		extensions.add(extension);
		return extension;
		
	}
	
	private native String getFieldType(JavaScriptObject jsObject)/*-{
		if (typeof jsObject.getType == 'function')
			return jsObject.getType();
		return null;
	}-*/;
	
	
	public List<Extension> getExtensions(){
		return extensions;
	}
	
	public Extension getInternaleExtensionByName(String name){
		if ("DefaultAssessmentHeaderViewExtension".equals(name))
			return new DefaultAssessmentHeaderViewExtension();
		if ("DefaultAssessmentFooterViewExtension".equals(name))
			return new DefaultAssessmentFooterViewExtension();
		if ("DefaultSoundProcessorExtension".equals(name))
			return new DefaultSoundProcessorExtension();
		if ("PlayerCoreApiExtension".equals(name))
			return new PlayerCoreApiExtension();
		return null;
	}

	@Override
	public JSONArray getState() {
		JSONArray arr = new JSONArray();
		for (Extension ext : extensions){
			if (ext instanceof StatefulExtension){
				arr.set(arr.size(), ((StatefulExtension)ext).getState());
			}
		}
		return arr;
	}

	@Override
	public void setState(JSONArray newState) {
		int counter = 0;
		for (Extension ext : extensions){
			if (ext instanceof StatefulExtension){
				((StatefulExtension)ext).setState(newState.get(counter).isArray());
				counter++;
			}
		}
	}

}
