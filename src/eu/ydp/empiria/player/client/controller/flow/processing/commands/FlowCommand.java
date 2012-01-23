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

		if ("NAVIGATE_NEXT_ITEM".equals(objName)) {
			command = new NavigateNextItem();
		} else if ("NAVIGATE_PREVIOUS_ITEM".equals(objName)) {
			command = new NavigatePreviousItem();
		} else if ("NAVIGATE_FIRST_ITEM".equals(objName)) {
			command = new NavigateFirstItem();
		} else if ("NAVIGATE_LAST_ITEM".equals(objName)) {
			command = new NavigateLastItem();
		} else if ("NAVIGATE_TOC".equals(objName)) {
			command = new NavigateToc();
		} else if ("NAVIGATE_TEST".equals(objName)) {
			command = new NavigateTest();
		} else if ("NAVIGATE_SUMMARY".equals(objName)) {
			command = new NavigateSummary();
		} else if ("NAVIGATE_GOTO_ITEM".equals(objName)) {
			command = new NavigateGotoItem(objIndex);
		} else if ("NAVIGATE_PREVIEW_ITEM".equals(objName)) {
			command = new NavigatePreviewItem(objIndex);
		} else if ("CONTINUE".equals(objName)) {
			command = new Continue();
		} else if ("CHECK".equals(objName)) {
			command = new Check();
		} else if ("RESET".equals(objName)) {
			command = new Reset();
		} else if ("SHOW_ANSWERS".equals(objName)) {
			command = new ShowAnswers();
		//} else if ("HIDE_ANSWERS".equals(objName)) {
		//	command = new HideAnswers();
		} else if ("LOCK".equals(objName)) {
			command = new Lock();
		}else if ("UNLOCK".equals(objName)) {
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
			super("NAVIGATE_NEXT_ITEM");
		}		
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.nextPage();
		}
	}
	public final static class NavigatePreviousItem extends FlowCommand{
		public NavigatePreviousItem(){
			super("NAVIGATE_PREVIOUS_ITEM");
		}		
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.previousPage();
		}
	}
	public final static class NavigateFirstItem extends FlowCommand{
		public NavigateFirstItem(){
			super("NAVIGATE_FIRST_ITEM");
		}		
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoFirstPage();
		}
	}
	public final static class NavigateLastItem extends FlowCommand{
		public NavigateLastItem(){
			super("NAVIGATE_LAST_ITEM");
		}		
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoLastPage();
		}
	}
	public final static class NavigateToc extends FlowCommand{
		public NavigateToc(){
			super("NAVIGATE_TOC");
		}		
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoToc();
		}
	}
	public final static class NavigateTest extends FlowCommand{
		public NavigateTest(){
			super("NAVIGATE_TEST");
		}		
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoTest();
		}
	}
	public final static class NavigateSummary extends FlowCommand{
		public NavigateSummary(){
			super("NAVIGATE_SUMMARY");
		}		
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
			super("NAVIGATE_GOTO_ITEM", index);
		}	
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.gotoPage(index);
		}
	}
	public final static class NavigatePreviewItem extends FlowCommandWithIndex{
		public NavigatePreviewItem(int index){
			super("NAVIGATE_PREVIEW_ITEM", index);
		}	
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.previewPage(index);
		}
	}
	public static class Check extends FlowCommand{
		public Check(){
			super("CHECK");
		}
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.checkPage();
		}
	}
	public static class Continue extends FlowCommand{
		public Continue(){
			super("CONTINUE");
		}
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.continuePage();
		}
	}
	public static class Reset extends FlowCommand{
		public Reset(){
			super("RESET");
		}
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.resetPage();
		}
	}
	public static class ShowAnswers extends FlowCommand{
		public ShowAnswers(){
			super("SHOW_ANSWERS");
		}
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.showAnswers();
		}
	}
	public static class Lock extends FlowCommand{
		public Lock(){
			super("LOCK");
		}
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.lockPage();
		}
	}
	public static class Unlock extends FlowCommand{
		public Unlock(){
			super("UNLOCK");
		}
		@Override
		public void execute(FlowCommandsListener listener) {
			listener.unlockPage();
		}
	}
	
	
}
