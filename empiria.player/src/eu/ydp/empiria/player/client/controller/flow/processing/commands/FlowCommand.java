package eu.ydp.empiria.player.client.controller.flow.processing.commands;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class FlowCommand implements IFlowCommand {

	protected String name;

	private FlowCommand(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public static IFlowCommand fromJsObject(JavaScriptObject commandJsObject){
		String objName = getJsObjectName(commandJsObject);
		if (objName == null  ||  objName.equals(""))
			return null;
		int objIndex = getJsObjectIndex(commandJsObject);
		
		IFlowCommand command = null;

		if (NavigateNextItem.NAME.equals(objName)) {
			command = new NavigateNextItem();
		} else if (NavigatePreviousItem.NAME.equals(objName)) {
			command = new NavigatePreviousItem();
		} else if (NavigateFirstItem.NAME.equals(objName)) {
			command = new NavigateFirstItem();
		} else if (NavigateLastItem.NAME.equals(objName)) {
			command = new NavigateLastItem();
		} else if (NavigateToc.NAME.equals(objName)) {
			command = new NavigateToc();
		} else if (NavigateTest.NAME.equals(objName)) {
			command = new NavigateTest();
		} else if (NavigateSummary.NAME.equals(objName)) {
			command = new NavigateSummary();
		} else if (NavigateGotoItem.NAME.equals(objName)) {
			command = new NavigateGotoItem(objIndex);
		} else if (NavigatePreviewItem.NAME.equals(objName)) {
			command = new NavigatePreviewItem(objIndex);
		} else if (Continue.NAME.equals(objName)) {
			command = new Continue();
		} else if (Check.NAME.equals(objName)) {
			command = new Check();
		} else if (Reset.NAME.equals(objName)) {
			command = new Reset();
		} else if (ShowAnswers.NAME.equals(objName)) {
			command = new ShowAnswers();
		} else if (Lock.NAME.equals(objName)) {
			command = new Lock();
		}else if (Unlock.NAME.equals(objName)) {
			command = new Unlock();
		}
		
		return command;
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

	public final static class NavigateNextItem extends FlowCommand{
		public NavigateNextItem(){
			super(NAME);
		}		
		public static final String NAME = "NAVIGATE_NEXT_ITEM"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.nextPage();
		}
	}
	public final static class NavigatePreviousItem extends FlowCommand{
		public NavigatePreviousItem(){
			super(NAME);
		}		
		public static final String NAME = "NAVIGATE_PREVIOUS_ITEM"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.previousPage();
		}
	}
	public final static class NavigateFirstItem extends FlowCommand{
		public NavigateFirstItem(){
			super(NAME);
		}		
		public static final String NAME = "NAVIGATE_FIRST_ITEM"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoFirstPage();
		}
	}
	public final static class NavigateLastItem extends FlowCommand{
		public NavigateLastItem(){
			super(NAME);
		}		
		public static final String NAME = "NAVIGATE_LAST_ITEM"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoLastPage();
		}
	}
	public final static class NavigateToc extends FlowCommand{
		public NavigateToc(){
			super(NAME);
		}
		public static final String NAME = "NAVIGATE_TOC"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoToc();
		}
	}
	public final static class NavigateTest extends FlowCommand{
		public NavigateTest(){
			super(NAME);
		}		
		public static final String NAME = "NAVIGATE_TEST"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoTest();
		}
	}
	public final static class NavigateSummary extends FlowCommand{
		public NavigateSummary(){
			super(NAME);
		}		
		public static final String NAME = "NAVIGATE_SUMMARY"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoSummary();
		}
	}
	public abstract static class FlowCommandWithIndex extends FlowCommand{
		public FlowCommandWithIndex(String name, int index){
			super(name);
			this.index = index;
		}
		protected int index;
	}
	public final static class NavigateGotoItem extends FlowCommandWithIndex{
		public NavigateGotoItem(int index){
			super(NAME, index);
		}	
		public static final String NAME = "NAVIGATE_GOTO_ITEM"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoPage(index);
		}
	}
	public final static class NavigatePreviewItem extends FlowCommandWithIndex{
		public NavigatePreviewItem(int index){
			super(NAME, index);
		}	
		public static final String NAME = "NAVIGATE_PREVIEW_ITEM"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.previewPage(index);
		}
	}
	public static class Check extends FlowCommand{
		public Check(){
			super(NAME);
		}
		public static final String NAME = "CHECK"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.checkPage();
		}
	}
	public static class Continue extends FlowCommand{
		public Continue(){
			super(NAME);
		}
		public static final String NAME = "CONTINUE"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.continuePage();
		}
	}
	public static class Reset extends FlowCommand{
		public Reset(){
			super(NAME);
		}
		public static final String NAME = "RESET"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.resetPage();
		}
	}
	public static class ShowAnswers extends FlowCommand{
		public ShowAnswers(){
			super(NAME);
		}
		public static final String NAME = "SHOW_ANSWERS"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.showAnswers();
		}
	}
	public static class Lock extends FlowCommand{
		public Lock(){
			super(NAME);
		}
		public static final String NAME = "LOCK"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.lockPage();
		}
	}
	public static class Unlock extends FlowCommand{
		public Unlock(){
			super(NAME);
		}
		public static final String NAME = "UNLOCK"; 
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.unlockPage();
		}
	}
	
	
}
