package eu.ydp.empiria.player.client.module;

import com.google.gwt.core.client.JavaScriptObject;

public class JsSocketFactory {

	public static JavaScriptObject createSocketObject(Object module){
		JavaScriptObject jso = JavaScriptObject.createObject(); 

		jso = addObjectFunctions(jso, module.getClass().getName());
		
		if (module instanceof IActivity){
			jso = addActivityFunctions(jso, module);
		}
		
		if (module instanceof IInteractionModule){
			jso = addInteractionFunctions(jso, module);
		}
		
		return jso;
	}
	
	public static native JavaScriptObject addObjectFunctions(JavaScriptObject jso, String className)/*-{
		var instanceClassName = className;		
		jso.getClassName = function(){
			return instanceClassName;
		}
		return jso; 
	}-*/;
	
	public static native JavaScriptObject addActivityFunctions(JavaScriptObject jso, Object moduleInstance)/*-{
		var instance = moduleInstance;
		jso.showAnswers = function(value){
			instance.@eu.ydp.empiria.player.client.module.IActivity::showCorrectAnswers(Z)(true);
			instance.@eu.ydp.empiria.player.client.module.IActivity::lock(Z)(true);
		}
		// REMOVE HIDE_ANSWERS
		//jso.hideAnswers = function(value){
		//	instance.@eu.ydp.empiria.player.client.module.IActivity::showCorrectAnswers(Z)(false);
		//	instance.@eu.ydp.empiria.player.client.module.IActivity::lock(Z)(false);
		//}
		jso.check = function(value){
			instance.@eu.ydp.empiria.player.client.module.IActivity::markAnswers(Z)(true);
			instance.@eu.ydp.empiria.player.client.module.IActivity::lock(Z)(true);
		}
		jso.continue1 = function(value){
			instance.@eu.ydp.empiria.player.client.module.IActivity::markAnswers(Z)(false);
			instance.@eu.ydp.empiria.player.client.module.IActivity::showCorrectAnswers(Z)(false);
			instance.@eu.ydp.empiria.player.client.module.IActivity::lock(Z)(false);
		}
		jso.lock = function(value){
			instance.@eu.ydp.empiria.player.client.module.IActivity::lock(Z)(true);
		}
		jso.unlock = function(value){
			instance.@eu.ydp.empiria.player.client.module.IActivity::lock(Z)(false);
		}
		jso.reset = function(){
			instance.@eu.ydp.empiria.player.client.module.IActivity::reset()();
		}
		return jso;
	}-*/;

	public static native JavaScriptObject addInteractionFunctions(JavaScriptObject jso, Object moduleInstance)/*-{
		var instance = moduleInstance;
		jso.getIdentifier = function(){
			return instance.@eu.ydp.empiria.player.client.module.IInteractionModule::getIdentifier();
		} 	
		return jso;	
	}-*/;
}
