package eu.ydp.empiria.player.client.view.page;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequestSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;
import eu.ydp.empiria.player.client.view.item.ItemContentView;
import eu.ydp.empiria.player.client.view.item.ItemViewSocket;

public class PageContentView implements PageViewSocket {
	
	public PageContentView(Panel p){
		pagePanel = p;
		titlePanel = new FlowPanel();
		titlePanel.setStyleName("qp-page-title");
		itemsPanel = new FlowPanel();
		itemsPanel.setStyleName("qp-page-content");
		pagePanel.add(itemsPanel);
	}

	private Panel pagePanel;
	private Panel itemsPanel;
	private Panel titlePanel;
	private Panel contentPanel;
	
	private Panel[] itemPanels;
	private ItemContentView[] items;
	
	@Override
	public ItemViewSocket getItemViewSocket(int index) {
		return items[index];
	}

	@Override
	public void initItemViewSockets(int count) {
		itemsPanel.clear();
		itemPanels = new Panel[count];
		items = new ItemContentView[count];
		
		for (int i = 0 ; i < count ; i ++){
			itemPanels[i] = new FlowPanel();
			itemPanels[i].setStyleName("qp-page-item");
			items[i] = new ItemContentView(itemPanels[i]);
			itemsPanel.add(itemPanels[i]);
		}

	}

	@Override
	public void setPageViewCarrier(PageViewCarrier pvc) {
		titlePanel.clear();
		titlePanel.add(pvc.getPageTitle());
		
		if (pvc.hasContent()){
			contentPanel = new FlowPanel();

			if (pvc.pageType == PageType.ERROR){

				contentPanel.setStyleName("qp-page-error");
				
				Label errorLabel =  new Label(pvc.errorMessage);
				errorLabel.setStyleName("qp-page-error-text");
				contentPanel.add(errorLabel);
				
			} else if (pvc.pageType == PageType.TOC){
				contentPanel.setStyleName("qp-toc");
				
				Label tocTitle = new Label(LocalePublisher.getText(LocaleVariable.TOC_TITLE));
				tocTitle.setStyleName("qp-toc-title");
				contentPanel.add(tocTitle);
				
				Panel titlesPanel = new VerticalPanel();
				titlesPanel.setStyleName("qp-toc-items");
				final IFlowRequestSocket flowRequestSocket = pvc.flowRequestSocket;
				
				for (int t = 0 ; t < pvc.titles.length ; t ++){
					final int tt = t;
					Label titleLabel = new Label(LocalePublisher.getText(LocaleVariable.TOC_PAGE) + " " + String.valueOf(t+1) + LocalePublisher.getText(LocaleVariable.TOC_PAGE_DOT) + pvc.titles[t]);
					titleLabel.setStyleName("qp-toc-item-title");
					titleLabel.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							flowRequestSocket.invokeRequest( new FlowRequest.NavigateGotoItem(tt) );
						}
					});
					titleLabel.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Label)event.getSource()).setStyleName("qp-toc-item-title-hover");
						}
					});
					titleLabel.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Label)event.getSource()).setStyleName("qp-toc-item-title");
						}
					});
					titlesPanel.add(titleLabel);
				}
				
				contentPanel.add(titlesPanel);
				
			} else if (pvc.pageType == PageType.SUMMARY){
				Grid resultItemsInfo = new Grid(pvc.titles.length, 5);
			    resultItemsInfo.setStylePrimaryName("qp-resultpage-items");
				final IFlowRequestSocket flowRequestSocket = pvc.flowRequestSocket;
				
				for (int t = 0 ; t < pvc.titles.length ; t ++){
					final int tt = t;
					String titleString = LocalePublisher.getText(LocaleVariable.SUMMARY_PAGE) + " " + String.valueOf(t+1) + ": " + pvc.titles[t];
					Label titleLabel = new Label(titleString);
					titleLabel.setStyleName("qp-toc-item-title");
					titleLabel.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							flowRequestSocket.invokeRequest( new FlowRequest.NavigateGotoItem(tt) );
						}
					});
					titleLabel.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Label)event.getSource()).setStyleName("qp-toc-item-title-hover");
						}
					});
					titleLabel.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Label)event.getSource()).setStyleName("qp-toc-item-title");
						}
					});
			    	resultItemsInfo.setWidget(t, 0, titleLabel);
			    	
			    	boolean currVisited = false;
			    	int currTodo = 0;
			    	int currDone = 0;
			    	int currChecks = 0;
			    	int currMistakes = 0;
			    	int currTime = 0;
			    	
			    	ItemSessionDataSocket currISDS = pvc.sessionDataSocket.getItemSessionDataSocket(t);
			    	if (currISDS != null){
			    		Variable currVar;
			    		currVar = pvc.sessionDataSocket.getItemSessionDataSocket(t).getVariableProviderSocket().getVariableValue("VISITED");
			    		currVisited = (currVar != null  &&  "TRUE".equals(currVar.getValuesShort()));
			    		currVar = pvc.sessionDataSocket.getItemSessionDataSocket(t).getVariableProviderSocket().getVariableValue("TODO");
			    		if (currVar != null)
			    			currTodo = IntegerUtils.tryParseInt( currVar.getValuesShort() );
			    		currVar = pvc.sessionDataSocket.getItemSessionDataSocket(t).getVariableProviderSocket().getVariableValue("DONE");
			    		if (currVar != null)
			    			currDone = IntegerUtils.tryParseInt( currVar.getValuesShort() );
			    		currVar = pvc.sessionDataSocket.getItemSessionDataSocket(t).getVariableProviderSocket().getVariableValue("CHECKS");
				    		if (currVar != null)
				    	currChecks = IntegerUtils.tryParseInt( currVar.getValuesShort() );
			    		currVar = pvc.sessionDataSocket.getItemSessionDataSocket(t).getVariableProviderSocket().getVariableValue("MISTAKES");
				    		if (currVar != null)
				    	currMistakes = IntegerUtils.tryParseInt( currVar.getValuesShort() );
				    	currTime = pvc.sessionDataSocket.getItemSessionDataSocket(t).getActualTime();
				    	
			    	}
			    	
			    	String resultString = "";
			    	if (currVisited){
			    		if (currTodo > 0)
			    			resultString = String.valueOf(currDone).replace(".0", "") + "/" + String.valueOf(currTodo).replace(".0", "");
			    		else
			    			resultString = LocalePublisher.getText(LocaleVariable.SUMMARY_NOTSCORED);
			    	} else {
			    		resultString = LocalePublisher.getText(LocaleVariable.SUMMARY_NOTVISITED);
			    	}
			    	resultItemsInfo.setText(t, 1, resultString);

					String timeFormatted = String.valueOf( currTime/60 ) + ":" + ((currTime%60 < 10)?"0":"") + String.valueOf( currTime%60 );
					
			    	resultItemsInfo.setText(t, 2, timeFormatted + LocalePublisher.getText(LocaleVariable.SUMMARY_STATS_TIME_SUFIX));
			    	
			    	if (currTodo > 0){
			    		resultItemsInfo.setText(t, 3, String.valueOf(currChecks) + LocalePublisher.getText(LocaleVariable.SUMMARY_STATS_CHECKCOUNT_SUFIX));
			    		resultItemsInfo.setText(t, 4, String.valueOf(currMistakes) + LocalePublisher.getText(LocaleVariable.SUMMARY_STATS_MISTAKES_SUFIX));
			    	} else {
			    		resultItemsInfo.setText(t, 3, LocalePublisher.getText(LocaleVariable.SUMMARY_STATS_CHECKCOUNT_NO));
			    		resultItemsInfo.setText(t, 4, LocalePublisher.getText(LocaleVariable.SUMMARY_STATS_MISTAKES_NO));
			    	}
		    	
				}

		    	int doneTotal = 0;
		    	int todoTotal = 0;
		    	int timeTotal = 0;
		    	
				Variable currVar;
				currVar = pvc.sessionDataSocket.getVariableProviderSocket().getVariableValue("DONE");
				if (currVar != null)
					doneTotal = IntegerUtils.tryParseInt( currVar.getValuesShort() );
				currVar = pvc.sessionDataSocket.getVariableProviderSocket().getVariableValue("TODO");
				if (currVar != null)
					todoTotal = IntegerUtils.tryParseInt( currVar.getValuesShort() );
				timeTotal = pvc.sessionDataSocket.getTimeAssessmentTotal();

				contentPanel.add(resultItemsInfo);
				
				int donePercent = 100;
				if (todoTotal > 0){
					donePercent = (doneTotal * 100)/todoTotal;
				}
				
	    	    FlowPanel resultScorePanel = new FlowPanel();
	    	    resultScorePanel.setStyleName("qp-resultpage-score");
	    	    contentPanel.add(resultScorePanel);
	    	    
	    		Label resultScoreInfoPercent = new Label(LocalePublisher.getText(LocaleVariable.SUMMARY_INFO_YOURSCOREIS1) + 
	    				donePercent + 
	    	    		LocalePublisher.getText(LocaleVariable.SUMMARY_INFO_YOURSCOREIS2)); 
	    		resultScoreInfoPercent.setStylePrimaryName("qp-resultpage-percents");
	    		resultScorePanel.add(resultScoreInfoPercent);
	    	    
	    		Label resultScoreInfoPoints = new Label(doneTotal + 
	    	    		LocalePublisher.getText(LocaleVariable.SUMMARY_INFO_YOURSCOREIS3));
	    	    resultScoreInfoPoints.setStylePrimaryName("qp-resultpage-points");
	    	    resultScorePanel.add(resultScoreInfoPoints);
	    	    
	    	    String timeFormatted = String.valueOf( timeTotal/60 ) + ":" + ((timeTotal%60 < 10)?"0":"") + String.valueOf( timeTotal%60 );
				
	    		Label resultScoreInfoTime = new Label(LocalePublisher.getText(LocaleVariable.SUMMARY_INFO_YOURSCOREIS4) + 
	    				timeFormatted + 
	    	    		LocalePublisher.getText(LocaleVariable.SUMMARY_INFO_YOURSCOREIS5));
	    	    resultScoreInfoTime.setStylePrimaryName("qp-resultpage-time");
	    	    resultScorePanel.add(resultScoreInfoTime);

	    	    contentPanel.setStyleName("qp-summary");
			}
			
			itemsPanel.clear();
			itemsPanel.add(contentPanel);
		}
	}

}
