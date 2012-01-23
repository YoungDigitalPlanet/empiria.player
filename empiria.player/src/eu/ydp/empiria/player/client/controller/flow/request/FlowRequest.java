package eu.ydp.empiria.player.client.controller.flow.request;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.util.js.JSArrayUtils;


public abstract class FlowRequest implements IFlowRequest {

	protected String name;
	
	private FlowRequest(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public static IFlowRequest fromJsObject(JavaScriptObject obj){
		String objName = getJsObjectName(obj);
		if (objName == null  ||  objName.equals(""))
			return null;
		int objIndex = getJsObjectIndex(obj);
		
		IFlowRequest request = null;

		if ("NAVIGATE_NEXT_ITEM".equals(objName)){ 
			request = new NavigateNextItem();
		} else if ("NAVIGATE_PREVIOUS_ITEM".equals(objName)){ 
			request = new NavigatePreviousItem();
		} else if ("NAVIGATE_FIRST_ITEM".equals(objName)){ 
			request = new NavigateFirstItem();
		} else if ("NAVIGATE_LAST_ITEM".equals(objName)){ 
			request = new NavigateLastItem();
		} else if ("NAVIGATE_TOC".equals(objName)){ 
			request = new NavigateToc();
		} else if ("NAVIGATE_TEST".equals(objName)){ 
			request = new NavigateTest();
		} else if ("NAVIGATE_SUMMARY".equals(objName)){ 
			request = new NavigateSummary();
		} else if ("NAVIGATE_GOTO_ITEM".equals(objName)){ 
			request = new NavigateGotoItem(objIndex);
		} else if ("NAVIGATE_PREVIEW_ITEM".equals(objName)){ 
			request = new NavigatePreviewItem(objIndex);
		} else if ("CONTINUE".equals(objName)){ 
			request = new Continue();
		} else if ("CHECK".equals(objName)){ 
			request = new Check();
		} else if ("RESET".equals(objName)){ 
			request = new Reset();
		} else if ("SHOW_ANSWERS".equals(objName)){ 
			request = new ShowAnswers();
		} else if ("LOCK".equals(objName)){ 
			request = new Lock();
		}else if ("UNLOCK".equals(objName)){ 
			request = new Unlock();
		} else {
			JavaScriptObject params = getJsObjectParams(obj);
			request = new Custom(objName, params);
		}
		
		return request;
	}

	private static native String getJsObjectName(JavaScriptObject obj)/*-{
		if (typeof obj.name == 'string')
			return obj.name;
		return "";
	}-*/;
	private static native int getJsObjectIndex(JavaScriptObject obj)/*-{
		if (typeof obj.index == 'number')
			return obj.index;
		return -1;
	}-*/;
	private static native JavaScriptObject getJsObjectParams(JavaScriptObject obj)/*-{
		if (typeof obj.params == 'object')
			return obj.params;
		return {};
	}-*/;
	
	public JavaScriptObject toJsObject() {
		return createRequestJsObject(getName());
	}
	
	private native JavaScriptObject createRequestJsObject(String name)/*-{
		var req = {};
		req.name = name;
		return req;
	}-*/;
	
	public final static class NavigateNextItem extends FlowRequest{
		public NavigateNextItem(){
			super("NAVIGATE_NEXT_ITEM");
		}
	}
	public final static class NavigatePreviousItem extends FlowRequest{
		public NavigatePreviousItem(){
			super("NAVIGATE_PREVIOUS_ITEM");
		}
	}
	public final static class NavigateFirstItem extends FlowRequest{
		public NavigateFirstItem(){
			super("NAVIGATE_FIRST_ITEM");
		}
	}
	public final static class NavigateLastItem extends FlowRequest{
		public NavigateLastItem(){
			super("NAVIGATE_LAST_ITEM");
		}
	}
	public final static class NavigateToc extends FlowRequest{
		public NavigateToc(){
			super("NAVIGATE_TOC");
		}
	}
	public final static class NavigateSummary extends FlowRequest{
		public NavigateSummary(){
			super("NAVIGATE_SUMMARY");
		}
	}
	public final static class NavigateTest extends FlowRequest{
		public NavigateTest(){
			super("NAVIGATE_TEST");
		}
	}
	public abstract static class FlowRequestWithIndex extends FlowRequest{
		
		private FlowRequestWithIndex(String name, int index){
			super(name);
			this.index = index;
		}		
		protected int index;
		
		public int getIndex(){
			return this.index;
		}

		@Override
		public JavaScriptObject toJsObject() {
			return createRequestJsObject(getName(), getIndex());
		}
		
		private native JavaScriptObject createRequestJsObject(String name, int index)/*-{
			var req = {};
			req.name = name;
			req.index = index;
			return req;
		}-*/;
		
	}
	public final static class NavigateGotoItem extends FlowRequestWithIndex{
		public NavigateGotoItem(int index) {
			super("NAVIGATE_GOTO_ITEM", index);
		}		
	}
	public final static class NavigatePreviewItem extends FlowRequestWithIndex{
		public NavigatePreviewItem(int index) {
			super("NAVIGATE_PREVIEW_ITEM", index);
		}		
	}
	public static class Check extends FlowRequest{
		public Check(){
			super("CHECK");
		}
	}
	public static class Continue extends FlowRequest{
		public Continue(){
			super("CONTINUE");
		}
	}
	public static class Reset extends FlowRequest{
		public Reset(){
			super("RESET");
		}
	}
	public static class ShowAnswers extends FlowRequest{
		public ShowAnswers() {
			super("SHOW_ANSWERS");
		}
	}
	public static class Lock extends FlowRequest{
		public Lock() {
			super("LOCK");
		}
	}
	public static class Unlock extends FlowRequest{
		public Unlock() {
			super("UNLOCK");
		}
	}
	public static class Custom extends FlowRequest{
		
		private Custom(String name, JavaScriptObject params ){
			super(name);
			this.params = params;
		}		
		
		protected JavaScriptObject params;
		
		public JavaScriptObject getParams(){
			return this.params;
		}

		@Override
		public JavaScriptObject toJsObject() {
			return createRequestJsObject(getName(), params);
		}
		
		private native JavaScriptObject createRequestJsObject(String name, JavaScriptObject params)/*-{
			var req = {};
			req.name = name;
			req.params = params;
			return req;
		}-*/;
		
	}
	
}
