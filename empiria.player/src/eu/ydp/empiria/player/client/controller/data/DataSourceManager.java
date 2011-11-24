package eu.ydp.empiria.player.client.controller.data;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.PageDataError;
import eu.ydp.empiria.player.client.controller.communication.PageDataSummary;
import eu.ydp.empiria.player.client.controller.communication.PageDataTest;
import eu.ydp.empiria.player.client.controller.communication.PageDataToC;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.data.events.AssessmentDataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.ItemDataCollectionLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.StyleDataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.log.OperationLogEvent;
import eu.ydp.empiria.player.client.controller.log.OperationLogManager;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.xml.XMLDocument;
import eu.ydp.empiria.player.client.util.xml.document.IDocumentLoaded;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class DataSourceManager implements AssessmentDataLoaderEventListener, ItemDataCollectionLoaderEventListener, StyleDataLoaderEventListener, DataSourceDataSupplier {
	
	public DataSourceManager(){
		mode = DataSourceManagerMode.NONE;
		assessmentDataManager = new AssessmentDataSourceManager(this);
		itemDataCollectionManager = new ItemDataSourceCollectionManager(this);
	}
	
	private StyleDataSourceManager styleDataSourceManager;
	private int styleLoadCounter;
	
	private AssessmentDataSourceManager assessmentDataManager;
	private ItemDataSourceCollectionManager itemDataCollectionManager;
	private DataSourceManagerMode mode;
	private DataLoaderEventListener listener;
	
	public XMLData getAssessmentData(){
		return assessmentDataManager.getAssessmentData();
	}

	public ItemData getItemData(int index){
		return itemDataCollectionManager.getItemData(index);
	}
	
	public StyleSocket getStyleProvider() {
		return getStyleDataSourceManager();
	}
	
	public int getItemsCount(){
		return assessmentDataManager.getItemsCount();
	}
	
	public void loadMainDocument(String url){
		
		if (mode == DataSourceManagerMode.LOADING_ASSESSMENT  ||  mode == DataSourceManagerMode.LOADING_ITEMS)
			return;
		
		OperationLogManager.logEvent(OperationLogEvent.LOADING_STARTED);
		
		mode = DataSourceManagerMode.LOADING_ASSESSMENT;

		String resolvedURL;

		if( GWT.getHostPageBaseURL().startsWith("file://") ){

			String localURL = URL.decode( GWT.getHostPageBaseURL() );
			resolvedURL = localURL + url;
		}
		else if( url.contains("://") || url.startsWith("/") ){
			resolvedURL = url;
		}
		else{
			resolvedURL = GWT.getHostPageBaseURL() + url;
		}

		new eu.ydp.empiria.player.client.util.xml.XMLDocument(resolvedURL, new IDocumentLoaded(){

			public void finishedLoading(Document document, String baseURL) {
				if (!isItemDocument(document)){
					assessmentDataManager.setAssessmentData(new XMLData(document, baseURL));
					loadItems();
				} else {
					assessmentDataManager.setAssessmentDefaultData();
					loadSingleItemData(new XMLData(document, baseURL));
				}
			}

			@Override
			public void loadingErrorHandler(String error) {
				assessmentDataManager.setAssessmentLoadingError(error);
				onLoadFinished();
			}
		});
	}
	
	public void loadItems(){
		loadItems(assessmentDataManager.getItemUrls());		
	}
	
	private void loadItems(String[] urls){
		
		mode = DataSourceManagerMode.LOADING_ITEMS;
		
		itemDataCollectionManager.initItemDataCollection(urls.length);
		
		if (urls.length == 0)
			onItemCollectionLoaded();
		
		for (int i = 0 ; i < urls.length ; i ++){
			final int ii = i;
			
			new XMLDocument(urls[ii], new IDocumentLoaded(){

				public void finishedLoading(Document document, String baseURL) {
					itemDataCollectionManager.setItemData(ii, new XMLData(document, baseURL));
				}

				@Override
				public void loadingErrorHandler(String error) {
					itemDataCollectionManager.setItemData(ii, error);
				}
			});
		}
		
	}
	
	private void loadSingleItemData(XMLData itemData){
		itemDataCollectionManager.setItemDataCollection(new XMLData[] {itemData});
	}
	
	public void loadData(XMLData ad, XMLData[] ids){
		assessmentDataManager.setAssessmentData(ad);
		itemDataCollectionManager.setItemDataCollection(ids);
	}
	
	
	public PageData generatePageData(PageReference ref){
		PageData pd;
		
		if (assessmentDataManager.isError()){
			pd = new PageDataError(assessmentDataManager.getErrorMessage());
			return pd;
		}
		
		if (ref.type == PageType.TOC){
			pd = new PageDataToC(itemDataCollectionManager.getTitlesList());
		} else if (ref.type == PageType.SUMMARY){
			pd = new PageDataSummary(itemDataCollectionManager.getTitlesList());
		} else {
			ItemData[] ids = new ItemData[ref.pageIndices.length];
			
			for (int i = 0 ; i < ref.pageIndices.length ; i ++){
				ids[i] = itemDataCollectionManager.getItemData(ref.pageIndices[i]);
			}
			
			pd = new PageDataTest(ids, ref.flowOptions, ref.displayOptions);
		}
		
		return pd;
	}
	
	public Vector<String> getAssessmentStyleLinksForUserAgent(String userAgent){
		return assessmentDataManager.getStyleLinksForUserAgent(userAgent);
	}
	
	public Vector<String> getPageStyleLinksForUserAgent(PageReference ref, String userAgent){
		if (ref.pageIndices.length == 0)
			return new Vector<String>();
		
		return itemDataCollectionManager.getStyleLinksForUserAgent(ref.pageIndices[0], userAgent);
	}

	@Override
	public void onAssessmentDataLoaded() {
		getDataLoaderEventListener().onAssessmentLoaded();
		mode = DataSourceManagerMode.SERVING;		
	}
	
	@Override
	public void onItemCollectionLoaded() {
/*
		long timeStart = (long) ((new Date()).getTime() );
		for (int i = 0 ; i < itemDataCollectionManager.getItemsCount() ; i ++){
			NodeList nodes = itemDataCollectionManager.getItemData(i).data.getDocument().getElementsByTagName("outcomeDeclaration");
			for (int n = 0 ; n < nodes.getLength() ; n ++){
				if (((Element)nodes.item(n)).getAttribute("identifier").equals("TODO")){
					NodeList nodes2 = ((Element)nodes.item(n)).getElementsByTagName("defaultValue");
					if (nodes2.getLength() > 0){
						if (nodes2.item(0).hasChildNodes()){
							NodeList nodes3 = ((Element)nodes2.item(0)).getElementsByTagName("value");
							if (nodes3.getLength() > 0){
								String valueString = nodes3.item(0).getFirstChild().getNodeValue();
								int value = Integer.parseInt(valueString);
								increment(value);
							}
						}
					}
				}
				
			}
		}
		long timeEnd = (long) ((new Date()).getTime() );
		String msg = (new Long(timeEnd - timeStart)).toString();
		Window.alert(msg);*/
		// load item styles
		loadStyles();
	}
	
	private void increment(int value){
		value++;
	}
	
	private void loadStyles() {
		mode = DataSourceManagerMode.LOADING_STYLES;
		String userAgent = Navigator.getUserAgent();
		try {
			// load assesment styles
			List<String> aStyles = assessmentDataManager.getStyleLinksForUserAgent(userAgent);
			for (String styleURL : aStyles) {
				final String styleURL2 = styleURL;
				RequestBuilder builder = new RequestBuilder( RequestBuilder.GET, styleURL);
				styleLoadCounter++;
				builder.setCallback( new RequestCallback() {
					@Override
					public void onResponseReceived(Request request, Response response) {
						styleLoadCounter--;
						if (response.getStatusCode()==Response.SC_OK) { 
							getStyleDataSourceManager().addAssessmentStyle( response.getText() , styleURL2 );
						} else {
							// TODO add error handling
						}
					}
					
					@Override
					public void onError(Request request, Throwable exception) {
						// TODO Add error handling
						styleLoadCounter--;
					}
				});
				builder.send();
			}
			// load item styles
			int itemCount = itemDataCollectionManager.getItemsCount();
			for (int i=itemCount-1;i>=0;i--) {
				List<String> iStyles = itemDataCollectionManager.getStyleLinksForUserAgent(i, userAgent);
				for (String styleURL : iStyles) {
					final int ii = i;
					final String styleURL2 = styleURL;
					RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, styleURL);
					styleLoadCounter++;
					builder.setCallback( new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							styleLoadCounter--;
							if (response.getStatusCode()==Response.SC_OK) {
								getStyleDataSourceManager().addItemStyle(ii, response.getText() , styleURL2);
							} else {
								// TODO add error handling
							}
						}
						
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO add error handling
							styleLoadCounter--;
						}
					});
					builder.send();
				}
			}
		} catch (RequestException e) {
			e.printStackTrace();
		}
		
		// check if all styles are loaded
		DeferredCommand.addCommand( new IncrementalCommand() {
			@Override
			public boolean execute() {
				if (styleLoadCounter==0) {
					onLoadFinished();
					return false;
				}
				return true;
			}
		});
		
	}

	@Override
	public void onStyleDataLoaded() {
		onLoadFinished();
	}
	
	public void onLoadFinished(){
		mode = DataSourceManagerMode.SERVING;		
		OperationLogManager.logEvent(OperationLogEvent.LOADING_FINISHED);
		getDataLoaderEventListener().onDataReady();		
	}
	
	public DataSourceManagerMode getMode(){
		return mode;
	}
	
	public String getAssessmentTitle(){
		return assessmentDataManager.getAssessmentTitle();
	}
	
	public String getItemTitle(int index){
		return itemDataCollectionManager.getTitlesList()[index];
	}

	@Inject
	public void setStyleDataSourceManager(StyleDataSourceManager styleDataSourceManager) {
		this.styleDataSourceManager = styleDataSourceManager;
	}
	
	public StyleDataSourceManager getStyleDataSourceManager() {
		return styleDataSourceManager;
	}

	public void setDataLoaderEventListener(DataLoaderEventListener listener) {
		this.listener = listener;
	}

	public DataLoaderEventListener getDataLoaderEventListener() {
		return listener;
	}

	private boolean isItemDocument(Document doc){
		try {
			return doc.getDocumentElement().getNodeName().equals("assessmentItem");
		} catch (Exception e) {
		}
		return true;
	}
	
}