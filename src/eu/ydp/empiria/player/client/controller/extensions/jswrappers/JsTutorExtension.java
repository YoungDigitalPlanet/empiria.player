package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorConfigJs;
import eu.ydp.empiria.player.client.controller.extensions.types.TutorExtension;
import eu.ydp.gwtutil.client.collections.JsMapStringToInt;

public class JsTutorExtension extends AbstractJsExtension implements TutorExtension {
	
	private JavaScriptObject playerJsObject;
	private TutorService tutorService;

	@Override
	public void init() {	
	}
	
	@Override
	public String getTutorId() {
		return getTutorIdNative(extensionJsObject);
	}

	private final native String getTutorIdNative(JavaScriptObject extensionJsObject)/*-{
		return extensionJsObject.getTutorId(); 
	}-*/;

	@Override
	public TutorConfig getTutorConfig() {
		JavaScriptObject tutorConfigJso = getTutorConfigNative(extensionJsObject);
		TutorConfigJs tutorConfigJs = tutorConfigJso.cast();
		return new TutorConfig(tutorConfigJs);
	}

	private final native JavaScriptObject getTutorConfigNative(JavaScriptObject extensionJsObject)/*-{
		return extensionJsObject.getTutorConfig(); 
	}-*/;

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_TUTOR;
	}

	@Override
	public void initJsApi(JavaScriptObject playerJsObject, TutorService tutorService) {
		this.playerJsObject = playerJsObject;
		this.tutorService = tutorService;
		initializeJsApiOnPlayer(playerJsObject);
	}

	private native void initializeJsApiOnPlayer(JavaScriptObject playerJsObject) /*-{
		var self = this;
		playerJsObject.exportTutorPersonasId = function(){
			return self.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsTutorExtension::exportTutorPersonasId()();
		}
		
		playerJsObject.importTutorPersonasId = function(jsTutorToPersonaIndex){
			self.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsTutorExtension::importTutorPersonasId(Leu/ydp/gwtutil/client/collections/JsMapStringToInt;)(jsTutorToPersonaIndex);
		}
		
	}-*/;

	JsMapStringToInt exportTutorPersonasId() {
		Map<String, Integer> tutorIdToCurrentPersonaIndexMap = tutorService.buildTutorIdToCurrentPersonaIndexMap();
		JsMapStringToInt jsTutorToPersonaIndex = JavaScriptObject.createObject().cast();
		for(Entry<String, Integer> tutorIdToCurrentPersonaIndex : tutorIdToCurrentPersonaIndexMap.entrySet()) {
			String key = tutorIdToCurrentPersonaIndex.getKey();
			Integer value = tutorIdToCurrentPersonaIndex.getValue();
			jsTutorToPersonaIndex.put(key, value);
		}
		
		return jsTutorToPersonaIndex;
	}
	
	void importTutorPersonasId(JsMapStringToInt jsTutorToPersonaIndex) {
		tutorService.setCurrentPersonasForTutors(jsTutorToPersonaIndex);
	} 
	
}
