package eu.ydp.empiria.player.client.controller.flow;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.ItemParameters;
import eu.ydp.empiria.player.client.controller.communication.ItemParametersSocket;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommandsListener;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventType;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;
import eu.ydp.empiria.player.client.util.config.OptionsReader;

public class MainFlowProcessor implements FlowCommandsListener, FlowDataSupplier {

	public MainFlowProcessor(FlowProcessingEventsListener feel){
		flowExecutionEventsListener = feel;
		flowOptions = OptionsReader.getFlowOptions();
		displayOptions = new DisplayOptions();
		isCheck = false;
		isShowAnswers = false;
		isInitalized = false;
	}
	
	private FlowProcessingEventsListener flowExecutionEventsListener;
	private ItemParametersSocket itemParametersSocket;
	
	private int currentPageIndex;
	private PageType currentPageType;
	private int itemsCount;
	private FlowOptions flowOptions;
	private DisplayOptions displayOptions;
	private boolean isCheck;
	private boolean isShowAnswers;
	private boolean isLock;
	private boolean isInitalized;

	public void init(int _itemsCount){
		itemsCount = _itemsCount;
		currentPageIndex = 0;
		if (flowOptions.showToC)
			currentPageType = PageType.TOC;
		else 
			currentPageType = PageType.TEST;
	}

	public void initFlow(){
		if (!isInitalized){
			isInitalized = true;
			doFlow();
		}
	}

	public void deinitFlow(){
		isInitalized = false;
	}
	
	private void doFlow(){
		if (isInitalized){
			flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.PAGE_LOADED ) );
		}
	}

	public void setFlowOptions(FlowOptions o){
		flowOptions = o;
		if (currentPageType == PageType.TOC  &&  !flowOptions.showToC){
			currentPageType = PageType.TEST;
			currentPageIndex = 0;
		} else if (currentPageType == PageType.SUMMARY  &&  !flowOptions.showSummary){
			currentPageType = PageType.TEST;
			currentPageIndex = 0;
		}
	}
	
	public FlowOptions getFlowOptions(){
		return flowOptions;
	}
	
	public void setDisplayOptions(DisplayOptions o){
		displayOptions = o;
	}
	
	public DisplayOptions getDisplayOptions(){
		return displayOptions;
	}
	
	@Override
	public void gotoPage(int index) {
		if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL){
			if (index == 0){
				onPageChange();
				currentPageType = PageType.TEST;
				currentPageIndex = index;
				doFlow();
			}
		} else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE){
			if (index >= 0  &&  index < itemsCount){
				onPageChange();
				currentPageType = PageType.TEST;
				currentPageIndex = index;
				doFlow();
			}
		}
		
	}

	@Override
	public void gotoSummary() {
		if (currentPageType != PageType.SUMMARY  &&  flowOptions.showSummary){
			onPageChange();
			flowOptions.activityMode = ActivityMode.NORMAL;
			//displayOptions.setPreviewMode(false);
			currentPageType = PageType.SUMMARY;
			currentPageIndex = 0;
			doFlow();
		}
		
	}

	@Override
	public void gotoToc() {
		if (currentPageType != PageType.TOC  &&  flowOptions.showToC){
			onPageChange();
			currentPageType = PageType.TOC;
			currentPageIndex = 0;
			doFlow();
		}
		
	}

	@Override
	public void gotoTest() {
		if (currentPageType != PageType.TEST){
			if (itemsCount > 0){
				onPageChange();
				gotoPage(0);
			}
		}
		
	}

	@Override
	public void nextPage() {
		if (currentPageType == PageType.SUMMARY){

		} else if (currentPageType == PageType.TOC){
			if (0 < itemsCount){
				gotoPage(0);
			}
		} else if (currentPageType == PageType.TEST){
			
			if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL){
				
			} else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE){
				if (currentPageIndex+1 >= 0  &&  currentPageIndex+1 < itemsCount){
					gotoPage(currentPageIndex+1);
				}
			}
		}
		
		
	}

	@Override
	public void previousPage() {
		if (currentPageType == PageType.SUMMARY){
			
		} else if (currentPageType == PageType.TOC){
			
		} else if (currentPageType == PageType.TEST){
			
			if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL){
				
			} else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE){
				if (currentPageIndex-1 >= 0  &&  currentPageIndex-1 < itemsCount){
					gotoPage(currentPageIndex-1);
				} else if (currentPageIndex == 0){
					gotoToc();
				}
			}
		}
		
	}

	@Override
	public void gotoFirstPage() {
		if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL){
			if (0 < itemsCount){
				gotoPage(0);
			}
		} else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE){
			if (0 < itemsCount){
				gotoPage(0);
			}
		}
	}

	@Override
	public void gotoLastPage() {
		if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL){
			if (0 < itemsCount){
				gotoPage(0);
			}
		} else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE){
			if (itemsCount > 0){
				gotoPage(itemsCount-1);
			}
		}
		
	}

	@Override
	public void checkPage() {
		if (isCheck == false  &&  isShowAnswers == false){
			isCheck = true;
			flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.CHECK ) );
			//updateNavigation();
		}
	}

	@Override
	public void showAnswers() {
		if (isCheck == false  &&  isShowAnswers == false){
			isShowAnswers = true;
			flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.SHOW_ANSWERS ) );
			//updateNavigation();
		}
		
	}
	/*
	// REMOVE HIDE_ANSWERS
	@Override
	public void hideAnswers() {
		if (isCheck == false  &&  isMarkAnswers == true){
			isMarkAnswers = false;
			flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.SHOW_ANSWERS ) );
			//updateNavigation();
		}
		
	}
	*/
	@Override
	public void continuePage() {
		if (isCheck == true){
			isCheck = false;
			flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.CONTINUE ) );
		}
		if (isLock == true){
			isLock = false;
			flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.UNLOCK ) );
		}

	}

	@Override
	public void lockPage() {
		if (isLock == false){
			isLock = true;
			flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.LOCK ) );
			//updateNavigation();
		}
	}

	@Override
	public void unlockPage() {
		if (isLock == true){
			isLock = false;
			flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.UNLOCK ) );
			//updateNavigation();
		}
	}

	@Override
	public void resetPage() {
		isCheck = false;
		isShowAnswers = false;
		flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.RESET ) );
		//updateNavigation();
	}

	@Override
	public void previewPage(int index) {
		onPageChange();
		flowOptions.activityMode = ActivityMode.CHECK;
		//displayOptions.setPreviewMode(true);
		gotoPage(index);
	}
	
	public void onPageChange(){
		flowExecutionEventsListener.onFlowExecutionEvent(new FlowProcessingEvent( FlowProcessingEventType.PAGE_CHANGING ) );
		isCheck = false;
		isShowAnswers = false;
		isLock = false;
	}
	/*
	public void updateNavigation(){
		navigationView.updateButtons(currentPageType, currentPageIndex, 
				(flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE)?itemsCount:1, flowOptions, isCheck, isAnswers, displayOptions, getItemParamters());
	}
	
	public NavigationViewSocket getNavigationViewSocket(){
		return navigationView;
	}*/
	
	public boolean getFlowFlagCheck(){
		return isCheck;
	}
	
	public boolean getFlowFlagShowAnswers(){
		return isShowAnswers;
	}
	
	public boolean getFlowFlagLock(){
		return isLock;
	}
	
	public PageReference getPageReference(){
		int[] currentPageItemsIndices = null;
		if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE){
			currentPageItemsIndices = new int[1];
			currentPageItemsIndices[0] = currentPageIndex;
		} else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL){
			currentPageItemsIndices = new int[itemsCount];
			for (int i = 0 ; i < itemsCount ; i ++){
				currentPageItemsIndices[i] = i;
			}
		}
		PageReference pr = new PageReference(currentPageType, currentPageItemsIndices, flowOptions, displayOptions);
		
		return pr;
	}/*
	
	public ItemActivityOptions getItemActivityOptions(){
		return activityOptions;
	}
*/
	public PageType getPageType(){
		return currentPageType;
	}
	
	public ActivityMode getActivityMode(){
		return flowOptions.activityMode;
	}

	public int getCurrentPageIndex(){
		return currentPageIndex;
	}
	public PageType getCurrentPageType(){
		return currentPageType;
	}

/*
	@Override
	public void setItemParamtersSocket(ItemParametersSocket ips) {
		itemParametersSocket = ips;
	}*/

	public ItemParameters getItemParamters() {
		if (itemParametersSocket != null)
			return itemParametersSocket.getItemParameters();
		return new ItemParameters();
	}
	
	
}
