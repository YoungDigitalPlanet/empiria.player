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

		if (NavigateNextItem.NAME.equals(objName)){ 
			request = new NavigateNextItem();
		} else if (NavigatePreviousItem.NAME.equals(objName)){ 
			request = new NavigatePreviousItem();
		} else if (NavigateFirstItem.NAME.equals(objName)){ 
			request = new NavigateFirstItem();
		} else if (NavigateLastItem.NAME.equals(objName)){ 
			request = new NavigateLastItem();
		} else if (NavigateToc.NAME.equals(objName)){ 
			request = new NavigateToc();
		} else if (NavigateTest.NAME.equals(objName)){ 
			request = new NavigateTest();
		} else if (NavigateSummary.NAME.equals(objName)){ 
			request = new NavigateSummary();
		} else if (NavigateGotoItem.NAME.equals(objName)){ 
			request = new NavigateGotoItem(objIndex);
		} else if (NavigatePreviewItem.NAME.equals(objName)){ 
			request = new NavigatePreviewItem(objIndex);
		} else if (Continue.NAME.equals(objName)){ 
			request = new Continue();
		} else if (Check.NAME.equals(objName)){ 
			request = new Check();
		} else if (Reset.NAME.equals(objName)){ 
			request = new Reset();
		} else if (ShowAnswers.NAME.equals(objName)){ 
			request = new ShowAnswers();
		} else if (Lock.NAME.equals(objName)){ 
			request = new Lock();
		}else if (Unlock.NAME.equals(objName)){ 
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
			super(NAME);
		}
		public static final String NAME = "NAVIGATE_NEXT_ITEM";
	}
	public final static class NavigatePreviousItem extends FlowRequest{
		public NavigatePreviousItem(){
			super(NAME);
		}
		public static final String NAME = "NAVIGATE_PREVIOUS_ITEM"; 
	}
	public final static class NavigateFirstItem extends FlowRequest{
		public NavigateFirstItem(){
			super(NAME);
		}
		public static final String NAME = "NAVIGATE_FIRST_ITEM";
	}
	public final static class NavigateLastItem extends FlowRequest{
		public NavigateLastItem(){
			super(NAME);
		}
		public static final String NAME = "NAVIGATE_LAST_ITEM"; 
	}
	public final static class NavigateToc extends FlowRequest{
		public NavigateToc(){
			super(NAME);
		}
		public static final String NAME = "NAVIGATE_TOC"; 
	}
	public final static class NavigateSummary extends FlowRequest{
		public NavigateSummary(){
			super(NAME);
		}
		public static final String NAME = "NAVIGATE_SUMMARY"; 
	}
	public final static class NavigateTest extends FlowRequest{
		public NavigateTest(){
			super(NAME);
		}
		public static final String NAME = "NAVIGATE_TEST";
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
			super(NAME, index);
		}		
		public static final String NAME = "NAVIGATE_GOTO_ITEM"; 
	}
	public final static class NavigatePreviewItem extends FlowRequestWithIndex{
		public NavigatePreviewItem(int index) {
			super(NAME, index);
		}		
		public static final String NAME = "NAVIGATE_PREVIEW_ITEM"; 
	}
	public static class Check extends FlowRequest{
		public Check(){
			super(NAME);
		}
		public static final String NAME = "CHECK"; 
	}
	public static class Continue extends FlowRequest{
		public Continue(){
			super(NAME);
		}
		public static final String NAME = "CONTINUE"; 
	}
	public static class Reset extends FlowRequest{
		public Reset(){
			super(NAME);
		}
		public static final String NAME = "RESET"; 
	}
	public static class ShowAnswers extends FlowRequest{
		public ShowAnswers() {
			super(NAME);
		}
		public static final String NAME = "SHOW_ANSWERS"; 
	}
	public static class Lock extends FlowRequest{
		public Lock() {
			super(NAME);
		}
		public static final String NAME = "LOCK"; 
	}
	public static class Unlock extends FlowRequest{
		public Unlock() {
			super(NAME);
		}
		public static final String NAME = "UNLOCK"; 
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
