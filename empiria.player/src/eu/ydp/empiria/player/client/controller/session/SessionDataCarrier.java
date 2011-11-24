package eu.ydp.empiria.player.client.controller.session;

import com.google.gwt.core.client.JavaScriptObject;

public class SessionDataCarrier {
	
	public SessionDataCarrier(){
		
	}

	public int[] dones;
	public int[] todos;
	public int[] times;
	public int[] checks;
	public int[] mistakes;
	public boolean[] visiteds;
	public int doneTotal;
	public int todoTotal;
	public int timeTotal;
	public int visitedCount;
	
	public JavaScriptObject toJsObject(){
		return createJsObject();
	}
	
	private native JavaScriptObject createJsObject()/*-{
		var obj = [];
		var instance = this;
		obj.getTimeTotal = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::timeTotal;
		}
		obj.getVariableTotalValue = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::timeTotal;
		}
		obj.getDoneTotal = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::doneTotal;
		}
		obj.getTodoTotal = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::todoTotal;
		}
		obj.getVisitedCount = function(){
			return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::visitedCount;
		}
		return obj;
	}-*/;
}
